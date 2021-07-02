/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Intake extends SequentialCommandGroup {
  /**
   * Creates a new IntakeTrueFalse.
   */
  public Intake(IntakeSubsystem succer, HopperSubsystem hopper, BallTrackingSubsystem tracker) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    
    super(
      new ConditionalCommand(
        //move balls to intake if necessary
        (new MoveToIntake(hopper)).withInterrupt(tracker::isBallInIntake), 
        //do nothing if necessary
        new WaitCommand(0), 
        //check for if the hopper is ready
        tracker::isBallAtTop),
      //start intake
      new Succ(succer, hopper, tracker)
    );

    
  }
}

