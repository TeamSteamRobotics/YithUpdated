/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
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
public class SixBallTrenchAuto extends ParallelRaceGroup {
  /**
   * Creates a new SixBallTrenchAuto.
   */
  public SixBallTrenchAuto(boolean stayOuttaTheWay, DriveSubsystem drive, ShooterSubsystem shooter, HopperSubsystem hopper, VisionSubsystem vision, BallTrackingSubsystem tracker, IntakeSubsystem intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new ManualShoot(shooter, vision),
      sequence(
        new InstantCommand(drive::resetGyro, drive),
        new WaitCommand(.5),
        parallel(
          new DeployIntaker(intake).withTimeout(1),
          sequence(
            new GyroTurn(drive, -15).withTimeout(.25),
            new VisionTurn(drive, vision).withTimeout(1))
        ),
        (new Shoot(shooter, hopper, tracker, intake)).withTimeout(2.5),
        new GyroTurn(drive, 0).withTimeout(1),
        //new VisionTurn(drive, vision).withTimeout(2),
        race(new Intake(intake, hopper, tracker), new DriveDistance(drive, 14, .275)),
        stayOuttaTheWay ? new WaitCommand(0) : new GyroTurn(drive, -30).withTimeout(.5),
        new DriveDistance(drive, -8, .5),
        new VisionTurn(drive, vision).withTimeout(.5),
        race(new VisionTurn(drive, vision), (new Shoot(shooter, hopper, tracker, intake)).withTimeout(3)),
        new GyroTurn(drive, 0)
      )
    );
  }
}
