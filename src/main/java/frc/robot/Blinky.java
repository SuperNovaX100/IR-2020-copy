/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here..
 */
public class Blinky {
    private DigitalInput[] irSensors;
    private TalonSRX[] irMotors;
    private boolean shooting;
    private TalonSRX intakeMotor;
    private Timer intakeTimer;
    private boolean wasIRTriggered;

    public Blinky() {
        //makes an array of all the IR sensors
        irSensors = new DigitalInput[5];
        irSensors[0] = new DigitalInput(Constants.IR_SENSOR_1);
        irSensors[1] = new DigitalInput(Constants.IR_SENSOR_2);
        irSensors[2] = new DigitalInput(Constants.IR_SENSOR_3);
        irSensors[3] = new DigitalInput(Constants.IR_SENSOR_4);
        irSensors[4] = new DigitalInput(Constants.IR_SENSOR_5);

        irMotors = new TalonSRX[5];
        //makes an array of all the motors in Blinky
        irMotors[0] = new TalonSRX(Constants.IR_MOTOR_1);
        irMotors[1] = new TalonSRX(Constants.IR_MOTOR_2);
        irMotors[2] = new TalonSRX(Constants.IR_MOTOR_3);
        irMotors[3] = new TalonSRX(Constants.IR_MOTOR_4);
        irMotors[4] = new TalonSRX(Constants.IR_MOTOR_5);
        irMotors[4].enableVoltageCompensation(true);
        irMotors[4].configVoltageCompSaturation(11);
        intakeMotor = new TalonSRX(Constants.INTAKE_MOTOR);

        shooting = false;
        wasIRTriggered = false;
    }

    public void teleopPeriodic() {
        if (!irSensors[0].get() && !wasIRTriggered){
            intakeTimer.start();
        } else if (irSensors[0].get() && wasIRTriggered) {
            intakeTimer.reset();
            intakeTimer.stop();
        }
        if (Robot.controllers.leftTriggerHeld()>= 0.02 && !intakeTimer.hasPeriodPassed(1.0)){
            intakeMotor.set(ControlMode.PercentOutput, -1.0);
        }
        
        wasIRTriggered = !irSensors[0].get();

        if (Robot.controllers.leftTriggerHeld()>= 0.02 || Robot.controllers.rightTriggerHeld()>= 0.02) {
            // Update intake motors 4 to 0 (5 to 1)
            SmartDashboard.putBoolean("Blinky/Ir Sensor 1", !irSensors[0].get());
            SmartDashboard.putBoolean("Ir Sensor 2", !irSensors[1].get());
            SmartDashboard.putBoolean("Ir Sensor 3", !irSensors[2].get());
            SmartDashboard.putBoolean("Ir Sensor 4", !irSensors[3].get());
            SmartDashboard.putBoolean("Ir Sensor 5", !irSensors[4].get());
            SmartDashboard.putBoolean("Shooting", shooting);
            // Set to true once one of the sensors is empty
            boolean canGo = false;
            // loops from closest sensor to the farthest sensor
            for (int i = 4; i >= 0; i--) {
                if (shooting && i == 4) {
                    irMotors[i].set(ControlMode.PercentOutput, -1.0);
                    continue;
                }
                if (canGo || irSensors[i].get()) {
                    canGo = true;
                    irMotors[i].set(ControlMode.PercentOutput, -1.0);
                } else {
                    irMotors[i].set(ControlMode.PercentOutput, 0);
                }
            }
        } else {
            for (TalonSRX motor : irMotors) {
                motor.set(ControlMode.PercentOutput, 0);
            }
        }
    }

    public boolean getShooting() {
        return shooting;
    }
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
    public boolean ballReadyToShoot() {
        return !irSensors[4].get();
    }
}
