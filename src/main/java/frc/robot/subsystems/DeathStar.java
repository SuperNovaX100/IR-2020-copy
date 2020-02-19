/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Order66;
import frc.robot.Robot;
import frc.robot.Subsystem;

/**
 * Add your docs here.
 */
public class DeathStar extends Subsystem {
    private TalonSRX irMotor5;
    private CANSparkMax shootLeftMotor;
    private CANSparkMax shootRightMotor;
    private CANEncoder shootLeftEncoder;
    private CANEncoder shootRightEncoder;
    private CANPIDController leftShootPidController;
    private CANPIDController rightShootPidController;
    private double rpmError = 0;
    private Order66 order66;

    public DeathStar() {
        order66 = Constants.DONT_EXECUTE_ORDER_66;
        irMotor5 = new TalonSRX(Constants.IR_MOTOR_5);
        shootLeftMotor = new CANSparkMax(Constants.SHOOTER_LEFT_MOTOR, MotorType.kBrushless);
        shootRightMotor = new CANSparkMax(Constants.SHOOTER_RIGHT_MOTOR, MotorType.kBrushless);
        irMotor5.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        leftShootPidController = shootLeftMotor.getPIDController();
        rightShootPidController = shootRightMotor.getPIDController();

        double kP = 0.00027;
        double kI = 0.0000015;
        double kD = 0;
        double kIz = 200;
        double kFF = 1.0 / 5400.0;
        double kMaxOutput = 1;
        double kMinOutput = -1;

        // set PID coefficients
        leftShootPidController.setP(kP);
        leftShootPidController.setI(kI);
        leftShootPidController.setD(kD);
        leftShootPidController.setIZone(kIz);
        leftShootPidController.setFF(kFF);
        leftShootPidController.setOutputRange(kMinOutput, kMaxOutput);
        rightShootPidController.setP(kP);
        rightShootPidController.setI(kI);
        rightShootPidController.setD(kD);
        rightShootPidController.setIZone(kIz);
        rightShootPidController.setFF(kFF);
        rightShootPidController.setOutputRange(kMinOutput, kMaxOutput);

        shootRightEncoder = shootRightMotor.getEncoder();
        shootLeftEncoder = shootLeftMotor.getEncoder();

    }

    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("ShooterDesiredRPM", 0);
        setOrder66(Constants.DONT_EXECUTE_ORDER_66);
        shootRightMotor.setInverted(false);
        shootLeftMotor.setInverted(true);
        leftShootPidController.setReference(0, ControlType.kVelocity);
        rightShootPidController.setReference(0, ControlType.kVelocity);
    }

    public int getShooterIntakeEncoderValue() {
        return irMotor5.getSelectedSensorPosition();
    }

    @Override
    public void autonomousPeriodic() {
        generalPeriodic();
    }

    public void generalPeriodic() {
        rpmError = Math.abs(order66.demand - shootLeftEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter/RPM Error", rpmError);
        SmartDashboard.putNumber("Left RPM", shootLeftEncoder.getVelocity());
        SmartDashboard.putNumber("Right RPM", shootRightEncoder.getVelocity());
        // Sets PID values based on the dashboard
        SmartDashboard.putNumber("Left Shooter Motor Power", shootLeftMotor.get());
        SmartDashboard.putNumber("Right Shooter Motor Power", shootRightMotor.get());
        leftShootPidController.setReference(order66.demand, order66.controlType);
        rightShootPidController.setReference(order66.demand, order66.controlType);
        if (order66.controlType == ControlType.kVelocity && Robot.blinky.ballReadyToShoot() && rpmError < 35) {
            Robot.blinky.setShooting(true);
        } else {
            Robot.blinky.setShooting(false);
        }

    }

    @Override
    public void teleopPeriodic() {
        order66 = Robot.controllers.getOrder66();
        generalPeriodic();
    }

    public void setOrder66(Order66 order66) {
        this.order66 = order66;
        if (order66.controlType == ControlType.kVelocity) {
            if (Math.abs(order66.demand) >= 5000) {
                leftShootPidController.setIZone(350);
                rightShootPidController.setIZone(350);
            } else {
                leftShootPidController.setIZone(200);
                rightShootPidController.setIZone(200);
            }
        }
    }

    public void setMotorPowers(double leftPower, double rightPower) {
        shootLeftMotor.set(leftPower);
        shootRightMotor.set(rightPower);
    }
}
