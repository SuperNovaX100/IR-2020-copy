/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DeathStar;
import frc.robot.subsystems.DisturbingForce;
import frc.robot.autons.ShootAndMoveOffLine;
import frc.robot.Controllers;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.TacoTime;
import frc.robot.subsystems.Vader;
import frc.robot.autons.AutonBase;
import frc.robot.autons.DriveBackCalibration;
import frc.robot.autons.ShootAndDriveToTrench;
import frc.robot.subsystems.Blinky;


public class Robot extends TimedRobot {
  public static List<Subsystem> subsystems;
  public static List<AutonBase> autons;
  //Chooser
  private final SendableChooser<String> chooser = new SendableChooser<>();
  public AutonBase autonToRun;
  //Subsystems
  public static DeathStar deathStar; 
  public static DriveTrain driveTrain;
  public static Controllers controllers;
  public static Vader vader;
  public static Blinky blinky;
  public static ColorSensor colorSensor;
  public static TacoTime tacoTime;
  public static Cameras cameras;
  private String autoSelected;
  private boolean joystickDpadPressed = false;

  //public static ShootFromAutonLine shootFromAutonLine;
  //private OldAutonBase autoToRun = new DoNothing();
  @Override
  public void robotInit() {
    subsystems = new ArrayList<>();
    autons = new ArrayList<>();
    cameras = new Cameras();
    //Subsystems
    controllers = new Controllers();
    driveTrain = new DriveTrain();
    deathStar = new DeathStar();
    vader = new Vader();
    blinky = new Blinky();
    colorSensor = new ColorSensor();
    tacoTime = new TacoTime();
    //Autons
    chooser.setDefaultOption("Default Auto", "default");
    chooser.addOption("Drive Back Calibration", new DriveBackCalibration().getName());
    chooser.addOption("Shoot and move off line", new ShootAndMoveOffLine().getName());
    chooser.addOption("Shoot and drive to trench", new ShootAndDriveToTrench().getName());
    SmartDashboard.putData("Auto choices", chooser);

  }

  @Override
  public void teleopInit() {

    driveTrain.setMotorMode(IdleMode.kCoast);
    //blinky.generalInit();
    //autoToRun.done();
    

    vader.setVaderControlMode(new DisturbingForce(ControlMode.Position, Robot.vader.getVaderEncoder()));
    if (autonToRun != null) autonToRun.done();
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.teleopInit();
      subsystem.generalInit();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/TeleopInit/" + name, timeTaken / 1000000);
    }
  }

  @Override
  public void teleopPeriodic() {
    boolean zeroing = controllers.dPadLeft() || controllers.dPadRight() || controllers.dPadUp() || controllers.dPadDown();

    if (controllers.closePosition(true))  {
      vader.setVaderControlMode(Constants.CLOSE_POSITION);
    } else if (controllers.trenchPosition(true)) {
      vader.setVaderControlMode(Constants.TRENCH_POSITION);
    } else if (controllers.veryClosePosition(true)) {
      vader.setVaderControlMode(Constants.VERY_CLOSE_POSITION);
    } else if (controllers.veryFarPosition(true)) {
      vader.setVaderControlMode(Constants.VERY_FAR_POSITION);
    } else if (controllers.autoLinePosition(true)) {
      vader.setVaderControlMode(Constants.AUTOLINE_DISTURBING_FORCE);
    }
    if (controllers.joystickDPadUp()) {
      vader.setVaderControlMode(Constants.MANUAL_MODE_UP);
      joystickDpadPressed = true;
    } else if (controllers.joystickDPadDown()) {
      vader.setVaderControlMode(Constants.MANUAL_MODE_DOWN);
      joystickDpadPressed = true;
    } else if (joystickDpadPressed) {
      vader.setVaderControlMode(new DisturbingForce(ControlMode.Position, Robot.vader.getVaderEncoder()));
      joystickDpadPressed = false;
    }

    if (zeroing) {
      vader.setVaderControlMode(Constants.ZEROING);
    }
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.teleopPeriodic();
      subsystem.generalPeriodic();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/TeleopPeriodic/" + name, timeTaken / 1000000);
    }
  }
  @Override
  public void autonomousInit() {

    driveTrain.setMotorMode(IdleMode.kBrake);

    autoSelected = chooser.getSelected();
    for (AutonBase auton : autons) {
      if (auton.getName() == autoSelected) {
        autonToRun = auton;
        auton.start();
      }
    }
    /*switch (autoSelected) {
      case shootFromAutonLineAuton:
        autoToRun = new AutonShootOnly();
        break;
      case autonShootDrive:
        autoToRun = new AutonShoot();
      default:
        // Put default auto code here
        break;
    }
    autoToRun.init();*/
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + autoSelected);
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.autonomousInit();
      subsystem.generalInit();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/AutonomousInit/" + name, timeTaken / 1000000);
    }
  }
  @Override
  public void autonomousPeriodic() {
    if (autonToRun != null) autonToRun.periodic();
    for (Subsystem subsystem : subsystems) {
      long startTime = System.nanoTime();
      subsystem.autonomousPeriodic();
      subsystem.generalPeriodic();
      double timeTaken = System.nanoTime() - startTime;
      String name = subsystem.getClass().getName();
      SmartDashboard.putNumber("Performance/AutonomousPeriodic/" + name, timeTaken / 1000000);
    }
  }
}
