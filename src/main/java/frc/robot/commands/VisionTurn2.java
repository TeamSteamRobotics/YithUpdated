/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionTurn2 extends CommandBase {
  /**
   * Creates a new VisionTurn2.
   */
  VisionSubsystem vision;
  DriveSubsystem drive;
  GyroTurn gyroTurn;
  VisionTurn visionTurn;
  double[] robotCoordinates = {3,1};
  double angle;

  public VisionTurn2(DriveSubsystem drive, VisionSubsystem vision) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(vision, drive);
    this.drive = drive;
    this.vision = vision;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double[] targetCoordinates = vision.getCoordinates();
    double[] targetOffset = {targetCoordinates[0] - robotCoordinates[0], targetCoordinates[1] - robotCoordinates[1]};
    double turnAngle = Math.atan( targetOffset[1] / targetOffset[0] );
    gyroTurn = (new GyroTurn(drive, drive.getAngle() + turnAngle));
    angle = turnAngle;
    //visionTurn = new VisionTurn(drive, vision);
    //gyroTurn.schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(angle);
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    gyroTurn.cancel();
    //visionTurn.cancel();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
