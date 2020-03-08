/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ColorMatch;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.DisturbingForce;

/**
 * Add your docs here.
 */
public class Constants {
    //CAN IDs
    public static final int LEFT_DRIVE_MOTOR_FRONT = 17;
    public static final int LEFT_DRIVE_MOTOR_BACK = 6;
    public static final int RIGHT_DRIVE_MOTOR_FRONT = 2;
    public static final int RIGHT_DRIVE_MOTOR_BACK = 5;
    public static final int SHOOTER_LEFT_MOTOR = 14;
    public static final int SHOOTER_RIGHT_MOTOR = 15;
    public static final int HOOD_MOTOR = 8;
    public static final int IR_MOTOR_2 = 11; //PDP Port 8
    public static final int IR_MOTOR_3 = 18; //PDP Port 9
    public static final int IR_MOTOR_4 = 10; //
    public static final int IR_MOTOR_5 = 3;
    public static final int WINCH_MOTOR = 4;
    public static final int CLIMB_HEIGHT_MOTOR = 13;
    public static final int INTAKE_MOTOR = 7;
    public static final int COLOR_WHEEL_BALANCE_MOTOR = 9;
    public static final int PCM = 1;
    //Pneumatic port numbers
    public static final int CLIMB_SOLENOID = 0;
    public static final int COLOR_SOLENOID = 1;
    public static final int INTAKE_DOWN = 2;
    public static final int INTAKE_UP = 3;

    //Computer USB Ports (Name / USB port ID)
    public static final int DRIVER_JOYSTICK = 0;
    public static final int OPERATOR_GAMEPAD = 1;
    //Joystick Buttons (NAME / joystick button ID)
    public static final int JOYSTICK_TRIGGER = 1;
    //Drive base gear ratio
    public static final double DRIVE_BASE_GEAR_RATIO = 10.71;
    //Digital Input ports (Name / DIO port)
    public static final int HOOD_LIMIT_SWITCH = 0;
    public static final int IR_SENSOR_1 = 1;
    public static final int IR_SENSOR_2 = 2;
    public static final int IR_SENSOR_3 = 3;
    public static final int IR_SENSOR_4 = 4;
    public static final int IR_SENSOR_5 = 5;

    //Encoder counts per revolution
    public static final int NEO_550_ENCODER = 42;
    public static final int NEO_ENCODER = 42;
    public static final int BAG_MOTOR_ENCODER = 1024;

    public static final double CIRCUMFERENCE_OF_WHEEL = (6 * 25.4) * Math.PI;
    public static final double DISTANCE_PER_COUNT_MILLIMETERS = (CIRCUMFERENCE_OF_WHEEL / NEO_ENCODER) / DRIVE_BASE_GEAR_RATIO;

    //PWM Ports
    public static final int COLOR_SERVO = 0;

    // Vader Setpoints
    public static final DisturbingForce STOP_DISTURBING_FORCE = new DisturbingForce(ControlMode.PercentOutput, 0);
    public static final DisturbingForce ZEROING = new DisturbingForce(ControlMode.PercentOutput, -0.5);
    public static final DisturbingForce MANUAL_MODE_UP = new DisturbingForce(ControlMode.PercentOutput, 0.25);
    public static final DisturbingForce MANUAL_MODE_DOWN = new DisturbingForce(ControlMode.PercentOutput, -0.25);
    public static final DisturbingForce VERY_CLOSE_POSITION = new DisturbingForce(ControlMode.Position, 185000);
    public static final DisturbingForce CLOSE_POSITION = new DisturbingForce(ControlMode.Position, 235000);
    public static final DisturbingForce AUTOLINE_DISTURBING_FORCE = new DisturbingForce(ControlMode.Position, 450000);
    public static final DisturbingForce TRENCH_POSITION = new DisturbingForce(ControlMode.Position, 510000);
    public static final DisturbingForce VERY_FAR_POSITION = new DisturbingForce(ControlMode.Position, 505000);
    //Death Star Speed
    public static final Order66 VERY_CLOSE_ORDER_66 = new Order66(ControlType.kVelocity, 2900);
    public static final Order66 CLOSE_ORDER_66 = new Order66(ControlType.kVelocity, 2570);
    public static final Order66 AUTOLINE_ORDER_66 = new Order66(ControlType.kVelocity, 3500);
    public static final Order66 TRENCH_ORDER_66 = new Order66(ControlType.kVelocity, 4800);
    public static final Order66 VERY_FAR_ORDER_66 = new Order66(ControlType.kVelocity, 5300);
    public static final Order66 DONT_EXECUTE_ORDER_66 = new Order66(ControlType.kDutyCycle, 0);

    //LIMELIGHT
    public static final double H1 = 26 * 25.4;
    public static final double H2BOTTOM = 80.75 * 25.4;
    public static final double H2TOP = 97.75 * 25.4;
    public static final double H2CENTER = ((H2BOTTOM + H2TOP) / 2.0);
    public static final double A1 = 27.2 - 1.43372754228168;

    //Color Sensor
    public static final Color SQUIRTLE_TARGET_COLOR = ColorMatch.makeColor(.15, .44, .39); // blue
    public static final Color CHARMANDER_TARGET_COLOR = ColorMatch.makeColor(.48, .36, .15); // red
    public static final Color BULBASAUR_TARGET_COLOR = ColorMatch.makeColor(.18, .54, .25); // green
    public static final Color PIKACHU_TARGET_COLOR = ColorMatch.makeColor(.31, .54, .13); // yellow
    public static final I2C.Port COLOR_SENSOR_I2C_PORT = I2C.Port.kOnboard;

    public static final double COLOR_SERVO_DOWN = 0.95;
    public static final double COLOR_SERVO_UP = 0.15;

    //Blinky
    public static final double[] INTAKE_POWER = new double[]{-1.0, -0.7, -0.7, -0.7, -0.7};
    public static final double[] INTAKE_REVERSE_POWER = new double[]{1.0, 0.75, 0.75, 0.75, 0.75};

    //Death Star Misc
    public static final int DEATH_STAR_TOLERANCE = 500;
}
