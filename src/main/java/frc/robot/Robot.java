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
import frc.robot.autons.TurnAuton;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.TacoTime;
import frc.robot.subsystems.Vader;
import frc.robot.autons.AutonBase;
import frc.robot.autons.Default;
import frc.robot.autons.DriveBackCalibration;
import frc.robot.autons.VisionShootAndDriveToTrench;
import frc.robot.autons.VisionShootAndGoToBrent;
import frc.robot.autons.VisionShootAndMoveFar;
import frc.robot.autons.VisionShootAndMoveForward;
import frc.robot.autons.VisionShootAndMoveOffLine;
import frc.robot.autons.ShootAndMoveFar;
import frc.robot.autons.ShootAndMoveForward;
import frc.robot.subsystems.Blinky;

public class Robot extends TimedRobot {
    public static List<Subsystem> subsystems;
    public static List<AutonBase> autons;

    // Chooser
    private final SendableChooser<String> chooser = new SendableChooser<>();
    public AutonBase autonToRun;
    // Subsystems
    private DeathStar deathStar;
    private DriveTrain driveTrain;
    private Controllers controllers;
    private Vader vader;
    private Blinky blinky;
    private ColorSensor colorSensor;
    private TacoTime tacoTime;
    private Cameras cameras;
    private String autoSelected;
    private boolean joystickDpadPressed = false;
    private Limelight limelight;
    private static final String SHOOT_AND_DRIVE_TO_TRENCH = "ShootAndDriveToTrench";
    private static final String SHOOT_AND_MOVE_OFF_LINE = "ShootAndMoveOffLine";
    private static final String DRIVE_BACK_CALIBRATION = "DriveBackCalibration";
    private static final String SHOOT_AND_GO_TO_BRENT = "ShootAndGoToBrent";
    private static final String TURN_AUTON = "TurnAuton";
    private static final String SHOOT_AND_MOVE_FORWARD = "ShootAndMoveForward";
    private static final String SHOOT_AND_MOVE_FAR = "ShootAndMoveFar";
    private static final String VISION_SHOOT_AND_DRIVE_TO_TRENCH = "VisionShootAndDriveToTrench";
    private static final String VISION_SHOOT_AND_GO_TO_BRENT = "VisionShootAndGoToBrent";
    private static final String VISION_SHOOT_AND_MOVE_FAR = "VisionShootAndMoveFar";
    private static final String VISION_SHOOT_AND_MOVE_FORWARD = "VisionShootAndMoveForward";
    private static final String VISION_SHOOT_AND_MOVE_OFF_LINE = "VisionShootAndMoveOffLine";
    private static final String DEFAULT = "Default";

    @Override
    public void robotInit() {
        subsystems = new ArrayList<>();
        autons = new ArrayList<>();
        cameras = new Cameras();
        // Subsystems
        controllers = Controllers.getInstance();
        driveTrain = DriveTrain.getInstance();
        deathStar = DeathStar.getInstance();
        vader = Vader.getInstance();
        blinky = Blinky.getInstance();
        colorSensor = ColorSensor.getInstance();
        tacoTime = TacoTime.getInstance();
        limelight = Limelight.getInstance();
        // Autons
        chooser.setDefaultOption("Default Auto", "default");
        chooser.addOption("Drive Back Calibration", DRIVE_BACK_CALIBRATION);
        chooser.addOption("Shoot and move off line", SHOOT_AND_MOVE_OFF_LINE);
        chooser.addOption("Shoot and drive to trench", SHOOT_AND_DRIVE_TO_TRENCH);
        chooser.addOption("Turn Auton", TURN_AUTON);
        chooser.addOption("Shoot and go to Brent", SHOOT_AND_GO_TO_BRENT);
        chooser.addOption("Shoot and Move Forward", SHOOT_AND_MOVE_FORWARD);
        chooser.addOption("Shoot and Move Far", SHOOT_AND_MOVE_FAR);
        chooser.addOption("Vision Shoot and drive to trench", VISION_SHOOT_AND_DRIVE_TO_TRENCH);
        chooser.addOption("Vision Shoot and go to Brent", VISION_SHOOT_AND_GO_TO_BRENT);
        chooser.addOption("Vision Shoot and Move Far", VISION_SHOOT_AND_MOVE_FAR);
        chooser.addOption("Vision Shoot and Move Forward", VISION_SHOOT_AND_MOVE_FORWARD);
        chooser.addOption("Vision Shoot and move off line", VISION_SHOOT_AND_MOVE_OFF_LINE);
        chooser.addOption("Default", DEFAULT);
        SmartDashboard.putData("Auto choices", chooser);

    }

    @Override
    public void teleopInit() {

        driveTrain.setMotorMode(IdleMode.kCoast);
        // blinky.generalInit();
        // autoToRun.done();

        vader.setVaderControlMode(new DisturbingForce(ControlMode.Position, vader.getVaderEncoder()));
        if (autonToRun != null) {
            autonToRun.done();
        }
        for (Subsystem subsystem : subsystems) {
            long startTime = System.nanoTime();
            subsystem.teleopInit();
            subsystem.generalInit();
            double timeTaken = System.nanoTime() - startTime;
            String name = subsystem.getClass().getName();
            //SmartDashboard.putNumber("Performance/TeleopInit/" + name, timeTaken / 1000000);
        }
    }

    @Override
    public void teleopPeriodic() {
        boolean zeroing = controllers.dPadLeft() || controllers.dPadRight() || controllers.dPadUp()
                || controllers.dPadDown();

        if (controllers.closePosition(true)) {
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
            vader.setVaderControlMode(new DisturbingForce(ControlMode.Position, vader.getVaderEncoder()));
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
           // SmartDashboard.putNumber("Performance/TeleopPeriodic/" + name, timeTaken / 1000000);
        }
        Limelight.getInstance().getDistance();
    }

    @Override
    public void autonomousInit() {

        driveTrain.setMotorMode(IdleMode.kBrake);

        autoSelected = chooser.getSelected();
        /*
         * for (AutonBase auton : autons) { if (auton.getName() == autoSelected) {
         * autonToRun = auton; auton.start(); } }
         */
        switch (autoSelected) {
            case SHOOT_AND_DRIVE_TO_TRENCH:
                autonToRun = new VisionShootAndDriveToTrench();
                break;
            case SHOOT_AND_MOVE_OFF_LINE:
                autonToRun = new ShootAndMoveOffLine();
                break;
            case DRIVE_BACK_CALIBRATION:
                autonToRun = new DriveBackCalibration();
                break;
            case TURN_AUTON:
                autonToRun = new TurnAuton();
                break;
            case SHOOT_AND_GO_TO_BRENT:
                autonToRun = new VisionShootAndGoToBrent();
                break;
            case SHOOT_AND_MOVE_FORWARD:
                autonToRun = new ShootAndMoveForward();
                break;
            case SHOOT_AND_MOVE_FAR:
                autonToRun = new ShootAndMoveFar();
                break;
            case VISION_SHOOT_AND_DRIVE_TO_TRENCH:
                autonToRun = new VisionShootAndDriveToTrench();
                break;
            case VISION_SHOOT_AND_GO_TO_BRENT:
                autonToRun = new VisionShootAndGoToBrent();
                break;
            case VISION_SHOOT_AND_MOVE_FAR:
                autonToRun = new VisionShootAndMoveFar();
                break;
            case VISION_SHOOT_AND_MOVE_FORWARD:
                autonToRun = new VisionShootAndMoveForward();
                break;
            case VISION_SHOOT_AND_MOVE_OFF_LINE:
                autonToRun = new VisionShootAndMoveOffLine();
                break;
            default:
                autonToRun = new Default();
                break;
        }
        
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

        autonToRun.start();
    }

    @Override
    public void autonomousPeriodic() {
        if (autonToRun != null)
            autonToRun.periodic();
        for (Subsystem subsystem : subsystems) {
            long startTime = System.nanoTime();
            subsystem.autonomousPeriodic();
            subsystem.generalPeriodic();
            double timeTaken = System.nanoTime() - startTime;
            String name = subsystem.getClass().getName();
           // SmartDashboard.putNumber("Performance/AutonomousPeriodic/" + name, timeTaken / 1000000);
        }
    }
}