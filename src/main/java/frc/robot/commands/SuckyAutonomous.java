/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

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
public class SuckyAutonomous extends ParallelRaceGroup {
  /**
   * Creates a new SuckyAutonomous.
   */
  public SuckyAutonomous(double amountToDrive, DriveSubsystem drive, ShooterSubsystem shooter, HopperSubsystem hopper, VisionSubsystem vision, BallTrackingSubsystem tracker, IntakeSubsystem intake) {

  
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new ManualShoot(shooter, vision),
      parallel(
        
        sequence(
          new VisionTurn(drive, vision).withTimeout(2),
          new WaitCommand(3),
          (new Shoot(shooter, hopper, tracker, intake)).withTimeout(5),
          new DriveDistance(drive, amountToDrive, .35),
          new DeployIntaker(intake).withTimeout(2)
      ))
    );
  }
}
