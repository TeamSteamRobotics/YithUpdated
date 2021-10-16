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
import frc.robot.Constants;

import static frc.robot.Constants.DriveConstants;

import java.util.Arrays;

public class DriveSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveSubsystem.
   */

  AHRS gyro = new AHRS();
/**/
  WPI_TalonFX leftFront = new WPI_TalonFX(Constants.DriveConstants.leftFront);
  WPI_TalonFX leftBack = new WPI_TalonFX(Constants.DriveConstants.leftBack);
  WPI_TalonFX rightFront = new WPI_TalonFX(Constants.DriveConstants.rightFront);
  WPI_TalonFX rightBack = new WPI_TalonFX(Constants.DriveConstants.rightBack);

  Orchestra orch = new Orchestra(Arrays.asList(leftFront, leftBack, rightFront, rightBack));
/*/
  WPI_TalonSRX leftFront = new WPI_TalonSRX(0);
  WPI_VictorSPX leftBack = new WPI_VictorSPX(0);
  WPI_TalonSRX rightFront = new WPI_TalonSRX(1);
  WPI_VictorSPX rightBack = new WPI_VictorSPX(1);
/**/
  SpeedControllerGroup left = new SpeedControllerGroup(leftBack, leftFront);
  SpeedControllerGroup right = new SpeedControllerGroup(rightBack, rightFront);

  DifferentialDrive diffDrive = new DifferentialDrive(left, right);

  public DriveSubsystem(){
    leftFront.setSensorPhase(true);
    rightFront.setSensorPhase(true);
    diffDrive.setSafetyEnabled(false);
  }
  @Override
  public void periodic() {
    //System.out.println(getDistance());
  }

  public void drive(double speed, double rotation, boolean squareInputs) {
    //diffDrive.arcadeDrive(speed, rotation, squareInputs);
    diffDrive.arcadeDrive(.6 * speed, rotation, squareInputs);
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
    return DriveConstants.feetPerTick * (leftFront.getSelectedSensorPosition() - rightFront.getSelectedSensorPosition()) / 2.0;
  }

  public void resetEncoders(){
    leftFront.setSelectedSensorPosition(0);
    rightFront.setSelectedSensorPosition(0);
  }

  public void autoDrive(double forward, double turn){
    forward += Math.copySign(.05, forward);
    turn += Math.copySign(.03, turn);
    drive(forward, turn, false);
  }

  public void keepGoing(){
    leftFront.set(leftFront.get());
    rightFront.set(rightFront.get());
  }
  public void configureRamping(boolean ramp){
    if (ramp) {
      double rampTime = .69;
      leftFront.configOpenloopRamp(rampTime);
      leftBack.configOpenloopRamp(rampTime);
      rightFront.configOpenloopRamp(rampTime);
      rightBack.configOpenloopRamp(rampTime);
    } else {
      leftFront.configOpenloopRamp(.0);
      leftBack.configOpenloopRamp(.0);
      rightFront.configOpenloopRamp(.0);
      rightBack.configOpenloopRamp(.0);
    }
  }

  public void loadMusic(String songFile){
    orch.loadMusic(songFile);
  }

  public void playMusic(){
    orch.play();
  }

  public void playTone(double frequency){
    leftFront.set(ControlMode.MusicTone, frequency);
    leftBack.set(ControlMode.MusicTone, frequency);
    rightFront.set(ControlMode.MusicTone, frequency);
    rightBack.set(ControlMode.MusicTone, frequency);
  }

  public void stop(){
    leftFront.set(0);
    leftBack.set(0);
    rightFront.set(0);
    rightBack.set(0);
  }
}
