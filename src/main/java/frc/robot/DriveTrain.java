/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.DriveSignal;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class DriveTrain {
    //Motors
    private TalonSRX leftMotorFront;
    private TalonSRX leftMotorBack;
    private TalonSRX rightMotorFront;
    private TalonSRX rightMotorBack;

    public DriveTrain() {
        leftMotorFront = new TalonSRX(Constants.LEFT_DRIVE_MOTOR_FRONT);
        leftMotorBack = new TalonSRX(Constants.LEFT_DRIVE_MOTOR_BACK);
        rightMotorFront = new TalonSRX(Constants.RIGHT_DRIVE_MOTOR_FRONT);
        rightMotorBack = new TalonSRX(Constants.RIGHT_DRIVE_MOTOR_BACK);
        leftMotorFront.setInverted(true);
        leftMotorBack.setInverted(true);
        
       

        leftMotorBack.follow(leftMotorFront);
        rightMotorBack.follow(rightMotorFront);
    }

    

    public void teleopPeriodic() {
        DriveSignal signal = Robot.controllers.arcadeDrive();
        setMotorPowerSignal(signal);
    
    }

    public void setMotorPowerSignal(DriveSignal signal) {
        setMotorPower(signal.getLeftPower(), signal.getRightPower());
    }
    public void setMotorPower(double leftPower, double rightPower) {
        leftMotorFront.set(ControlMode.PercentOutput, leftPower);
        rightMotorFront.set(ControlMode.PercentOutput, rightPower);
    }
}
