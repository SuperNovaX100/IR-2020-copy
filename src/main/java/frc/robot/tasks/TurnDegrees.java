/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class TurnDegrees implements TaskBase {
    private double degrees;

    public TurnDegrees(double degrees) {
        this.degrees = Math.abs(degrees / 360) * 34.25;
    }

    @Override
    public void start() {
        if (degrees >= 0) {
            Robot.driveTrain.setMotorPower(-0.25, 0.25);
        } else {
            Robot.driveTrain.setMotorPower(0.25, -0.25);
        }
    }

    @Override
    public boolean periodic() {
        return Robot.driveTrain.leftEncoderFront.getPosition() >= 25;
    }

    @Override
    public void done() {
        System.out.println(Robot.driveTrain.getAngle());
        Robot.driveTrain.setMotorPower(0, 0);
    }
}
