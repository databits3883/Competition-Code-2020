/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //CAN
    public static final int leftLeaderChannel = 1;
    public static final int leftFollowerChannel = 4;
    public static final int rightLeaderChannel = 3;
    public static final int rightFollowerChannel = 2;
    
    public static final int intakeExtenderChannel = 10;
 
    public static final int turretRotationChannel = 5;
    public static final int turretHoodChannel =8;
    public static final int launcherLeaderChannel = 6;
    public static final int launcherFollowerChannel = 7;
  
    public static final int controlWheelChannel = 9;

    //pwm
    public static final int intakeRetrieveChannel = 0;
    public static final int lowerIntakeChannel = 1;
    public static final int upperIntakeChannel = 2;
    public static final int hookLiftChannel = 3;
    public static final int liftWinchChannel = 4;

    //DIO
    public static final int upperStagingSensor = 7;
    public static final int lowerStagingStartSensor = 8;
    public static final int lowerStagingEndSensor = 9;

    public static final int controlPanelPressedChannel = 5;

    //I2C
    public static final I2C.Port colorSensorPort = Port.kOnboard;

    //physical
    //distances in feet, time in seconds
    public static final double maxTurretAngle = 250;
    public static final double maxDriveSpeed = 20;
    public static final double wheelDiameter = 7.0/12.0;
    public static final double driveTrainGearingRatio = 8.45;
    public static final double minimumHoodAngle = 0;
    public static final double maximumHoodAngle = 0;
    public static final double stagingSpeed = 0.5;

    //Color
    public static final double redThreshold = 0.3;
    public static final double greenThreshold = 0.4;
    public static final double blueThreshold = 0.3;

    
    }
