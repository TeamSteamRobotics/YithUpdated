/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.XboxControllerConstants;
import frc.robot.commands.DeployIntaker;
import frc.robot.commands.Drive;
import frc.robot.commands.Intake;
import frc.robot.commands.ManualShoot;
import frc.robot.commands.MoveToIntake;
import frc.robot.commands.MoveToShooter;
import frc.robot.commands.RetractIntaker;
import frc.robot.commands.SpinIntake;
import frc.robot.commands.VisionTurn;
import frc.robot.commands.Vomit;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShuffleBoardSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  
	//private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final HopperSubsystem m_feederSubsystem = new HopperSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final BallTrackingSubsystem m_ballTrackingSubsystem = new BallTrackingSubsystem();
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  private final ShuffleBoardSubsystem m_shuffleBoardSubsystem = new ShuffleBoardSubsystem();
  
  


  Joystick stick = new Joystick(0);
  XboxController xboxController = new XboxController(1);

  JoystickButton aimButton = new JoystickButton(xboxController, 3);
  JoystickButton shootButton = new JoystickButton(stick, JoystickConstants.trigger);
  TriggerButton intakeButton = new TriggerButton(xboxController, Hand.kLeft); //left trigger
  JoystickButton spinUpButton = new JoystickButton(stick, JoystickConstants.thumbButton);

  //manual overrides
  POVButton manualIntakeButton = new POVButton(xboxController, XboxControllerConstants.leftPOV); //left POV
  POVButton moveToShooterButton = new POVButton(xboxController, XboxControllerConstants.upPOV); //top POV
  POVButton moveToIntakeButton = new POVButton(xboxController, XboxControllerConstants.downPOV); //bottom POV

  JoystickButton vomitButton = new JoystickButton(xboxController, XboxControllerConstants.buttonB); //red b button
  JoystickButton deployIntakeButton = new JoystickButton(stick, JoystickConstants.baseButton7); //bottom analog touchdown
  JoystickButton retractIntakeButton = new JoystickButton(stick, JoystickConstants.baseButton8);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    m_driveSubsystem.setDefaultCommand(new Drive(m_driveSubsystem, stick::getY, stick::getX));
    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    aimButton.whileHeld(new VisionTurn(m_driveSubsystem, m_visionSubsystem));
     
    intakeButton.whileHeld(new ConditionalCommand(
      new WaitCommand(0), 
      new Intake(m_intakeSubsystem, m_feederSubsystem, m_ballTrackingSubsystem)
        .raceWith(new StartEndCommand(
          () -> xboxController.setRumble(RumbleType.kRightRumble, 0), 
          () -> xboxController.setRumble(RumbleType.kRightRumble, 0)
        )),
      m_ballTrackingSubsystem::isHopperFull
    ));
    spinUpButton.toggleWhenActive(new ManualShoot(m_shooterSubsystem, m_visionSubsystem));
    spinUpButton.toggleWhenActive(new StartEndCommand(
      () -> xboxController.setRumble(RumbleType.kLeftRumble, 1), 
      () -> xboxController.setRumble(RumbleType.kLeftRumble, 0)
    ));

    //manual overrides
    moveToIntakeButton.whileHeld(new MoveToIntake(m_feederSubsystem));
    moveToShooterButton.whileHeld(new MoveToShooter(m_feederSubsystem));
    manualIntakeButton.whileHeld(new SpinIntake(m_intakeSubsystem));


    vomitButton.toggleWhenPressed(
      new ConditionalCommand(
        new RetractIntaker(m_intakeSubsystem).andThen(new Vomit(m_intakeSubsystem).alongWith(new MoveToIntake(m_feederSubsystem))), //if the intake IS deployed, retract it and vomit
        new Vomit(m_intakeSubsystem).alongWith(new MoveToIntake(m_feederSubsystem)), //if the intake ISN'T deployed, then just vomit
        m_intakeSubsystem :: isDeployed //the condition that checks if the intake is deployed
      )
    );
     ;

    deployIntakeButton.whileHeld(new DeployIntaker(m_intakeSubsystem));
    retractIntakeButton.whileHeld(new RetractIntaker(m_intakeSubsystem));
    
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
