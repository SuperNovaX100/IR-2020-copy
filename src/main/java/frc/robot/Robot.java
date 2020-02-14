/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Autonomous;
import frc.robot.subsystems.ColorSensor;
import frc.robot.Controllers;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.TacoTime;
import frc.robot.subsystems.Vader;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Blinky;




public class Robot extends TimedRobot {
  public static List<Subsystem> subsystems;
  public static Shooter shooter; 
  public static DriveTrain driveTrain;
  public static Controllers controllers;
  public static Vader vader;
  public  static Blinky blinky;
  public static ColorSensor colorSensor;
  public static TacoTime tacoTime;
  public static Autonomous autonomous;
  public static Cameras cameras;
 
  @Override
  public void robotInit() {
    subsystems = new ArrayList<Subsystem>();
    cameras = new Cameras();
    //Subsystems
    controllers = new Controllers();
    driveTrain = new DriveTrain();
    shooter = new Shooter();
    vader = new Vader();
    blinky = new Blinky();
    colorSensor = new ColorSensor();
    tacoTime = new TacoTime();
    autonomous = new Autonomous();
  }

  @Override
  public void teleopInit() {
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.teleopInit();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/TeleopInit/" + name, timeTaken / 1000000);
    }
  }

  @Override
  public void teleopPeriodic() {
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.teleopPeriodic();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/TeleopPeriodic/" + name, timeTaken / 1000000);
    }
  }
  @Override
  public void autonomousInit() {
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.autonomousInit();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/AutonomousInit/" + name, timeTaken / 1000000);
    }
  }
  @Override
  public void autonomousPeriodic() {
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.autonomousPeriodic();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/AutonomousPeriodic/" + name, timeTaken / 1000000);
    }
  }
}