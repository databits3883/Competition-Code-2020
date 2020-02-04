/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Launcher extends SubsystemBase {
  private final CANSparkMax leader = new CANSparkMax(Constants.launcherLeaderChannel, MotorType.kBrushless);
  private final CANSparkMax follower = new CANSparkMax(Constants.launcherFollowerChannel, MotorType.kBrushless);

  private final CANEncoder encoder = new CANEncoder(leader);
  private final CANPIDController controller = new CANPIDController(leader);

  private double p,i,d,ff,speed;
  private NetworkTableEntry pEntry,iEntry,dEntry,ffEntry,speedEntry;
  
  /**
   * Creates a new Launcher.
   */
  public Launcher() {
    follower.follow(leader,true);
    encoder.setVelocityConversionFactor(1);

    pEntry = Shuffleboard.getTab("LaunchingTuning").add("portional",p).getEntry();
    iEntry = Shuffleboard.getTab("LaunchingTuning").add("integral",i).getEntry();
    dEntry = Shuffleboard.getTab("LaunchingTuning").add("derivative",d).getEntry();
    ffEntry = Shuffleboard.getTab("LaunchingTuning").add("feedForward",ff).getEntry();



    speedEntry = Shuffleboard.getTab("LaunchingTuning").add("speed",speed).getEntry();
  }

  /**
   * Sets the speed of the Launcher wheel
   * @param speed the target speed of the wheel in inches per second
   */
  public void setSpeed(double speed){
    controller.setReference(speed, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateGains();
    System.out.println(controller.getP());
  }
  
  private void updateGains(){
    if(p != pEntry.getDouble(0)){
      p = pEntry.getDouble(0);
      controller.setP(p);
    }
    if(i != iEntry.getDouble(0)){
      i = iEntry.getDouble(0);
      controller.setI(i);
    }
    if(d != dEntry.getDouble(0)){
      d = dEntry.getDouble(0);
      controller.setD(d);
    }
    if(ff != ffEntry.getDouble(0)){
      ff = ffEntry.getDouble(0); 
      controller.setFF(ff);
    }
    if(speed != speedEntry.getDouble(0)){
      speed = speedEntry.getDouble(0); 
      setSpeed(speed);
    }
  }
}
