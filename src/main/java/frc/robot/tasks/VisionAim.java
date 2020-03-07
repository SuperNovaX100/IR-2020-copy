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
public class VisionAim implements TaskBase{

    @Override
    public void start() {
    }

    @Override
    public boolean periodic() {
        Robot.driveTrain.visionLoop();
        return Robot.driveTrain.isAimedAtTarget();
    }

    @Override
    public void done() {
    }
}
