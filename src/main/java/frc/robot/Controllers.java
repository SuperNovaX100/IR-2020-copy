/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.DriveSignal;
import frc.robot.subsystems.DisturbingForce;

/**
 * Add your docs here.
 */
public class Controllers {
  private Joystick joystick;
  private XboxController gamepad;
  public boolean autoLineShoot = false;

  public Controllers() {
    joystick = new Joystick(Constants.DRIVER_JOYSTICK);
    gamepad = new XboxController(Constants.OPERATOR_GAMEPAD);
  }

  /**
   * 
   * @param value
   * @param deadband
   * @return
   * 
   */
  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  public DriveSignal arcadeDrive() {
    double yInput = joystick.getY();
    double xInput = joystick.getX();

    yInput = MathUtil.clamp(yInput, -1.0, 1.0);
    yInput = applyDeadband(yInput, 0.09);

    xInput = -MathUtil.clamp(xInput, -1.0, 1.0);
    xInput = applyDeadband(xInput, 0.09);

    if (joystickButton7() || joystickButton9() || joystickButton11()) {
      yInput /= 2;
      xInput /= 2;
    }
    double leftPower;
    double rightPower;

    double maxInput = Math.copySign(Math.max(Math.abs(yInput), Math.abs(xInput)), yInput);

    if (yInput >= 0.0) {
      if (xInput >= 0.0) {
        leftPower = maxInput;
        rightPower = yInput - xInput;
      } else {
        leftPower = yInput + xInput;
        rightPower = maxInput;
      }
    } else {
      if (xInput >= 0.0) {
        leftPower = yInput + xInput;
        rightPower = maxInput;
      } else {
        leftPower = maxInput;
        rightPower = yInput - xInput;
      }
    }

    leftPower = MathUtil.clamp(leftPower, -1.0, 1.0);
    rightPower = MathUtil.clamp(rightPower, -1.0, 1.0);
    DriveSignal driveSignal = new DriveSignal(leftPower, rightPower);
    // System.out.println(leftPower);
    // System.out.println(rightPower);
    return driveSignal;
  }

  public boolean joystickTriggerHeld() {
    return joystick.getRawButton(Constants.JOYSTICK_TRIGGER) || autoLineShoot;
  }

  public boolean yHeld() {
    return gamepad.getYButton();
  }

  public boolean yPressed() {
    return gamepad.getYButtonPressed();
  }

  public boolean bHeld() {
    return gamepad.getBButton();
  }

  public boolean bPressed() {
    return gamepad.getBButtonPressed();
  }

  public boolean aHeld() {
    return gamepad.getAButton();
  }

  public boolean xHeld() {
    return gamepad.getXButton();
  }

  public double getGamepadY(Hand hand) {
    return gamepad.getY(hand);
  }

  public double getGamepadX(Hand hand) {
    return gamepad.getX(hand);
  }

  public boolean leftBumperHeld() {
    return gamepad.getBumper(Hand.kLeft);
  }

  public boolean rightBumperHeld() {
    return gamepad.getBumper(Hand.kRight);
  }

  public double rightTriggerHeld() {
    return gamepad.getTriggerAxis(Hand.kRight);
  }

  public double leftTriggerHeld() {
    return gamepad.getTriggerAxis(Hand.kLeft);
  }

  public boolean dPadUp() {
    int pov = gamepad.getPOV();
    return pov == 0 || pov == 30 || pov == 330;
  }

  public boolean dPadDown() {
    int pov = gamepad.getPOV();
    return pov == 180 || pov == 150 || pov == 210;
  }

  public boolean dPadRight() {
    int pov = gamepad.getPOV();
    return pov == 90 || pov == 110 || pov == 60;
  }

  public boolean dPadLeft() {
    int pov = gamepad.getPOV();
    return pov == 270 || pov == 300 || pov == 240;
  }

  public boolean joystickButton7() {
    return joystick.getRawButton(7);
  }

  public boolean joystickButton9() {
    return joystick.getRawButton(9) || autoLineShoot;
  }

  public boolean joystickButton11() {
    return joystick.getRawButton(11);
  }

  public boolean joystickDPadUp() {
    int pov = joystick.getPOV();
    return pov == 0 || pov == 45 || pov == 315;
  }

  public boolean joystickDPadDown() {
    int pov = joystick.getPOV();
    return pov == 180 || pov == 225 || pov == 135;
  }

  public boolean joystickDPadLeft() {
    int pov = joystick.getPOV();
    return pov == 270;
  }

  public boolean joystickDPadRight() {
    int pov = joystick.getPOV();
    return pov == 90;
  }

  public boolean backButton() {
    return gamepad.getBackButton();
  }

  public boolean joystickButton2() {
    return joystick.getRawButton(2);
  }

  public boolean joystickButton8() {
    return joystick.getRawButton(8);
  }

  public boolean joystickButton12() {
    return joystick.getRawButton(12);
  }

  public DisturbingForce getDisturbingForce(boolean wasLimitSwitchPressed) {
    boolean zeroing = dPadLeft() || dPadRight() || dPadUp() || dPadDown();
    if (zeroing) {
      if (wasLimitSwitchPressed) {
        return new DisturbingForce(ControlMode.PercentOutput, 0);
      } else {
        return new DisturbingForce(ControlMode.PercentOutput, -0.50);
      }
    } else if (Robot.controllers.joystickDPadUp()) {
      return new DisturbingForce(ControlMode.PercentOutput, 0.25);
    } else if (Robot.controllers.joystickDPadDown()) {
      return new DisturbingForce(ControlMode.PercentOutput, -0.25);
    } else if (Robot.controllers.joystickButton8()) {
      return new DisturbingForce(ControlMode.Position, Constants.VADER_VERY_CLOSE_POSITION);
    } else if (Robot.controllers.joystickButton7()) {
      return new DisturbingForce(ControlMode.Position, Constants.VADER_CLOSE_POSITION);
    } else if (Robot.controllers.joystickButton9()) {
      return Constants.AUTOLINE_DISTURBING_FORCE;
    } else if (Robot.controllers.joystickButton11()) {
      return new DisturbingForce(ControlMode.Position, Constants.VADER_TRENCH_POSITION);
    } else if (Robot.controllers.joystickButton12()) {
      return new DisturbingForce(ControlMode.Position, Constants.VADER_VERY_FAR_POSITION);
    } else {
      return new DisturbingForce(ControlMode.PercentOutput, 0);
    }
  }

}
