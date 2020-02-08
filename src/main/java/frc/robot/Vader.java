/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
//import frc.robot.PID;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Vader {
   private TalonSRX vaderMotor;
   private DigitalInput hoodLimitSwitch;
   private int targetPosition;
   private Timer limitSwitchTimer;
   private boolean wasLimitSwitchPressed;



   //private PID hoodPID;
   

   public Vader() {
       vaderMotor = new TalonSRX(Constants.HOOD_MOTOR);
       hoodLimitSwitch = new DigitalInput(Constants.HOOD_LIMIT_SWITCH);
       limitSwitchTimer = new Timer();

       vaderMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30); //finds type of encoder, PID Index, time we wait for encoder
       vaderMotor.setSelectedSensorPosition(0);
       vaderMotor.set(ControlMode.Position, 0);

       vaderMotor.config_kP(0, 0.04, 30);
       vaderMotor.config_kI(0, 0.00001, 30);
       vaderMotor.config_kD(0, 0, 30);
       vaderMotor.config_IntegralZone(0, 5000, 30);
       vaderMotor.config_kF(0, 0, 30);
       vaderMotor.setInverted(true);
    
       vaderMotor.configPeakOutputForward(1.0);
       wasLimitSwitchPressed = false;

       targetPosition = 0;
   }
   int resetCounter = 0;
   public void teleopPeriodic() {
        //These three altogether make it so the hood motor resets its encoder counts when it holds the limit switch for a short amount of time
        if (hoodLimitSwitch.get() && !wasLimitSwitchPressed){
            limitSwitchTimer.start();
            //System.out.println(limitSwitchTimer.get());
            //This one makes it so the timer only starts one time per limit switch press
        }
        else if (!hoodLimitSwitch.get() && wasLimitSwitchPressed){
            vaderMotor.configPeakOutputForward(1.0);
            limitSwitchTimer.reset();
            //System.out.println("timer reset");
            limitSwitchTimer.stop();
            //this one resets the timer after we have stopped hitting the limit switch
        }
        if (limitSwitchTimer.hasPeriodPassed(0.1)) /* CHANGE TIME TO NOT 1 SECOND*/{
            //System.out.println("resetting vader" + String.valueOf(resetCounter));
            //resetCounter++;
            vaderMotor.configPeakOutputForward(0);
            vaderMotor.setSelectedSensorPosition(0);
            //this one sets the encoder counts to zero and stops the motor from going past the limit switch if the switch has been held down long enough

        if (vaderMotor.getSelectedSensorPosition() >= 720001){
            vaderMotor.configPeakOutputReverse(0);
        }
        else{
            vaderMotor.configPeakOutputReverse(1.0);
        }
        }
        wasLimitSwitchPressed = hoodLimitSwitch.get();
        int encoderPosition = -vaderMotor.getSelectedSensorPosition();

        if (Robot.controllers.joystickDPadUp()){
            vaderMotor.set(ControlMode.PercentOutput, 0.5);
        }
        else if (Robot.controllers.joystickDPadDown()) {
            vaderMotor.set(ControlMode.PercentOutput, -0.5);
        }

        else if (Robot.controllers.joystickButton7()) {
            vaderMotor.set(ControlMode.Position, -Constants.VADER_LOW_POSITION);
            targetPosition = -Constants.VADER_LOW_POSITION;
        } else if (Robot.controllers.joystickButton9()) {
            vaderMotor.set(ControlMode.Position, -Constants.VADER_MIDDLE_POSTION);
            targetPosition = -Constants.VADER_MIDDLE_POSTION;
        } else if (Robot.controllers.joystickButton11()) {
            vaderMotor.set(ControlMode.Position, -Constants.VADER_HIGH_POSITION);
            targetPosition = -Constants.VADER_HIGH_POSITION;
        } else {
            vaderMotor.set(ControlMode.PercentOutput, 0);
            targetPosition = 0;
        }
        //writes encoder counts to dashboard
        SmartDashboard.putNumber("Vader Encoder Counts", encoderPosition);
        SmartDashboard.putNumber("Vader Target Position", targetPosition);

   }

}
