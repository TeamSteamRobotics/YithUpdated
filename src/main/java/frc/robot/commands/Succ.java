/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import static edu.wpi.first.wpilibj2.command.CommandGroupBase.sequence;

public class Succ extends CommandBase {
  /**
   * Creates a new IntakeCommand.
   */
  IntakeSubsystem intaker;
  HopperSubsystem hopper;
  BallTrackingSubsystem tracker;

  int loopsSinceClear = 0;
  int spacingLoops = 18;
  public Succ(IntakeSubsystem intaker, HopperSubsystem hopper, BallTrackingSubsystem tracker) {
    // Use addRequirements() here to declare subsystem dependencies.
    //this.intaker = intaker;
    this.hopper = hopper;
    this.tracker = tracker;
    this.intaker = intaker;
    addRequirements(intaker, hopper, tracker);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    loopsSinceClear = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intaker.intake();
    
    
    if(tracker.isBallInIntake()){
      loopsSinceClear = 0;
    }else{
      loopsSinceClear++;
    }

    if(loopsSinceClear < spacingLoops && !tracker.isBallAtTop()){
      hopper.moveToShooter();
    }
    else {
      hopper.stop();
    }

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sequence(
      new InstantCommand(() -> intaker.setSpeed(-.3)), 
      new WaitCommand(.02), 
      new InstantCommand(intaker::stopIntake)).schedule();
    //intaker.stopIntake();
    hopper.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return tracker.isHopperFull();
  }
}
