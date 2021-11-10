
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class ShuffleBoardSubsystem extends SubsystemBase {
  
  /** Creates a new ShuffleBoardSubsystem. */
  public ShuffleBoardSubsystem() {

    ShuffleboardLayout basic = Shuffleboard.getTab("basic")
    .getLayout("bools", BuiltInLayouts.kList)
    .withSize(1, 2); // hide labels for commands
    basic.add("RobotEnabled", false);
    basic.add("Camera Server", false);

    basic.getLayout("physical info", BuiltInLayouts.kList)
    .withSize(1, 2);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
