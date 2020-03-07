/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

import static frc.robot.Constants.*;

/**
 * Add your docs here.
 */
public class ShootFromAutonLine implements TaskBase {
    private Timer timer;
    private Timer totalTimer;
    private boolean wasBlinkyEmpty;

    @Override
    public void start() {
        Robot.deathStar.setOrder66(AUTOLINE_ORDER_66);
        Robot.vader.setVaderControlMode(AUTOLINE_DISTURBING_FORCE);
        timer = new Timer();
        totalTimer = new Timer();
        totalTimer.start();
        Robot.blinky.wantToShoot = true;
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
        return timer.get() >= 1 || totalTimer.get() >= 12;
    }

    @Override
    public void done() {
        timer.stop();
        Robot.deathStar.setOrder66(DONT_EXECUTE_ORDER_66);
        Robot.blinky.wantToShoot = false;

    }

}
