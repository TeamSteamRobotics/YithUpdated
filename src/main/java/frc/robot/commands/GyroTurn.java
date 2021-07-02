/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveSubsystem;
import static frc.robot.Constants.DriveConstants.GyroTurnPID;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class GyroTurn extends PIDCommand {
  /**
   * Creates a new GyroTurn2.
   */
  public GyroTurn(DriveSubsystem drive, double target) {
    super(
        // The controller that the command will use
        new PIDController(GyroTurnPID.kP, GyroTurnPID.kI, GyroTurnPID.kD),
        // This should return the measurement
        drive::getAngle,
        // This should return the setpoint (can also be a constant)
        target,
        // This uses the output
        output -> {
          drive.drive(0, output, false);
          //System.out.println(drive.getAngle());
        });
    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(GyroTurnPID.posTolerance, GyroTurnPID.velTolerance);
  }

  @Override
  public void execute() {
    super.execute();
    System.out.println("pos: "+getController().getPositionError()+", vel: "+getController().getVelocityError());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
