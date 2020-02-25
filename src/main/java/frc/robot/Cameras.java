/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;

/**
 * Add your docs here.
 */
public class Cameras {
    private UsbCamera shootCamera;
    private UsbCamera fisheyeCamera;

    public Cameras() {
        shootCamera = CameraServer.getInstance().startAutomaticCapture();
        fisheyeCamera = CameraServer.getInstance().startAutomaticCapture();
        shootCamera.setResolution(160, 120);
        shootCamera.setFPS(15);
        //shootCamera.setWhiteBalanceAuto();
        shootCamera.setBrightness(45);
        fisheyeCamera.setResolution(160, 120);
        fisheyeCamera.setFPS(15);
        fisheyeCamera.setWhiteBalanceAuto();

    }
}
