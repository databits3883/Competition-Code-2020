/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import java.util.Map;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.util.PIDTuningParameters;
import frc.robot.util.SparkMaxPIDController;

public abstract class SparkMaxPIDSubsystem extends SubsystemBase {
  SparkMaxPIDController m_mainController;
  CANSparkMax m_motor;
  CANDigitalInput lowerLimit;
  CANDigitalInput upperLimit;
  CANEncoder m_encoder;

  double m_setpointMin;
  double m_setpointMax;

  NetworkTableEntry m_setpointEntry;
  DoubleSupplier m_processVariable;

  double m_tolerance;
  /**
   * Creates a new SparkMaxPIDSubsystem.
   */
  public SparkMaxPIDSubsystem(String name, CANSparkMax mainMotor, ControlType controlType, PIDTuningParameters tuning, double conversionFactor, double min, double max, LimitSwitchPolarity limitPolarity) {
    //set up conversion factor and accessor for the process variable
    m_encoder = mainMotor.getEncoder();
    lowerLimit = mainMotor.getReverseLimitSwitch(limitPolarity);
    upperLimit = mainMotor.getForwardLimitSwitch(limitPolarity);

    switch (controlType){
      case kPosition: m_encoder.setPositionConversionFactor(conversionFactor);
        m_processVariable = m_encoder::getPosition;
        break;
      case kVelocity: m_encoder.setVelocityConversionFactor(conversionFactor);
        m_processVariable = m_encoder::getVelocity;
        break;
      default: throw new IllegalArgumentException("SparkMaxPIDSubsystem doesn't yet support control type "+controlType.toString());
    }
    m_motor = mainMotor;

    m_mainController =new SparkMaxPIDController(mainMotor,controlType,tuning);

    m_setpointMin = min;
    m_setpointMax = max;

    ShuffleboardLayout container = Shuffleboard.getTab(name).getLayout(name,BuiltInLayouts.kList).withProperties(Map.of("Label position","TOP")).withSize(2, 5);
    ShuffleboardLayout tuningLayout = container.getLayout("tuning","Grid Layout");
    m_mainController.addTuningToShuffleboard(tuningLayout).withProperties(SparkMaxPIDController.tuningDisplayMap).withSize(2, 2);
    //add a graph with the setpoint and the current value
    container.addDoubleArray("Process Variable vs Setpoint", ()->(new double[] {m_processVariable.getAsDouble(),m_mainController.getSetpoint()}))
      .withWidget(BuiltInWidgets.kGraph);
    m_setpointEntry = container.add("setpoint", m_mainController.getSetpoint())
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("Min",m_setpointMin,"Max",m_setpointMax))
      .getEntry();
    m_setpointEntry.addListener(notification ->setSetpointInternal(notification.value.getDouble()), EntryListenerFlags.kUpdate);
  }

  void setSetpointInternal(double newSetpoint){
    newSetpoint = MathUtil.clamp(newSetpoint, m_setpointMin, m_setpointMax);
    m_mainController.setSetpoint(newSetpoint);
  }
  public void setSetpoint(double newSetpoint){
    setSetpointInternal(newSetpoint);
    m_setpointEntry.setDouble(newSetpoint);
  }

  public void holdCurrentPosition(){
    setSetpoint(m_processVariable.getAsDouble());
    m_mainController.reset();
  }

  public double getCurrentError(){
    return m_mainController.getSetpoint() - m_processVariable.getAsDouble();
  }

  public boolean onTarget(){
    return Math.abs(getCurrentError()) <=m_tolerance;
  }
  public void setTolerance(double tolerance){
    m_tolerance = tolerance;
  }

  void checkLimitSwitches(){
    if(lowerLimit.get()){
      m_encoder.setPosition(m_setpointMin);
    }
    if(upperLimit.get()){
      m_encoder.setPosition(m_setpointMax);
    }
  }
}
