/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.tasks.DriveDistance;
import frc.robot.tasks.TaskBase;

/**
 * Add your docs here.
 */
public class DriveBackCalibration extends AutonBase {

    public DriveBackCalibration() {
        super("Drive Back Calibration", new TaskBase[]{
            new DriveDistance(-120 * 25.4, 0.5)

        

        
        // TODO Auto-generated constructor stub
    });
    
}
}
