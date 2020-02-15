/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.DriveSignal;
import frc.robot.Robot;
import frc.robot.Subsystem;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
    //Motors
    private CANSparkMax leftMotorFront;
    private CANSparkMax leftMotorBack;
    private CANSparkMax rightMotorFront;
    private CANSparkMax rightMotorBack;
    private CANEncoder leftEncoderFront;
    private CANEncoder leftEncoderBack;
    private CANEncoder rightEncoderFront;
    private CANEncoder rightEncoderBack;
    
    public DriveTrain() {
        leftMotorFront = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        leftMotorBack = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
        rightMotorFront = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        rightMotorBack = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
        leftEncoderFront = new CANEncoder(leftMotorFront);
        leftEncoderBack = new CANEncoder(leftMotorBack);
        rightEncoderFront = new CANEncoder(rightMotorFront);
        rightEncoderBack = new CANEncoder(rightMotorBack);      
        
        leftMotorBack.follow(leftMotorFront);
        rightMotorBack.follow(rightMotorFront);
    }

    @Override
    public void teleopInit() {
        rightMotorFront.setInverted(false);
        rightMotorBack.setInverted(false);
        leftMotorBack.setInverted(true);
        leftMotorFront.setInverted(true);
        setMotorPower(0,0);
    }
    @Override
    public void teleopPeriodic() {
        DriveSignal signal = Robot.controllers.arcadeDrive();
        setMotorPowerSignal(signal);
    
    }

    public void setMotorPowerSignal(DriveSignal signal) {
        setMotorPower(signal.getLeftPower(), signal.getRightPower());
    }
    public void setMotorPower(double leftPower, double rightPower) {
        leftMotorFront.set(leftPower);
        rightMotorFront.set(rightPower);
    }
    
}
