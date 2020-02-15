/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.Subsystem;

/**
 * Add your docs here.
 */
public class DriveOffLine extends Subsystem {
    private Timer autonTimer;

    public DriveOffLine() {
        autonTimer = new Timer();
    }

    @Override
    public void autonomousInit(){
        autonTimer.reset();
        autonTimer.start();
    }
    
    @Override
    public void autonomousPeriodic(){
        if (autonTimer.get() < 1.0) {
            Robot.driveTrain.setMotorPower(0.25, 0.25);
        }else{
            Robot.driveTrain.setMotorPower(0, 0);
        }

    }
}

