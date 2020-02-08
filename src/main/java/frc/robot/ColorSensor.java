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
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor;
  private final ColorMatch colorMatcher;
  private final Color squirtleTarget; //blue
  private final Color charmanderTarget; //red
  private final Color bulbasaurTarget; //green
  private final Color pikachuTarget; //yellow
  String gameData;

  //Spin Color Wheel
  Solenoid colorWheelSolenoid;
  CANSparkMax colorWheelMotor;
  CANEncoder colorWheelEncoder;
    
  public ColorSensor(){
    //Wheel Turn
    colorWheelSolenoid = new Solenoid(Constants.PCM, Constants.COLOR_SOLENOID_UP);
    colorWheelMotor = new CANSparkMax(Constants.COLOR_WHEEL_MOTOR, MotorType.kBrushless);
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
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    }
    public void teleopPeriodic(){

      //Wheel Turn
      if (Robot.controllers.dPadLeft()){
        colorWheelSolenoid.set(true);
    }
    if (Robot.controllers.dPadRight()){
        colorWheelSolenoid.set(false);        
    }
    if (Robot.controllers.bHeld()){ //TODO make the logic actually logical
        colorWheelEncoder.setPosition(462);
        SmartDashboard.putNumber("ColorWheelEncoderCounts", colorWheelEncoder.getPosition());
    }

    else{
        colorWheelEncoder.setPosition(0);

      //Color Position Turn
      if(gameData.length() > 0){
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
      } else{}

        Color detectedColor = colorSensor.getColor();
        double IR = colorSensor.getIR();
        SmartDashboard.putNumber("Color Sensor/Red", detectedColor.red);
        SmartDashboard.putNumber("Color Sensor/Green", detectedColor.green);
        SmartDashboard.putNumber("Color Sensor/Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", IR);
        String colorString;
        String fieldColor = "Unknown";
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        //colorString is what our color sensor is seeing, fieldColor is what the field should be seeing if we are seeing a certain color
        if (match.color == squirtleTarget) {
          colorString = "Blue";
          fieldColor = "R";
        }else if (match.color == charmanderTarget){
          colorString = "Red";
          fieldColor = "B";

        }else if (match.color == bulbasaurTarget){
          colorString = "Green";
          fieldColor = "Y";
        }else if (match.color == pikachuTarget){
          colorString = "Yellow";
          fieldColor = "G";
        }else{
          colorString = "Unknown";
        }
        SmartDashboard.putNumber("Color Sensor/Confidence", match.confidence);
        SmartDashboard.putString("Color Sensor/Detected Color", colorString);

        //this sees if the fieldColor(based off of our colorString) is equal to the color FMS is giving us
        if (fieldColor == gameData) {
          colorWheelMotor.set(0.0);
        } else {
          colorWheelMotor.set(0.1);
        }
  }
}
}
