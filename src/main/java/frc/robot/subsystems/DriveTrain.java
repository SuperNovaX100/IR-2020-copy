/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Controllers;
import frc.robot.DriveSignal;
import frc.robot.Subsystem;


import static frc.robot.Constants.*;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
    // Motors
    private CANSparkMax leftMotorFront;
    private CANSparkMax leftMotorBack;
    private CANSparkMax rightMotorFront;
    private CANSparkMax rightMotorBack;
    public CANEncoder leftEncoderFront;
    public CANEncoder leftEncoderBack;
    public CANEncoder rightEncoderFront;
    public CANEncoder rightEncoderBack;
    private CANPIDController leftMotorFrontPID;
    private CANPIDController leftMotorBackPID;
    private CANPIDController rightMotorFrontPID;
    private CANPIDController rightMotorBackPID;
    public AHRS gyro;
    private double change = 0.0;
    private Limelight limelight;
    private double angleToTarget;
    private int counterForVision;
    private static DriveTrain instance;
    private final Controllers controllers = Controllers.getInstance();
    // Minimum amount of time to go from 0 to full throttle in open loop control
    private static final double OPEN_LOOP_TIME_TO_FULL = 0.08; 
    private static final int SMART_CURRENT_LIMIT = 45; // Amps

    private DriveTrain() {
        gyro = new AHRS(SPI.Port.kMXP);
        counterForVision = 0;
        leftMotorFront = new CANSparkMax(LEFT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        leftMotorBack = new CANSparkMax(LEFT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
        rightMotorFront = new CANSparkMax(RIGHT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        rightMotorBack = new CANSparkMax(RIGHT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
        leftEncoderFront = new CANEncoder(leftMotorFront);
        leftEncoderBack = new CANEncoder(leftMotorBack);
        rightEncoderFront = new CANEncoder(rightMotorFront);
        rightEncoderBack = new CANEncoder(rightMotorBack);
        leftMotorFrontPID = new CANPIDController(leftMotorFront);
        leftMotorBackPID = new CANPIDController(leftMotorBack);
        rightMotorFrontPID = new CANPIDController(rightMotorFront);
        rightMotorBackPID = new CANPIDController(rightMotorBack);
        leftMotorBack.follow(leftMotorFront);
        rightMotorBack.follow(rightMotorFront);
        resetEncoders();
        setPIDValue(0, 0, 0, 0, 0);
        angleToTarget = 0;

        limelight = Limelight.getInstance();



    }

    public static DriveTrain getInstance(){
        if (instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }

    public void setMotorMode(IdleMode mode) {
        leftMotorFront.setIdleMode(mode);
        leftMotorBack.setIdleMode(mode);
        rightMotorFront.setIdleMode(mode);
        rightMotorBack.setIdleMode(mode);
    }

    private void setPIDValue(double p, double i, double d, double ff, double iz) {

        leftMotorFrontPID.setP(p);
        leftMotorFrontPID.setI(i);
        leftMotorFrontPID.setD(d);
        leftMotorFrontPID.setFF(ff);
        leftMotorFrontPID.setIZone(iz);

        leftMotorBackPID.setP(p);
        leftMotorBackPID.setI(i);
        leftMotorBackPID.setD(d);
        leftMotorBackPID.setFF(ff);
        leftMotorBackPID.setIZone(iz);

        rightMotorFrontPID.setP(p);
        rightMotorFrontPID.setI(i);
        rightMotorFrontPID.setD(d);
        rightMotorFrontPID.setFF(ff);
        rightMotorFrontPID.setIZone(iz);

        rightMotorBackPID.setP(p);
        rightMotorBackPID.setI(i);
        rightMotorBackPID.setD(d);
        rightMotorBackPID.setFF(ff);
        rightMotorBackPID.setIZone(iz);
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public void resetGyro() {
        gyro.reset();
    }

    @Override
    public void generalInit() {
        resetGyro();
        rightMotorFront.setInverted(false);
        rightMotorBack.setInverted(false);
        leftMotorBack.setInverted(true);
        leftMotorFront.setInverted(true);
        setMotorPower(0, 0);

       // SmartDashboard.putNumber("DriveTrain P Values", 0);
        //SmartDashboard.putNumber("DriveTrain I Values", 0);
        //SmartDashboard.putNumber("DriveTrain D Values", 0);
        //SmartDashboard.putNumber("DriveTrain FF Values", 0);
        //SmartDashboard.putNumber("DriveTrain Iz Values", 0);
    }

    @Override
    public void teleopInit() {
        rightMotorFront.setOpenLoopRampRate(OPEN_LOOP_TIME_TO_FULL);
        rightMotorBack.setOpenLoopRampRate(OPEN_LOOP_TIME_TO_FULL);
        leftMotorFront.setOpenLoopRampRate(OPEN_LOOP_TIME_TO_FULL);
        leftMotorBack.setOpenLoopRampRate(OPEN_LOOP_TIME_TO_FULL);
        rightMotorFront.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        rightMotorBack.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        leftMotorFront.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        leftMotorBack.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
    }
    @Override
    public void autonomousInit() {
        rightMotorFront.setOpenLoopRampRate(0);
        rightMotorBack.setOpenLoopRampRate(0);
        leftMotorFront.setOpenLoopRampRate(0);
        leftMotorBack.setOpenLoopRampRate(0);
        rightMotorFront.setSmartCurrentLimit(200000);
        rightMotorBack.setSmartCurrentLimit(200000);
        leftMotorFront.setSmartCurrentLimit(200000);
        leftMotorBack.setSmartCurrentLimit(200000);
    }


    double averageLeft = 0;
    double averageRight = 0;

    /*
     * Power,LeftAvg,RightAvg 0.05,183.077,168.668 0.1,451.458,433.115
     * 0.2,970.786,909.829 0.3,1460.02,1386.026
     */
    @Override
    public void generalPeriodic() {
        /*System.out.println(gyro.getAngle());
        SmartDashboard.putNumber("DriveTrain/Left Front Encoder Value", leftEncoderFront.getPosition());
        SmartDashboard.putNumber("DriveTrain/Left Back Encoder Value", leftEncoderBack.getPosition());
        SmartDashboard.putNumber("DriveTrain/Right Front Encoder Value", rightEncoderFront.getPosition());
        SmartDashboard.putNumber("DriveTrain/Right Back Encoder Value", rightEncoderBack.getPosition());
        SmartDashboard.putNumber("DriveTrain/Left Front Velocity Value", leftEncoderFront.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Left Back Velocity Value", leftEncoderBack.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Right Front Velocity Value", rightEncoderFront.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Right Back Velocity Value", rightEncoderBack.getVelocity()); */
    }

    @Override
    public void teleopPeriodic() {
        rightMotorBack.setOpenLoopRampRate(0.08);
        DriveSignal signal = controllers.arcadeDrive();

        if (controllers.useVision()) {
            visionLoop();
        } else {
            setMotorPowerSignal(signal);
        }
    }

    public void setMotorPowerSignal(DriveSignal signal) {
        setMotorPower(signal.getLeftPower(), signal.getRightPower());
    }

    public void setMotorPower(double leftPower, double rightPower) {
        /*System.out.print(leftPower);
        System.out.print(", ");
        System.out.println(rightPower);*/
        leftMotorFront.set(leftPower);
        rightMotorFront.set(rightPower);
    }

    public void resetEncoders() {
        leftEncoderFront.setPosition(0);
        leftEncoderBack.setPosition(0);
        rightEncoderFront.setPosition(0);
        rightEncoderBack.setPosition(0);
    }

    public void goAboutThreeFeet() {
        leftMotorFrontPID.setReference(17, ControlType.kPosition);
        //SmartDashboard.putNumber("PID DriveTrain Encoders", 0);

        leftMotorBackPID.setReference(17, ControlType.kPosition);
        rightMotorFrontPID.setReference(17, ControlType.kPosition);
        rightMotorBackPID.setReference(17, ControlType.kPosition);
    }

    public void visionLoop() {
        if (limelight.seesTarget()) {

            double currentAngle = limelight.getAngleToTarget() - 1;
            change = Math.abs(currentAngle - angleToTarget);
            angleToTarget = currentAngle;
            double p = 1.0 / 90.0;
            double leftPower = -angleToTarget * p;
            double rightPower = angleToTarget * p;
            DriveSignal driveSignal = new DriveSignal(leftPower, rightPower);
            setMotorPowerSignal(driveSignal);

        } else {
            setMotorPowerSignal(new DriveSignal(0, 0));
        }
    }

    public boolean isAimedAtTarget() {
        if ((Math.abs(angleToTarget) <= 1) && change < 0.01) {
            counterForVision++;
        } else {
            counterForVision = 0;
        }
        return counterForVision >= 3;
    }

    public void straightLineLoop(double desiredAngle, double power) {
        double currentAngle = gyro.getAngle();
        double error = desiredAngle - currentAngle;
        double p = 1.0 / 90.0;
        double leftPower = -error * p + power;
        double rightPower = error * p + power;
        DriveSignal driveSignal = new DriveSignal(leftPower, rightPower * 1.03);
        setMotorPowerSignal(driveSignal);
    }

}
