/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

import static frc.robot.Constants.DriveConstants.EncDrivePID;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class HoldPosition extends PIDCommand {
  /**
   * Creates a new HoldPosition.
   */
  DriveSubsystem passDrive;

  public HoldPosition(DriveSubsystem drive) {
    super(
        // The controller that the command will use
        new PIDController(EncDrivePID.kP, EncDrivePID.kI, EncDrivePID.kD),
        // This should return the measurement
        drive::getDistance,
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          System.out.println(output);
          drive.autoDrive(output, 0);
        });
    addRequirements(drive);
    passDrive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    double position = passDrive.getDistance();
    m_setpoint = () -> position;
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
