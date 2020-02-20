/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ZeroHoodMotor implements TaskBase{

    @Override
    public void start() {
        Robot.vader.setDisturbingForce(Constants.ZEROING);
    }

    @Override
    public boolean periodic() {
        
        return Robot.vader.wasLimitSwitchPressed;
    }

    @Override
    public void done() {
        Robot.vader.setDisturbingForce(Constants.STOP_DISTURBING_FORCE);
    }
}
