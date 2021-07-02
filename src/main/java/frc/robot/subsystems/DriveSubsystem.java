/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;
//import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants;

import java.util.Arrays;

public class DriveSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveSubsystem.
   */

  AHRS gyro = new AHRS();
/**/
  WPI_TalonFX left1 = new WPI_TalonFX(0);
  WPI_TalonFX left2 = new WPI_TalonFX(1);
  WPI_TalonFX right1 = new WPI_TalonFX(2);
  WPI_TalonFX right2 = new WPI_TalonFX(3);

  Orchestra orch = new Orchestra(Arrays.asList(left1, left2, right1, right2));
/*/
  WPI_TalonSRX left1 = new WPI_TalonSRX(0);
  WPI_VictorSPX left2 = new WPI_VictorSPX(0);
  WPI_TalonSRX right1 = new WPI_TalonSRX(1);
  WPI_VictorSPX right2 = new WPI_VictorSPX(1);
/**/
  SpeedControllerGroup left = new SpeedControllerGroup(left1, left2);
  SpeedControllerGroup right = new SpeedControllerGroup(right1, right2);

  DifferentialDrive diffDrive = new DifferentialDrive(left, right);

  public DriveSubsystem(){
    left1.setSensorPhase(true);
    right1.setSensorPhase(true);
    diffDrive.setSafetyEnabled(false);
  }
  @Override
  public void periodic() {
    //System.out.println(getDistance());
  }

  public void drive(double speed, double rotation, boolean squareInputs) {
    //diffDrive.arcadeDrive(speed, rotation, squareInputs);
    diffDrive.arcadeDrive(speed, rotation, squareInputs);
  }

  public void curveDrive(double speed, double curvature, boolean quickTurn){
    diffDrive.curvatureDrive(speed, curvature, quickTurn);
  }

  
  public double getAngle(){
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.zeroYaw();
  }


  public double getDistance(){
    return DriveConstants.feetPerTick * (left1.getSelectedSensorPosition() - right1.getSelectedSensorPosition()) / 2.0;
  }

  public void resetEncoders(){
    left1.setSelectedSensorPosition(0);
    right1.setSelectedSensorPosition(0);
  }

  public void autoDrive(double forward, double turn){
    forward += Math.copySign(.05, forward);
    turn += Math.copySign(.03, turn);
    drive(forward, turn, false);
  }

  public void keepGoing(){
    left1.set(left1.get());
    right1.set(right1.get());
  }
  public void configureRamping(boolean ramp){
    if (ramp) {
      double rampTime = .69;
      left1.configOpenloopRamp(rampTime);
      left2.configOpenloopRamp(rampTime);
      right1.configOpenloopRamp(rampTime);
      right2.configOpenloopRamp(rampTime);
    } else {
      left1.configOpenloopRamp(.0);
      left2.configOpenloopRamp(.0);
      right1.configOpenloopRamp(.0);
      right2.configOpenloopRamp(.0);
    }
  }

  public void loadMusic(String songFile){
    orch.loadMusic(songFile);
  }

  public void playMusic(){
    orch.play();
  }

  public void playTone(double frequency){
    left1.set(ControlMode.MusicTone, frequency);
    left2.set(ControlMode.MusicTone, frequency);
    right1.set(ControlMode.MusicTone, frequency);
    right2.set(ControlMode.MusicTone, frequency);
  }

  public void stop(){
    left1.set(0);
    left2.set(0);
    right1.set(0);
    right2.set(0);
  }
}
