/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class NavXTurnDegrees implements TaskBase {
    private PIDController pidController;
    private double targetDegrees;
    private int withinToleranceCounter;
    private double currentDegrees;
    private double lastDegrees;

    public NavXTurnDegrees(double targetDegrees) {
        this.targetDegrees = targetDegrees;
    }

    @Override
    public void start() {
        currentDegrees = Robot.driveTrain.getAngle();
        withinToleranceCounter = 0;
        pidController = new PIDController((1.5 / 90.0), 0.03, .001);
        // pidController.setIntegratorRange(-0.2, 0.2);
        pidController.setSetpoint(targetDegrees);
        // pidController.enableContinuousInput(-180.0, 180.0);
    }

    @Override
    public boolean periodic() {
        lastDegrees = currentDegrees;
        currentDegrees = Robot.driveTrain.getAngle();
        double output = pidController.calculate(currentDegrees);
        Robot.driveTrain.setMotorPower(-output, output);

        SmartDashboard.putNumber("Auton Test/Degrees Rotated", Robot.driveTrain.getAngle());

        if (Math.abs(lastDegrees - currentDegrees) <= 0.05) {
            withinToleranceCounter++;
        } else {
            withinToleranceCounter = 0;
        }
        return withinToleranceCounter >= 3;
    }

    @Override
    public void done() {
        Robot.driveTrain.setMotorPower(0, 0);
        System.out.println("done");
    }

}
