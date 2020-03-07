/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import frc.robot.Robot;

import static frc.robot.Constants.*;

/**
 * Add your docs here.
 */
public class ZeroHoodMotor implements TaskBase{

    @Override
    public void start() {
        Robot.vader.setVaderControlMode(ZEROING);
    }

    @Override
    public boolean periodic() {
        return Robot.vader.isZeroed();
    }

    @Override
    public void done() {
        System.out.println("Finished Zeroing");
        Robot.vader.setVaderControlMode(STOP_DISTURBING_FORCE);
    }
}
