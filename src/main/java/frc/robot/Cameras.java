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
    private UsbCamera testCamera;
    public Cameras() {
        testCamera = CameraServer.getInstance().startAutomaticCapture();
    }
}
