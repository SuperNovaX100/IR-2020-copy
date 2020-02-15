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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DeathStar;
import frc.robot.subsystems.DoNothing1;
import frc.robot.Controllers;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.TacoTime;
import frc.robot.subsystems.Vader;
import frc.robot.subsystems.AutoShootOnly;
import frc.robot.subsystems.Blinky;




public class Robot extends TimedRobot {
  public static List<Subsystem> subsystems;
  public static DeathStar deathStar; 
  public static DriveTrain driveTrain;
  public static Controllers controllers;
  public static Vader vader;
  public  static Blinky blinky;
  public static ColorSensor colorSensor;
  public static TacoTime tacoTime;
  //public static DriveOffLine driveOffLine;
  public static Cameras cameras;
  private String autoSelected;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  public static final String driveOffLineAuton = "Drive off line auton";
  public static final String shootFromAutonLineAuton = "Shoot from auton line";
  //public static ShootFromAutonLine shootFromAutonLine;
  private AutonBase autoToRun = new DoNothing1();
  @Override
  public void robotInit() {
    subsystems = new ArrayList<Subsystem>();
    cameras = new Cameras();
    //Subsystems
    controllers = new Controllers();
    driveTrain = new DriveTrain();
    deathStar = new DeathStar();
    vader = new Vader();
    blinky = new Blinky();
    colorSensor = new ColorSensor();
    tacoTime = new TacoTime();
    //driveOffLine = new DriveOffLine();
    //shootFromAutonLine = new ShootFromAutonLine();
    chooser.setDefaultOption("Default Auto", "default");
    chooser.addOption("My Auto", shootFromAutonLineAuton);
    SmartDashboard.putData("Auto choices", chooser);

  }

  @Override
  public void teleopInit() {
    //blinky.generalInit();
    autoToRun.done();
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
    autoSelected = chooser.getSelected();
    switch (autoSelected) {
      case shootFromAutonLineAuton:
        autoToRun = new AutoShootOnly();
        break;
      default:
        // Put default auto code here
        break;
    }
    autoToRun.init();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + autoSelected);
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
    autoToRun.periodic();
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.teleopPeriodic();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/AutonomousPeriodic/" + name, timeTaken / 1000000);
    }
  }
}
