/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem;

import static frc.robot.Constants.*;

/**
 * Add your docs here.
 */
public class Vader extends Subsystem {
  private TalonSRX vaderMotor = new TalonSRX(HOOD_MOTOR);
  private StableSwitch vaderLimitSwitch = new StableSwitch(HOOD_LIMIT_SWITCH);
  private DisturbingForce pastDisturbingForce;

  public Vader() {
    pastDisturbingForce = STOP_DISTURBING_FORCE;
    vaderMotor.setInverted(false);
    vaderMotor.configPeakOutputForward(1.0);
    vaderMotor.configPeakOutputReverse(-1.0);
    vaderMotor.setSelectedSensorPosition(0);
    vaderMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
    vaderMotor.config_kP(0, 0.04);
    vaderMotor.config_kI(0, 0.00001);
    vaderMotor.config_kD(0, 0.0);
    vaderMotor.config_kF(0, 0);
    vaderMotor.config_IntegralZone(0, 5000);
    SmartDashboard.putNumber("Vader Demand", 0);
  }

  @Override
  public void generalInit() {
    vaderMotor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void generalPeriodic() {
    vaderLimitSwitch.periodic();
    if (vaderLimitSwitch.get()) {
      vaderMotor.configPeakOutputReverse(0.0);
      vaderMotor.setSelectedSensorPosition(0);
    } else {
      vaderMotor.configPeakOutputReverse(-1.0);
    }

    SmartDashboard.putNumber("Vader Encoder Counts", vaderMotor.getSelectedSensorPosition());
  }

  public void setVaderControlMode(DisturbingForce disturbingForce) {
    vaderMotor.set(disturbingForce.controlMode, disturbingForce.demand);
    // System.out.println("New Demand : " + String.valueOf(disturbingForce.demand));
    SmartDashboard.putNumber("Vader Demand", disturbingForce.demand);
    pastDisturbingForce = disturbingForce;
  }

  public boolean isZeroed() {
    return vaderLimitSwitch.get();
  }

  public int getVaderEncoder() {
    return vaderMotor.getSelectedSensorPosition();
  }

  public boolean isToPosition() {
    if (pastDisturbingForce.controlMode == ControlMode.Position) {
      return Math.abs(getVaderEncoder() - pastDisturbingForce.demand) <= 2500;
    }
    return true;
  }
}
