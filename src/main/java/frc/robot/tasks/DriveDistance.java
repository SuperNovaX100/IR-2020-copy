/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Order66;
import frc.robot.Robot;
import frc.robot.subsystems.DisturbingForce;

/**
 * Add your docs here.
 */
public class DriveDistance implements TaskBase {
    private double distance;
    private double demand;
    boolean revUp;
    Order66 order66;
    DisturbingForce disturbingForce;
    private final double power;
    private double desiredAngle;

    public DriveDistance(double distance, double power) {
        this.distance = distance;
        this.power = power;
    }

    public DriveDistance(double distance, boolean revUp, Order66 order66, DisturbingForce disturbingForce, double power) {
        this.distance = distance;
        this.revUp = revUp;
        this.order66 = order66;
        this.disturbingForce = disturbingForce;
        this.power = (power);
    }

    @Override
    public void start() {
        desiredAngle = Robot.driveTrain.getAngle();
        demand = (-distance / 44.7) * 1.0434;
        System.out.print("Demand: ");
        System.out.println(demand);
        Robot.driveTrain.resetEncoders();
        if (demand < 0) {
            //power = -power;
            System.out.println("DriveDistance inverted");
        } else {
            System.out.println("DriveDistance NOT inverted");
        }
        System.out.print("Power: ");
        System.out.println(power);
        Robot.driveTrain.setMotorPower(power, power * 1.03);
        if (revUp) {
            Robot.deathStar.setOrder66(order66);
            Robot.vader.setVaderControlMode(disturbingForce);
        }
    }

    @Override
    public boolean periodic() {
        Robot.driveTrain.straightLineLoop(desiredAngle, power);
        SmartDashboard.putNumber("Auton Testing/Left Drive Encoder", Robot.driveTrain.leftEncoderFront.getPosition());
        SmartDashboard.putNumber("Auton Testing/Drive Demand", demand);
        return Math.abs(Math.abs(Robot.driveTrain.leftEncoderFront.getPosition()) - Math.abs(demand)) < 5;
    }

    @Override
    public void done() {
        System.out.println("DONE");
        Robot.driveTrain.setMotorPower(0, 0);
    }
}
