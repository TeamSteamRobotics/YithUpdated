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

import static frc.robot.Constants.DriveConstants.EncDrivePID;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveDistance extends PIDCommand {
  /**
   * Creates a new DriveDistance.
   */
  static double targetAngle;
  double distance;
  DriveSubsystem drive;

  public DriveDistance(DriveSubsystem drivetrain, double distance, double maxPower) {
    super(
        // The controller that the command will use
        new PIDController(EncDrivePID.kP, EncDrivePID.kI, EncDrivePID.kD),
        // This should return the measurement
        drivetrain::getDistance,
        // This should return the setpoint (can also be a constant)
        0,
        // This uses the output
        output -> {
          output = Math.min(maxPower, output);
          output = Math.max(-maxPower, output);
          double turnValue = (targetAngle - drivetrain.getAngle()) * EncDrivePID.turnkP;
          drivetrain.autoDrive(output, turnValue);
          //System.out.println(output);
        });
    
    addRequirements(drivetrain);
    drive = drivetrain;
    this.distance = distance;
    getController().setTolerance(EncDrivePID.posTolerance, EncDrivePID.velTolerance);
    //drivetrain.resetEncoders();
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  @Override
  public void initialize() {
    super.initialize();
    double position = drive.getDistance();
    m_setpoint = () -> (position + distance);
    //targetPosition = drive.getDistance() + distance;
    targetAngle = drive.getAngle();
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
    //System.out.println("pos: "+getController().getPositionError()+", vel: "+getController().getVelocityError());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
