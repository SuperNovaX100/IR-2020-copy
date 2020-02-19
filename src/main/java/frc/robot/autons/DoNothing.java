/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.autons.OldAutonBase;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class DoNothing implements OldAutonBase{

    @Override
    public void init() {

        // TODO Auto-generated method stub

    }

    @Override
    public void periodic() {
        Robot.controllers.autoLineShoot = true;

        // TODO Auto-generated method stub

    }

    @Override
    public void done() {
        Robot.controllers.autoLineShoot = false;
        // TODO Auto-generated method stub

    }
    
}
