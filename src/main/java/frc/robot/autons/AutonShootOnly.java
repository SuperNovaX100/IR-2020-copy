/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.autons.OldAutonBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class AutonShootOnly implements OldAutonBase{

    @Override
    public void init() {


    }

    @Override
    public void periodic() {
        Robot.controllers.autoLineShoot = true;
        Robot.vader.setDisturbingForce(Constants.AUTOLINE_DISTURBING_FORCE);
        Robot.deathStar.setOrder66(Constants.AUTOLINE_ORDER_66);
    }

    @Override
    public void done() {
        Robot.controllers.autoLineShoot = false;
        Robot.vader.setDisturbingForce(Constants.STOP_DISTURBING_FORCE);
        Robot.deathStar.setOrder66(Constants.DONT_EXECUTE_ORDER_66);
    }
    
}
