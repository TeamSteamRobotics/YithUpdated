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
import frc.robot.subsystems.VisionSubsystem;
import static frc.robot.Constants.DriveConstants.VisionDrivePID;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class VisionDrive extends PIDCommand {
  /**
   * Creates a new VisionDrive.
   */
  static double targetAngle;
  DriveSubsystem drive;
  boolean runExecute = true;

  public VisionDrive(DriveSubsystem drive, VisionSubsystem vision) {
    this(drive, vision, VisionDrivePID.shootDistance);
  }

  public VisionDrive(DriveSubsystem drive, VisionSubsystem vision, double distance) {
    super(
        // The controller that the command will use
        new PIDController(VisionDrivePID.kP, VisionDrivePID.kI, VisionDrivePID.kD, .04),
        // This should return the measurment
        vision::getTargetDistance,
        // This should return the setpoint (can also be a constant)
        distance,
        // This uses the output
        output -> {
          double turnValue = (targetAngle - drive.getAngle()) * VisionDrivePID.turnkP;
          drive.autoDrive(-output, turnValue);
          // Use the output here
        });

    addRequirements(drive, vision);
    
    this.drive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(VisionDrivePID.posTolerance, VisionDrivePID.velTolerance);
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    targetAngle = drive.getAngle();
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    if(runExecute){
      super.execute();
    }else{
      drive.keepGoing();
    }

    runExecute = !runExecute;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
