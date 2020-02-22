/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.tasks.DeployIntakeTrench;
import frc.robot.tasks.DriveDistance;
import frc.robot.tasks.IntakeComeBack;
import frc.robot.tasks.NavXTurnDegrees;
import frc.robot.tasks.ParallelTask;
import frc.robot.tasks.ShootFromAutonLine;
import frc.robot.tasks.TaskBase;
import frc.robot.tasks.ZeroHoodMotor;

/**
 * Add your docs here.
 */
public class ShootAndDriveToTrench extends AutonBase {

    public ShootAndDriveToTrench() {
        super("shootAndDriveToTrench", new TaskBase[] {
            new ZeroHoodMotor(),
            new ShootFromAutonLine(),
            new NavXTurnDegrees(-10),
            new DriveDistance(-84 * 25.4),
            new DeployIntakeTrench(),
            new DriveDistance(-24 * 25.4),
            new NavXTurnDegrees(19),
            new DriveDistance(-72 * 25.4),
            new IntakeComeBack()
        });
    }
}
