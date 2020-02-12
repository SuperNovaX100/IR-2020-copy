/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.Robot;

import com.revrobotics.ColorMatchResult;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;


import edu.wpi.first.wpilibj.DriverStation;

/**
 * Add your docs here.
 */
public class ColorSensor {
  private final char[] colors = new char[] {'R', 'G', 'B', 'Y'};
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor;
  private final ColorMatch colorMatcher;
  private final Color squirtleTarget; //blue
  private final Color charmanderTarget; //red
  private final Color bulbasaurTarget; //green
  private final Color pikachuTarget; //yellow
  private int changes;
  private char gameData;

  private String lastColorString;

  private Timer timer;
  public boolean colorSpinManualMode;

  //Spin Color Wheel
  Solenoid colorWheelSolenoid;
  CANSparkMax colorWheelMotor;
  CANEncoder colorWheelEncoder;
    
  public ColorSensor(){
      changes = 0;
      //Wheel Turn
      colorWheelSolenoid = new Solenoid(Constants.PCM, Constants.COLOR_SOLENOID);
      colorWheelMotor = new CANSparkMax(Constants.COLOR_WHEEL_BALANCE_MOTOR, MotorType.kBrushless);
      colorWheelEncoder = colorWheelMotor.getEncoder();
      //Color Position Turn
      colorSensor = new ColorSensorV3(i2cPort);
      colorMatcher = new ColorMatch();
      squirtleTarget = ColorMatch.makeColor(.15, .44, .39);
      charmanderTarget = ColorMatch.makeColor(.48, .36, .15);
      bulbasaurTarget = ColorMatch.makeColor(.18, .54, .25);
      pikachuTarget = ColorMatch.makeColor(.31, .54, .13);
      colorMatcher.addColorMatch(squirtleTarget);
      colorMatcher.addColorMatch(charmanderTarget);
      colorMatcher.addColorMatch(bulbasaurTarget);
      colorMatcher.addColorMatch(pikachuTarget);
      

      timer = new Timer();
    }

    public void teleopInit() {
      colorSpinManualMode = false;
      changes = 0;
      colorWheelSolenoid.set(false);
      timer.stop();
      timer.reset();
      colorWheelMotor.set(0);
    }
    public void teleopPeriodic() {
      

      //Wheel Turn
      colorWheelSolenoid.set(Robot.controllers.aHeld());

      //Color Position Turn
      /*if(gameData.length() > 0){
        switch (gameData.charAt(0)){
          case 'B' :
          break;
          case 'G' :
          break;
          case 'R' :
          break;
          case 'Y' :
          break;
          default :
          break;
        }
      } else{}*/

        Color detectedColor = colorSensor.getColor();
        double IR = colorSensor.getIR();
        SmartDashboard.putNumber("Color Sensor/Red", detectedColor.red);
        SmartDashboard.putNumber("Color Sensor/Green", detectedColor.green);
        SmartDashboard.putNumber("Color Sensor/Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", IR);
        String colorString;
        char fieldColor = 'U';
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        //colorString is what our color sensor is seeing, fieldColor is what the field should be seeing if we are seeing a certain color
        if (match.color == squirtleTarget) {
          colorString = "Blue";
          fieldColor = 'R';
        }else if (match.color == charmanderTarget){
          colorString = "Red";
          fieldColor = 'B';

        }else if (match.color == bulbasaurTarget){
          colorString = "Green";
          fieldColor = 'Y';
        }else if (match.color == pikachuTarget){
          colorString = "Yellow";
          fieldColor = 'G';
        }else{
          colorString = "Unknown";
        }
        SmartDashboard.putNumber("Color Sensor/Confidence", match.confidence);
        SmartDashboard.putString("Color Sensor/Detected Color", colorString);

        int leftDistance = 0;
        int rightDistance = 0;
        if (fieldColor != 'U') {
          int startPosition = 0;
          for (int i = 0; i < 4; i++) {
              if (colors[i] == fieldColor) {
                  startPosition = i;
              }
          }

          int position = startPosition;
          for (int i = 0; i < 4; i++) {
              if (gameData == colors[position]) {
                  break;
              }
              position += 1;
              leftDistance += 1;
              if (position > 3) {
                  position = 0;
              }
          }
          position = startPosition;
          for (int i = 0; i < 4; i++) {
              if (gameData == colors[position]) {
                  break;
              }
              position -= 1;
              rightDistance += 1;
              if (position < 0) {
                  position = 3;
              }
          }
          SmartDashboard.putBoolean("Color Sensor/Direction", rightDistance > leftDistance);
      }
      SmartDashboard.putNumber("Color Sensor/Confidence", match.confidence);
      SmartDashboard.putString("Color Sensor/Detected Color", colorString);
      if (timer.hasPeriodPassed(0.1)) {
          changes += 1;
          timer.stop();
          timer.reset();
      }
      if (lastColorString != colorString) {
        timer.reset();
        timer.start();
        lastColorString = colorString;
      }
      SmartDashboard.putNumber("Color Sensor/Changes", changes);

      if (Robot.controllers.bPressed()){
        gameData = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
        colorSpinManualMode = false;
      }
      if(Robot.controllers.yPressed()){
        colorSpinManualMode = false;
        changes = 0;
      }
      if (Robot.controllers.yHeld() && changes < 32){
        colorWheelMotor.set(1.0);
      }

      //this sees if the fieldColor(based off of our colorString) is equal to the color FMS is giving us
       else if (Robot.controllers.bHeld() && fieldColor != gameData ) {
          colorWheelMotor.set(rightDistance > leftDistance ? 1 : -1);
        } else {
          colorWheelMotor.set(0);
        }
        if (Robot.controllers.getGamepadX(Hand.kRight) >= 0.02){
          colorSpinManualMode = true;
          colorWheelMotor.set(Robot.controllers.getGamepadX(Hand.kRight));
        }
  }
}

