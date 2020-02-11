/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class TacoTime {
    Solenoid climbSolenoid;
    TalonSRX winchMotor;
    TalonSRX climbHeightMotor;
    CANSparkMax balanceMotor;

    public TacoTime() {
        climbSolenoid = new Solenoid(Constants.PCM, Constants.CLIMB_SOLENOID);
        winchMotor = new TalonSRX(Constants.WINCH_MOTOR);
        climbHeightMotor = new TalonSRX(Constants.CLIMB_HEIGHT_MOTOR);
        balanceMotor = new CANSparkMax(Constants.COLOR_WHEEL_BALANCE_MOTOR, MotorType.kBrushless);
    }

    public void teleopInit() {
        climbSolenoid.set(false);
    }

    public void teleopPeriodic() {
        climbSolenoid.set(Robot.controllers.xHeld());


        if (Robot.controllers.rightTriggerHeld() >= 0.02){
            winchMotor.set(ControlMode.PercentOutput, Robot.controllers.rightTriggerHeld());
            climbHeightMotor.set(ControlMode.PercentOutput, 0.5);
        } else{
            winchMotor.set(ControlMode.PercentOutput, 0);
        }
       
        
        if (Robot.controllers.leftBumperHeld()) {
            climbHeightMotor.set(ControlMode.PercentOutput, 1.0);
        }
        else if (Robot.controllers.rightBumperHeld()) {
            climbHeightMotor.set(ControlMode.PercentOutput, -1.0);
        }
        else {
            climbHeightMotor.set(ControlMode.PercentOutput, 0.0);
        }
        

    if(Robot.controllers.getGamepadX(Hand.kLeft)>=0.02){
        balanceMotor.set(0.5);
    }else if(Robot.controllers.getGamepadX(Hand.kLeft)<=-0.02){
        balanceMotor.set(-0.5);
    }else{
        balanceMotor.set(0);
    }

    }
}
