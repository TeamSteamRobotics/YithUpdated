



/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int leftFront = 0;
        public static final int leftBack = 1;
        public static final int rightFront = 3;
        public static final int rightBack = 2;

        public static final double feetPerTick = 7.168135E-5;

        public static final class GyroTurnPID {
            public static final double kP = .9 / 69.0;
            public static final double kI = .5 / 75;
            public static final double kD = 2.0 / 690;

            public static final double posTolerance = 2;
            public static final double velTolerance = 2;
        }

        public static final class VisionTurnPID {
            public static final double kP = 1.0 / 69.0;   //0.65;
            public static final double kI = 1.0 / 20.0;   //0.7;
            public static final double kD = -3.0 / 690.0; //0.04;

            public static final double posTolerance = 2;  //.01;
            public static final double velTolerance = 1;  //.01;
        }

        public static final class EncDrivePID {
            public static final double kP = .6;
            public static final double kI = 0;
            public static final double kD = 0.0725;

            public static final double posTolerance = 2.5 / 12;
            public static final double velTolerance = 2.0 / 12;

            public static final double turnkP = .03;
        }
        public static final class VisionDrivePID{
            public static final double kP = 1.5;
            public static final double kI = 0;
            public static final double kD = .22;
            public static final double shootDistance = 0;

            public static final double posTolerance = .1;
            public static final double velTolerance = .083333333333333333333333;

            public static final double turnkP = .05;
        }
    }

    public static final class ShooterConstants {
        public static final int masterID = 4;
        public static final int followerID = 5;
        
        public static final double clicksPerRev = 4096;

        public static final double tolerance = 450;

        public static final double trenchShotSpeed = -36000;
    }

    public static final class FeederConstants {
        public static final int feederID1 = 7;
        public static final int feederID2 = 6;
    }

    public static final class IntakeConstants {
        public static final int lowerIntakeID = 8;
        public static final int upperIntakeID = 9;
        public static final int deployMotorID = 10;
    }

    public static final class ClimbConstants {
        public static final int leftClimberID = 11;
        public static final int rightClimberID = 12;
        public static final double kP = 0.7;
        public static final double kI = 0.3;
        public static final double kD = 0.2;
        public static final double tolerance = 600;
    }

    public static final class TrackerConstants {
        public static final int topSensorPort = 3;
        public static final int bottomSensorPort = 2;
        public static final int intakeSensorPort = 1;
    }
}
