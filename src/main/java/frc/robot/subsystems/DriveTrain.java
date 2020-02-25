/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ResourceBundle.Control;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public CANEncoder leftEncoderFront;
    public CANEncoder leftEncoderBack;
    public CANEncoder rightEncoderFront;
    public CANEncoder rightEncoderBack;
    private CANPIDController leftMotorFrontPID;
    private CANPIDController leftMotorBackPID;
    private CANPIDController rightMotorFrontPID;
    private CANPIDController rightMotorBackPID;
    public AHRS gyro;
    private double degreesOfTolerance = 2.0;

    public DriveTrain() {
        gyro = new AHRS(SPI.Port.kMXP);

        leftMotorFront = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        leftMotorBack = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
        rightMotorFront = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_FRONT, MotorType.kBrushless);
        rightMotorBack = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_BACK, MotorType.kBrushless);
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



    }

    public void setMotorMode(IdleMode mode){
        leftMotorFront.setIdleMode(mode);
        leftMotorBack.setIdleMode(mode);
        rightMotorFront.setIdleMode(mode);
        rightMotorBack.setIdleMode(mode);
    }

    public void setPIDValue(double p, double i, double d, double ff, double iz){

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
    public void setReference(double value, ControlType type) {
        leftMotorFrontPID.setReference(value, type);
        leftMotorBackPID.setReference(value, type);
        rightMotorFrontPID.setReference(value, type);
        rightMotorBackPID.setReference(value, type);
    }
    public void setSmartMotionParameters(double minVel, double maxVel, double maxAccel, double allowedErr, AccelStrategy accelStrategy) {
        leftMotorFrontPID.setSmartMotionMinOutputVelocity(minVel, 0);
        leftMotorFrontPID.setSmartMotionMaxVelocity(maxVel, 0);
        leftMotorFrontPID.setSmartMotionMaxAccel(maxAccel, 0);
        leftMotorFrontPID.setSmartMotionAllowedClosedLoopError(allowedErr, 0);
        leftMotorFrontPID.setSmartMotionAccelStrategy(accelStrategy, 0);

        rightMotorFrontPID.setSmartMotionMinOutputVelocity(minVel, 0);
        rightMotorFrontPID.setSmartMotionMaxVelocity(maxVel, 0);
        rightMotorFrontPID.setSmartMotionMaxAccel(maxAccel, 0);
        rightMotorFrontPID.setSmartMotionAllowedClosedLoopError(allowedErr, 0);
        rightMotorFrontPID.setSmartMotionAccelStrategy(accelStrategy, 0);

        leftMotorBackPID.setSmartMotionMinOutputVelocity(minVel, 0);
        leftMotorBackPID.setSmartMotionMaxVelocity(maxVel, 0);
        leftMotorBackPID.setSmartMotionMaxAccel(maxAccel, 0);
        leftMotorBackPID.setSmartMotionAllowedClosedLoopError(allowedErr, 0);
        leftMotorBackPID.setSmartMotionAccelStrategy(accelStrategy, 0);

        rightMotorBackPID.setSmartMotionMinOutputVelocity(minVel, 0);
        rightMotorBackPID.setSmartMotionMaxVelocity(maxVel, 0);
        rightMotorBackPID.setSmartMotionMaxAccel(maxAccel, 0);
        rightMotorBackPID.setSmartMotionAllowedClosedLoopError(allowedErr, 0);
        rightMotorBackPID.setSmartMotionAccelStrategy(accelStrategy, 0);
    }
    public double getAngle() {
        return gyro.getYaw();
    }

    @Override
    public void generalInit() {
        rightMotorFront.setInverted(false);
        rightMotorBack.setInverted(false);
        leftMotorBack.setInverted(true);
        leftMotorFront.setInverted(true);
        //setMotorPower(0,0);

        SmartDashboard.putNumber("DriveTrain P Values", 0);
        SmartDashboard.putNumber("DriveTrain I Values", 0);
        SmartDashboard.putNumber("DriveTrain D Values", 0);
        SmartDashboard.putNumber("DriveTrain FF Values", 0);
        SmartDashboard.putNumber("DriveTrain Iz Values", 0);
    }

    double averageLeft = 0;
    double averageRight = 0;
    /*
    Power,LeftAvg,RightAvg
    0.05,183.077,168.668
    0.1,451.458,433.115
    0.2,970.786,909.829
    0.3,1460.02,1386.026
    */
    @Override
    public void generalPeriodic(){
        SmartDashboard.putNumber("DriveTrain/Left Front Encoder Value", leftEncoderFront.getPosition());
        SmartDashboard.putNumber("DriveTrain/Left Back Encoder Value", leftEncoderBack.getPosition());
        SmartDashboard.putNumber("DriveTrain/Right Front Encoder Value", rightEncoderFront.getPosition());
        SmartDashboard.putNumber("DriveTrain/Right Back Encoder Value", rightEncoderBack.getPosition());
        SmartDashboard.putNumber("DriveTrain/Left Front Velocity Value", leftEncoderFront.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Left Back Velocity Value", leftEncoderBack.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Right Front Velocity Value", rightEncoderFront.getVelocity());
        SmartDashboard.putNumber("DriveTrain/Right Back Velocity Value", rightEncoderBack.getVelocity());
    }

    @Override
    public void teleopPeriodic() {
        DriveSignal signal = Robot.controllers.arcadeDrive();
        //double weight = 0.7;
        //double currentLeftValue = leftEncoderFront.getVelocity();
        //double currentRightValue = rightEncoderFront.getVelocity();
        //averageLeft = (weight * currentLeftValue) + (1 - weight) * averageLeft;
        //averageRight = (weight * currentRightValue) + (1 - weight) * averageRight;
        //double power = 0.4;
        //signal = new DriveSignal(power, power);
        //SmartDashboard.putNumber("DriveTrain/Left Power", signal.getLeftPower());
        //SmartDashboard.putNumber("DriveTrain/Right Power", signal.getRightPower());
        //SmartDashboard.putNumber("DriveTrain/Right Avg", averageRight);
        //SmartDashboard.putNumber("DriveTrain/Left Avg", averageLeft);
        setMotorPowerSignal(signal);
        /*
        boolean goForward = Robot.controllers.joystickButton3Pressed();
        boolean stop = Robot.controllers.joystickButton6Pressesd();
        if (goForward || stop){
            double p = SmartDashboard.getNumber("DriveTrain P Values", 0);
            double i = SmartDashboard.getNumber("DriveTrain I Values", 0);
            double d = SmartDashboard.getNumber("DriveTrain D Values", 0);
            double ff = SmartDashboard.getNumber("DriveTrain FF Values", 0);
            double iz = SmartDashboard.getNumber("DriveTrain Iz Values", 0);

            //resetEncoders();

            setPIDValue(p, i, d, ff, iz);

            double setPoint = goForward ? 240 : 0;
            ControlType controlType = goForward ? ControlType.kVelocity : ControlType.kDutyCycle;*/
            

        }
    

    public void setMotorPowerSignal(DriveSignal signal) {
        setMotorPower(signal.getLeftPower(), signal.getRightPower());
    }
    public void setMotorPower(double leftPower, double rightPower) {
        leftMotorFront.set(leftPower);
        rightMotorFront.set(rightPower);
    }
    public void resetEncoders(){
        leftEncoderFront.setPosition(0);
        leftEncoderBack.setPosition(0);
        rightEncoderFront.setPosition(0);
        rightEncoderBack.setPosition(0);
    }
    public void goAboutThreeFeet(){
        leftMotorFrontPID.setReference(17, ControlType.kPosition);
        SmartDashboard.putNumber("PID DriveTrain Encoders", 0);

        leftMotorBackPID.setReference(17, ControlType.kPosition);
        rightMotorFrontPID.setReference(17, ControlType.kPosition);
        rightMotorBackPID.setReference(17, ControlType.kPosition);
    }

}
