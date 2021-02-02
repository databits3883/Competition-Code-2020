/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDLights extends SubsystemBase {
  AddressableLED lights = new AddressableLED(Constants.ledChannel);
  AddressableLEDBuffer buffer = new AddressableLEDBuffer(60);
  Timer timer = new Timer();
  /**
   * Creates a new LEDLights.
   */
  public LEDLights() {
    // lights.setLength(60);
    // for(int i =0; i<buffer.getLength(); i++){
    //   buffer.setLED(i, new Color(0,0,0));
    // }
    // lights.setData(buffer);
    // lights.start();
    // timer.start();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // double time = timer.get()%10.0;
    // for(int i =0; i<buffer.getLength(); i++){
    //   buffer.setLED(i, new Color(0,0.1,0));
    // }
    // lights.setData(buffer);
  }
}
