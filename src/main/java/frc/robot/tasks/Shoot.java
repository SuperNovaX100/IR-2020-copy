/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Order66;
import frc.robot.Robot;
import frc.robot.subsystems.DisturbingForce;

import static frc.robot.Constants.*;

/**
 * Add your docs here.
 */
public class Shoot implements TaskBase{
    private Timer timer;
    private boolean wasBlinkyEmpty;
    private Order66 order66;
    private DisturbingForce disturbingForce;

    public Shoot(Order66 order66, DisturbingForce disturbingForce) {
        this.order66 = order66;
        this.disturbingForce = disturbingForce;
    }

    @Override
    public void start() {
        timer = new Timer();
        Robot.blinky.wantToShoot = true;
        Robot.deathStar.setOrder66(order66);
        Robot.vader.setVaderControlMode(disturbingForce);
    }

    @Override
    public boolean periodic() {
        if (Robot.blinky.blinkyEmpty() && !wasBlinkyEmpty) {
            timer.reset();
            timer.start();
            wasBlinkyEmpty = true;
        } else if (!Robot.blinky.blinkyEmpty() && wasBlinkyEmpty) {
            timer.stop();
            wasBlinkyEmpty = false;
        }
        return timer.get() >= 1.0;
    }

    @Override
    public void done() {
        timer.stop();
        Robot.deathStar.setOrder66(DONT_EXECUTE_ORDER_66);
        Robot.blinky.wantToShoot = false;
    }
}
