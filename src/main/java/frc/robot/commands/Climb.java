// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.ClimbConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ClimbSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Climb extends PIDCommand {
  /** Creates a new ClimbUpPID. */
  ClimbSubsystem climb;
  double climbHeight;
  public Climb(ClimbSubsystem climb, double climbHeight) {
    super(
        // The controller that the command will use
        new PIDController(ClimbConstants.kP, ClimbConstants.kI, ClimbConstants.kD),
        // This should return the measurement
        climb :: getClimbHeight,
        // This should return the setpoint (can also be a constant)
        climbHeight,
        // This uses the output
        output -> {
          // Use the output here
          climb.setSpeed(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climb);
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return (getController().getPositionError()) < ClimbConstants.tolerance;
    return getController().atSetpoint();
    //return false;
  }
}
