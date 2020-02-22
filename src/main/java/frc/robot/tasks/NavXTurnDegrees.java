/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class NavXTurnDegrees implements TaskBase {
    private PIDController pidController;
    private double targetDegrees;

    public NavXTurnDegrees(double targetDegrees) {
        this.targetDegrees = targetDegrees;
    }

    @Override
    public void start() {
        pidController = new PIDController(0.01, 0.0, 0.0);
        pidController.setIntegratorRange(0.0, 0.0);
        pidController.setSetpoint(targetDegrees);
        pidController.enableContinuousInput(-180.0, 180.0);
    }

    @Override
    public boolean periodic() {
       double currentDegrees = Robot.driveTrain.getAngle();
       double output = pidController.calculate(currentDegrees) / 180;
       Robot.driveTrain.setMotorPower(-output, output);
       return Math.abs(targetDegrees - currentDegrees) <= 2;
    }

    @Override
    public void done() {
        Robot.driveTrain.setMotorPower(0, 0);
    }

}
