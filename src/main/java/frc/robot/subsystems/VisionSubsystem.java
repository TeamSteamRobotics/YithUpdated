/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Arrays;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VisionTurn;

public class VisionSubsystem extends SubsystemBase {
  /**
   * Creates a new VisionSubsystem.
   */
  NetworkTableInstance table = NetworkTableInstance.getDefault();
  NetworkTable visionTable = table.getTable("photonvision").getSubTable("Microsoft_LifeCam_HD-3000");
  NetworkTableEntry poseEntry = visionTable.getEntry("targetPose");
  NetworkTableEntry yawEntry = visionTable.getEntry("targetYaw");
  NetworkTableEntry pitchEntry = visionTable.getEntry("targetPitch");
  VisionTurn visionTurn;
  int counter = 0;

 

  public VisionSubsystem(){

  }

  public double getTargetDistance(){
    double[] defaultPose = {0, 0, 0};
    double[] pose = poseEntry.getDoubleArray(defaultPose);
    return Math.hypot(pose[0], pose[1]);
    //return pitchEntry.getDouble(22);
  }
  @Override
  public void periodic() {

  }

  /**
   * returns the scaled x position of the target in the interval [-1,1].
   */
  public double getTargetX(){
    return yawEntry.getDouble(0) / 30.0;
  }

  public double getTargetAngle(){
    /*double[] defaultPose = {0, 0, 0};
    double[] pose = poseEntry.getDoubleArray(defaultPose);
    return pose[2];*/
    return yawEntry.getDouble(0);
  }
  public double[] getCoordinates(){
    double[] pose = poseEntry.getDoubleArray(new double[3]);
    return Arrays.copyOfRange(pose, 0, 2);
  }

  public boolean isNearTarget() {
    if (visionTurn.getController().atSetpoint()) {
      return true;
    } else {
      return false;
    }
  }
}
