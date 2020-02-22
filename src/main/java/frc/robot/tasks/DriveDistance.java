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
public class DriveDistance implements TaskBase {
    private double distance;
    private double demand;
    public DriveDistance(double distance) {
        this.distance = distance;
    }
    @Override
    public void start() {
        demand = distance / 46.3;
        Robot.driveTrain.resetEncoders();
        double power = 0.25;
        Robot.driveTrain.setMotorPower(power, power * 1.03);
    }

    @Override
    public boolean periodic() {
        return Math.abs(Robot.driveTrain.leftEncoderFront.getPosition() - (demand)) < 0.5;
    }

    @Override
    public void done() {
        System.out.println("DONE");
        Robot.driveTrain.setMotorPower(0, 0);
    }
}
