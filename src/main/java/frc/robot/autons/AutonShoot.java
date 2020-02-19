/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.autons.OldAutonBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class AutonShoot implements OldAutonBase{
    private Timer autonDriveTimer;
   private Timer autonDrivebaseTimer;

    @Override
    public void init() {
        autonDriveTimer = new Timer();
        autonDriveTimer.start();

        autonDrivebaseTimer = new Timer();

        autonDrivebaseTimer.start();;
    }

    @Override
    public void periodic() {
        Robot.controllers.autoLineShoot = true;
        Robot.vader.setDisturbingForce(Constants.AUTOLINE_DISTURBING_FORCE);
        Robot.deathStar.setOrder66(Constants.AUTOLINE_ORDER_66);
        
        if (autonDriveTimer.get() >= 4){
            if (autonDriveTimer.get() <= 5) {
                Robot.driveTrain.setMotorPower(0.5, 0.5);
            }else{
                Robot.driveTrain.setMotorPower(0, 0);
            }
    
        }
    }

    @Override
    public void done() {
        Robot.controllers.autoLineShoot = false;
        Robot.vader.setDisturbingForce(Constants.STOP_DISTURBING_FORCE);
        Robot.deathStar.setOrder66(Constants.DONT_EXECUTE_ORDER_66);

        autonDriveTimer.stop();
    }
    
}
