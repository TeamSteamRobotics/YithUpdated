/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ManualShoot extends CommandBase {
  /**
   * Creates a new ManualShoot.
   */
  ShooterSubsystem shooter;
  VisionSubsystem vision;
  public ManualShoot(ShooterSubsystem shooter, VisionSubsystem vision) {
    this.shooter = shooter;
    this.vision = vision;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!shooter.isShootingBall) {
      shooter.movePID(/*ShooterConstants.trenchShotSpeed);//*/shooter.getTargetSpeed(vision.getTargetDistance()));
      System.out.println("Shooter Speed is ::::: " + shooter.getTargetSpeed(vision.getTargetDistance()));
      System.out.println("Vision Distance is ::::: " + vision.getTargetDistance()); 
    } else {
      shooter.move(shooter.getPower());
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
