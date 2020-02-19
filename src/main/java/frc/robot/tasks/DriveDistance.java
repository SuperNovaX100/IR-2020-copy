/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class DriveDistance implements TaskBase {
    private double distance;
    public DriveDistance(double distance) {
        this.distance = distance;
    }
    @Override
    public void start() {
        Robot.driveTrain.setPIDValue(0.01, 0, 0, 0, 0); //TODO DO NOT USE THESE I REPEAT DO NOT USE THESE
    }

    @Override
    public boolean periodic() {
        return false;
    }

    @Override
    public void done() {

    }
}
