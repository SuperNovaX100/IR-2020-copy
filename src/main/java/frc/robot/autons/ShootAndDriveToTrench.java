/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.Constants;
import frc.robot.tasks.DeployIntakeTrench;
import frc.robot.tasks.DriveDistance;
import frc.robot.tasks.IntakeComeBack;
import frc.robot.tasks.NavXTurnDegrees;
import frc.robot.tasks.ParallelTask;
import frc.robot.tasks.Shoot;
import frc.robot.tasks.ShootFromAutonLine;
import frc.robot.tasks.TaskBase;
import frc.robot.tasks.VisionAim;
import frc.robot.tasks.ZeroHoodMotor;

/**
 * Add your docs here.
 */
public class ShootAndDriveToTrench extends AutonBase {

    public ShootAndDriveToTrench() {
        super("shootAndDriveToTrench", new TaskBase[] {
            new ZeroHoodMotor(),
            new ShootFromAutonLine(),
            new DeployIntakeTrench(),
            new DriveDistance(-120 * 25.4, 0.5),
            new NavXTurnDegrees(11.5),
            new DriveDistance(-76 * 25.4, 0.4),
            new IntakeComeBack(),
            new NavXTurnDegrees(-2),
            new DriveDistance(120 * 25.4, true, Constants.TRENCH_ORDER_66, Constants.TRENCH_POSITION, 0.6),
            new VisionAim(),
            new Shoot(),
            //new DriveDistance(120 * 25.4, true),
            //new DriveDistance(-72 * 25.4),
        });
    }
}
