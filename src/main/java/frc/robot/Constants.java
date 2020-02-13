/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
        public static final int IR_MOTOR_1 = 12;
        public static final int IR_MOTOR_2 = 11;
        public static final int IR_MOTOR_3 = 18;
        public static final int IR_MOTOR_4 = 10;
        public static final int IR_MOTOR_5 = 3;
        public static final int WINCH_MOTOR = 4;
        public static final int CLIMB_HEIGHT_MOTOR = 13;
        public static final int INTAKE_MOTOR = 7; //TODO
        public static final int COLOR_WHEEL_BALANCE_MOTOR = 9; //TODO
        public static final int PCM = 1;
        //Pneumatic port numbers
        public static final int CLIMB_SOLENOID = 0;
        public static final int COLOR_SOLENOID = 1; //TODO
        public static final int INTAKE_DOWN = 2;
        public static final int INTAKE_UP = 3;

        //USB Cameras
        public static final int INTAKE_CAMERA = 1;
        public static final int SHOOTER_CAMERA = 2;
        public static final int CLIMBER_CAMERA = 3;
        
        //Computer USB Ports (Name / USB port ID)
        public static final int DRIVER_JOYSTICK = 0;
        public static final int OPERATOR_GAMEPAD = 1;
        //Joystick Buttons (NAME / joystick button ID)
        public static final int JOYSTICK_TRIGGER = 1;
        //Gearing in shooter  (Name / ratio (numerator/denominator) )
        public static final double SHOOTER_GEAR_RATIO = 42/18; //numerator is big gear, denominator is smaller gear
        //Digital Input ports (Name / DIO port)
        public static final int HOOD_LIMIT_SWITCH = 0; 
        public static final int IR_SENSOR_1 = 1;
        public static final int IR_SENSOR_2 = 2;
        public static final int IR_SENSOR_3 = 3;
        public static final int IR_SENSOR_4 = 4;
        public static final int IR_SENSOR_5 = 5;
    
        //encoder experiment (Name / nightmare nightmare nightmare nightmare)
        public static final int FULL_ANGLE_ENCODER_COUNT = 50;
    
        //Hood Positions(In encoder counts) and Shooter RPMs
        public static final int VADER_LOW_POSITION = 2000;
        public static final int VADER_AUTONLINE_POSTION = 475571;
        public static final int VADER_TRENCH_POSITION = 549027;
        public static final int SHOOTER_CLOSE_SPEED = 5000; //TODO Adjust speed, this is for right next to target
        public static final int SHOOTER_TRENCH_SPEED = 4500;
        public static final int SHOOTER_AUTOLINE_SPEED = 3500;
    
        //Encoder counts per revolution
        public static final int NEO_550 = 42;
        public static final int NEO = 42;
        public static final int BAG_MOTOR_ENCODER = 1024;

        //Vision Settings
        public static final int VISION_MIN_H = 50;
        public static final int VISION_MIN_S = 205;
        public static final int VISION_MIN_V = 64;

        public static final int VISION_MAX_H = 90;
        public static final int VISION_MAX_S = 255;
        public static final int VISION_MAX_V = 255;

        //PWM Ports
        public static final int COLOR_SERVO = 0;

}
