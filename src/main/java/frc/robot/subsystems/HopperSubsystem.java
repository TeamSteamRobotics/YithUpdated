/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FeederConstants;

public class HopperSubsystem extends SubsystemBase {
  /**
   * Creates a new FeederSystem.
   */
  WPI_TalonSRX hopperMotor = new WPI_TalonSRX(FeederConstants.feederID1);
  WPI_TalonSRX hopperMotor2 = new WPI_TalonSRX(FeederConstants.feederID2);

  SpeedControllerGroup hopper = new SpeedControllerGroup(hopperMotor, hopperMotor2);

  public HopperSubsystem(){
    
  }

  public void moveToShooter(){
    hopper.set(.25);
  }
  public void moveToIntake(){     
    hopper.set(-.25);
  }
  public void stop(){
    hopper.set(0);
  }

  public void move(double speed){
    hopper.set(speed * Math.abs(speed));
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
