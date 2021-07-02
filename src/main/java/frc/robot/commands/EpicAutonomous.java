/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class EpicAutonomous extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous.
   */
  public EpicAutonomous(DriveSubsystem drive, VisionSubsystem vision, IntakeSubsystem theBigSucc, HopperSubsystem hopper, BallTrackingSubsystem tracker, ShooterSubsystem shooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new InstantCommand(drive::resetEncoders, drive),
      new InstantCommand(() -> drive.configureRamping(true)),
      new InstantCommand(drive::resetGyro, drive),
      race(new ManualShoot(shooter, vision), 
       sequence(
        new ParallelRaceGroup(new Intake(theBigSucc, hopper, tracker), (new DriveDistance(drive, 4, .35)).withTimeout(3)),
        new VisionTurn(drive, vision).withTimeout(2),
        (new Shoot(shooter, hopper, tracker, theBigSucc)).withTimeout(1.5),
        new GyroTurn(drive, 0).withTimeout(2),
        new ParallelRaceGroup(new Intake(theBigSucc, hopper, tracker), (new DriveDistance(drive, 3, .35)).withTimeout(3.5)),
        (new DriveDistance(drive, -3, .35)).withTimeout(3.5),
        new VisionTurn(drive, vision).withTimeout(2),
        (new Shoot(shooter, hopper, tracker, theBigSucc)).withTimeout(2.5),
        new InstantCommand(() -> drive.configureRamping(false))))
    );
  }}
