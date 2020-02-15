/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Subsystem;

/**
 * Add your docs here.
 */
public class ShootFromAutonLine extends Subsystem{
    private Timer autonTimer;
    private int targetDemand;
    private TalonSRX vaderMotor;

    public ShootFromAutonLine(){
        autonTimer = new Timer();

    }
    @Override
    public void autonomousInit(){
        autonTimer.reset();
        autonTimer.start();

    }
    @Override
    public void autonomousPeriodic(){
    targetDemand = -Constants.SHOOTER_AUTOLINE_SPEED;
        
    vaderMotor.set(ControlMode.Position, Constants.VADER_AUTONLINE_POSTION);


    }

}
