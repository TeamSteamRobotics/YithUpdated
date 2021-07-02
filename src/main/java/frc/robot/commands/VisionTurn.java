/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.BootlegPID;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import static frc.robot.Constants.DriveConstants.VisionTurnPID;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class VisionTurn extends PIDCommand {
  /**
   * Creates a new GyroTurn2.
   */
  private int runExecute = 0;

  public VisionTurn(DriveSubsystem drive, VisionSubsystem vision) {
    super(
        // The controller that the command will use
        new BootlegPID(VisionTurnPID.kP, VisionTurnPID.kI, VisionTurnPID.kD, .02, drive),
        // This should return the measurement
        () -> -vision.getTargetAngle(),//.getTargetX(),
        // This should return the setpoint (can also be a constant)
        0,
        // This uses the output
        output -> {
          drive.drive(0, output, false);
          //drive.autoDrive(0, output);
          //System.out.println(getController());
        });

    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(VisionTurnPID.posTolerance, VisionTurnPID.velTolerance);
  }


  @Override
  public void execute() {
    // TODO Auto-generated method stub
    System.out.println("Target Offset :::::: " + getController().getPositionError());
    if(true){//runExecute >= 3){
      //System.out.println(getController().getPositionError());
      if (Math.abs(getController().getPositionError()) < 20) {
        getController().setI(VisionTurnPID.kI);
      } else {
        getController().setI(0);
      }
      super.execute();
      runExecute = 0;
    }
    runExecute += 1;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
