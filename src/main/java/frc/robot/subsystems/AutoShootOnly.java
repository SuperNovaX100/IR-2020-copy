/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.AutonBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class AutoShootOnly implements AutonBase{

    @Override
    public void init() {


    }

    @Override
    public void periodic() {
        Robot.controllers.autoLineShoot = true;
        Robot.vader.setDisturbingForce(Constants.AUTOLINE_DISTURBING_FORCE);
    }

    @Override
    public void done() {
        Robot.controllers.autoLineShoot = false;
    }
    
}
