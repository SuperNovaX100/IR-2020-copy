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
import frc.robot.Robot;
import frc.robot.Subsystem;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
    private TalonSRX irMotor5;
    private CANSparkMax shootLeftMotor;
    private CANSparkMax shootRightMotor;
    private CANEncoder shootLeftEncoder;
    private CANEncoder shootRightEncoder;
    private CANPIDController leftShootPidController;
    private CANPIDController rightShootPidController;
    private boolean wantToShoot = false;
    private int targetDemand;
    private boolean useKVelocity;
    private double rpmError = 0;

    public Shooter() {
        irMotor5 = new TalonSRX(Constants.IR_MOTOR_5);
        shootLeftMotor = new CANSparkMax(Constants.SHOOTER_LEFT_MOTOR, MotorType.kBrushless);
        shootRightMotor = new CANSparkMax(Constants.SHOOTER_RIGHT_MOTOR, MotorType.kBrushless);
        irMotor5.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        leftShootPidController = shootLeftMotor.getPIDController();
        rightShootPidController = shootRightMotor.getPIDController();

        SmartDashboard.putNumber("RPM Demand", 0);

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

        SmartDashboard.putNumber("Left Shooter Encoder Velocity", shootLeftEncoder.getVelocity());
        SmartDashboard.putNumber("Right Shooter Encoder Velocity", shootRightEncoder.getVelocity());
        SmartDashboard.setPersistent("Left Shooter Encoder Velocity");
        SmartDashboard.setPersistent("Right Shooter Encoder Velocity");
        SmartDashboard.putNumber("Left Shooter Motor Power", shootLeftMotor.get());
        SmartDashboard.putNumber("Right Shooter Motor Power", shootRightMotor.get());
        SmartDashboard.setPersistent("Left Shooter Motor Power");
        SmartDashboard.setPersistent("Right Shooter Motor Power");

    }

    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("ShooterDesiredRPM", 0);
        targetDemand = 0;
        useKVelocity = true;

        shootRightMotor.setInverted(true);

        leftShootPidController.setReference(0, ControlType.kVelocity);
        rightShootPidController.setReference(0, ControlType.kVelocity);

        shootLeftMotor.set(0);
        shootRightMotor.set(0);
    }

    public int getShooterIntakeEncoderValue() {
        return irMotor5.getSelectedSensorPosition();
    }

    @Override
    public void autonomousPeriodic(){

    }
    public void generalPeriodic(){
        rpmError = Math.abs(targetDemand - shootLeftEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter/RPM Error", rpmError);
        SmartDashboard.putNumber("Shooter/RPM", shootLeftEncoder.getVelocity());
        SmartDashboard.putBoolean("Shooter/Ball Ready to Shoot", Robot.blinky.ballReadyToShoot());
        SmartDashboard.putBoolean("Shooter/RPM Within Tolerance", rpmError < 35);
        SmartDashboard.putBoolean("Shooter/Joystick Trigger Held", Robot.controllers.joystickTriggerHeld());
        SmartDashboard.putNumber("Left Shooter Encoder Velocity",
                shootLeftEncoder.getVelocity()/* Constants.SHOOTER_GEAR_RATIO */);
        SmartDashboard.putNumber("Right Shooter Encoder Velocity",
                shootRightEncoder.getVelocity()/* Constants.SHOOTER_GEAR_RATIO */);
        // Sets PID values based on the dashboard
        SmartDashboard.putNumber("Left Shooter Motor Power", shootLeftMotor.get());
        SmartDashboard.putNumber("Right Shooter Motor Power", shootRightMotor.get());
        if (useKVelocity) {
            leftShootPidController.setReference(targetDemand, ControlType.kVelocity);
            rightShootPidController.setReference(targetDemand, ControlType.kVelocity);
        }else {
            setMotorPowers(0, 0);
        }
        if (wantToShoot && Robot.blinky.ballReadyToShoot() && rpmError < 35) {
            Robot.blinky.setShooting(true);
        } else {
            Robot.blinky.setShooting(false);
        }

    }

    @Override
    public void teleopPeriodic() {
        wantToShoot = Robot.controllers.joystickTriggerHeld();
        generalPeriodic();       
        if (Robot.controllers.joystickButton8()) {
            targetDemand = -Constants.SHOOTER_VERY_CLOSE_SPEED;
            useKVelocity = true;
        } else if (Robot.controllers.joystickButton7()) {
            targetDemand = -Constants.VADER_CLOSE_POSITION;
            if (Math.abs(targetDemand) >= 5000) {
                leftShootPidController.setIZone(350);
                rightShootPidController.setIZone(350);
            } else {
                leftShootPidController.setIZone(200);
                rightShootPidController.setIZone(200);
            }
            useKVelocity = true;
        } else if (Robot.controllers.joystickButton9()) {
            targetDemand = -Constants.SHOOTER_AUTOLINE_SPEED;
            useKVelocity = true;

        } else if (Robot.controllers.joystickButton11()) {
            targetDemand = -Constants.SHOOTER_TRENCH_SPEED;
            useKVelocity = true;
        } else if (Robot.controllers.joystickButton12()) {
            targetDemand = -Constants.SHOOTER_VERY_FAR_SPEED;
            useKVelocity = true;
        } else {
            useKVelocity = false;
            targetDemand = 0;
            shootLeftMotor.set(0);
            shootRightMotor.set(0);
        }
    }

    public void setMotorPowers(double leftPower, double rightPower) {
        shootLeftMotor.set(leftPower);
        shootRightMotor.set(rightPower);
    }
}
