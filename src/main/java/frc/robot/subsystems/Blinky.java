/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Subsystem;

/**
 * Add your docs here..
 */
public class Blinky extends Subsystem {
    private DigitalInput[] irSensors;
    private TalonSRX[] irMotors;
    private boolean shooting;
    private TalonSRX intakeMotor;
    private DoubleSolenoid intakeDeploy;
    private boolean wasIRTriggered;
    private boolean wantToShoot;
    private boolean wantToIntake;
    private boolean blinkyBackwards;
    private boolean intakeBackwards;

    public Blinky() {
        // intake solenoids
        intakeDeploy = new DoubleSolenoid(Constants.PCM, Constants.INTAKE_DOWN, Constants.INTAKE_UP);


        // makes an array of all the IR sensors
        irSensors = new DigitalInput[5];
        irSensors[0] = new DigitalInput(Constants.IR_SENSOR_1);
        irSensors[1] = new DigitalInput(Constants.IR_SENSOR_2);
        irSensors[2] = new DigitalInput(Constants.IR_SENSOR_3);
        irSensors[3] = new DigitalInput(Constants.IR_SENSOR_4);
        irSensors[4] = new DigitalInput(Constants.IR_SENSOR_5);

        irMotors = new TalonSRX[5];
        // makes an array of all the motors in Blinky
        irMotors[0] = new TalonSRX(Constants.IR_MOTOR_1);
        irMotors[1] = new TalonSRX(Constants.IR_MOTOR_2);
        irMotors[2] = new TalonSRX(Constants.IR_MOTOR_3);
        irMotors[3] = new TalonSRX(Constants.IR_MOTOR_4);
        irMotors[4] = new TalonSRX(Constants.IR_MOTOR_5);
        irMotors[4].enableVoltageCompensation(true);
        irMotors[4].configVoltageCompSaturation(11);
        for (TalonSRX motor : irMotors) {
            motor.setNeutralMode(NeutralMode.Brake);
        }
        intakeMotor = new TalonSRX(Constants.INTAKE_MOTOR);
    }
    @Override
    public void autonomousPeriodic(){

    }
    public void generalInit(){
        blinkyBackwards = false;
        intakeBackwards = false;
        wantToIntake = false;
        wantToShoot = false;
    }

    public void generalPeriodic(){
        if (wantToIntake) {
            //System.out.println("Want To Intake");
            intakeDeploy.set(Value.kForward);
            intakeMotor.set(ControlMode.PercentOutput, 1.0);
        } else if (intakeBackwards) {
            intakeDeploy.set(Value.kReverse);
            intakeMotor.set(ControlMode.PercentOutput, -1.0);
        } else {
            intakeDeploy.set(Value.kReverse);
            intakeMotor.set(ControlMode.PercentOutput, 0.0);
        }
        
        wasIRTriggered = !irSensors[0].get();

        if (wantToIntake || wantToShoot) {
            // Set to true once one of the sensors is empty
            boolean canGo = false;
            // loops from closest sensor to the farthest sensor
            for (int i = 4; i >= 0; i--) {
                 if (shooting && i == 4) {
                    irMotors[i].set(ControlMode.PercentOutput, -0.75);
                } else if (canGo || irSensors[i].get()) {
                    canGo = true;
                    irMotors[i].set(ControlMode.PercentOutput, -0.75);
                } else {
                    irMotors[i].set(ControlMode.PercentOutput, 0);
                }
            }
        } else {
            for (TalonSRX motor : irMotors) {
                motor.set(ControlMode.PercentOutput, 0);
            }
        }
        if (blinkyBackwards){
            for (TalonSRX irMotor : irMotors) {
                irMotor.set(ControlMode.PercentOutput, 0.75);
            }
        }
    }

    @Override
    public void teleopInit() {
        shooting = false;
        wasIRTriggered = false;
    }

    @Override
    public void teleopPeriodic() {
        // deploy intake
        wantToIntake = Robot.controllers.leftTriggerHeld() >= 0.02;
        blinkyBackwards = Robot.controllers.joystickButton2();
        wantToShoot = Robot.controllers.joystickTriggerHeld();
        intakeBackwards = Robot.controllers.backButton();
        generalPeriodic();
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

    public boolean blinkyEmpty() {
        boolean activeSensor = false;
        for (DigitalInput irSensor : irSensors) {
            if (!irSensor.get()) {
                 activeSensor = true;
                 break;
            }
        }
        return !activeSensor;
    }
}
