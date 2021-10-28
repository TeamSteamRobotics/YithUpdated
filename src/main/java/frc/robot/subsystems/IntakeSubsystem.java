/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  WPI_TalonSRX lowerIntakeMotor = new WPI_TalonSRX(IntakeConstants.lowerIntakeID);
  WPI_TalonSRX upperIntakeMotor = new WPI_TalonSRX(IntakeConstants.upperIntakeID);
  //WPI_TalonSRX intakeDeployer = new WPI_TalonSRX(IntakeConstants.deployMotorID);

  SpeedControllerGroup intakeMotors = new SpeedControllerGroup(lowerIntakeMotor, upperIntakeMotor);
  DoubleSolenoid doubleSolenoid = new DoubleSolenoid(0, 6, 7);
  
  public void intake(){
   intakeMotors.set(.5);
  }
  public void vomit(){
    intakeMotors.set(-.5);
  }
  
  public void deployIntake(){
    //intakeMotors.set(-.5);
    doubleSolenoid.set(Value.kForward);
    //doubleSolenoid.getFwdChannel();
  }
  public void retractIntake() {
    doubleSolenoid.set(Value.kReverse);
  }

  public boolean isDeployed() {
    if (doubleSolenoid.get() == Value.kForward) {
      return true;
    } else {
      return false;
    }
  }
  public void stopSolenoid() {
    doubleSolenoid.set(Value.kOff);
  }

  public void stopIntake(){
    intakeMotors.set(0);
  }
  public void setSpeed(double speed){
    intakeMotors.set(speed);
  }

  public void holdDeployment() {
    //intakeDeployer.set(.3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
