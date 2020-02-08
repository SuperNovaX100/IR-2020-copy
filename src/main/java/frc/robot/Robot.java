/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Autonomous;
import frc.robot.ColorSensor;
import frc.robot.Controllers;
import frc.robot.DriveTrain;
import frc.robot.TacoTime;
import frc.robot.Vader;
import frc.robot.Shooter;
import frc.robot.Blinky;




public class Robot extends TimedRobot { 
  public static Shooter shooter; 
  public static DriveTrain driveTrain;
  public static Controllers controllers;
  public static Vader vader;
  public  static Blinky blinky;
  public static ColorSensor colorSensor;
  public static TacoTime tacoTime;
  public static Autonomous autonomous;
 
  @Override
  public void robotInit() {
    controllers = new Controllers();
    driveTrain = new DriveTrain();
    shooter = new Shooter();
    vader = new Vader();
    blinky = new Blinky();
    colorSensor = new ColorSensor();
    tacoTime = new TacoTime();
    autonomous = new Autonomous();
   // CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    driveTrain.teleopPeriodic();
    shooter.teleopPeriodic();
    vader.teleopPeriodic();
    blinky.teleopPeriodic();
    colorSensor.teleopPeriodic();
    tacoTime.teleopPeriodic();
  }
  @Override
  public void autonomousPeriodic() {
    autonomous.autonomousPeriodic();
    // TODO Auto-generated method stub
    super.autonomousPeriodic();
  }

  @Override
  public void disabledInit() {

  }
  @Override
  public void disabledPeriodic() {

  }

}