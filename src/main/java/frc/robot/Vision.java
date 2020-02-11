/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Vision {
	// Table of Pixels
	private static double[] pixelTable = new double[] { 307, 250, 207, 175, 151, 135, 120, 109, 98, 92, 82, 78, 72, 70,
			67, 63, 57, 53 };
	// Outputs
	private double distance;
	private double angleError;
	// Points
	private Point midPoint;
	private Point leftPoint;
	private Point rightPoint;
	private Point bottomPoint;

	private Vision(double distance, double angleError, Point midPoint, Point leftPoint, Point rightPoint, Point bottomPoint) {
		this.distance = distance;
		this.angleError = angleError;
		this.midPoint = midPoint;
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
		this.bottomPoint = bottomPoint;
	}

	public static Vision findTarget(Mat src) {
		// Run the image through opencv's mask filter
		Mat output = new Mat(src.rows(), src.cols(), CvType.CV_8U);
		Mat image = new Mat(src.rows(), src.cols(), CvType.CV_8U);
		Imgproc.cvtColor(src, image, Imgproc.COLOR_BGR2HSV);
		Core.inRange(image, new Scalar(50, 205, 64), new Scalar(90, 255, 255), output);
		// inRange converts images to 3 grayscale images, so we have to convert it into
		// bgr to merge them
		Imgproc.cvtColor(output, image, Imgproc.COLOR_GRAY2BGR);
		// Finds the leftmost pixel in the image
		Point leftPoint = new Point(0, 0);
		for (int col = 0; col < image.cols(); col++) {
			boolean found = false;
			for (int row = 0; row < image.rows(); row++) {
				if (image.get(row, col)[0] == 255) {
					found = true;
					leftPoint = new Point(col, row);
					break;
				}
			}
			if (found) {
				break;
			}
		}
		// Finds the rightmost pixel in the image
		Point rightPoint = new Point(0, 0);
		for (int col = image.cols() - 1; col >= 0; col--) {
			boolean found = false;
			for (int row = 0; row < image.rows(); row++) {
				if (image.get(row, col)[0] == 255) {
					found = true;
					rightPoint = new Point(col, row);
					break;
				}
			}
			if (found) {
				break;
			}
		}
		// Finds the midpoint between the leftmost and rightmost pixel
		Point midPoint = new Point((rightPoint.x + leftPoint.x) / 2, (rightPoint.y + leftPoint.y) / 2);
		// Finds the lowest pixel on the x axis of the midpoint
		Point bottomPoint = new Point(0, 0);
		for (int row = image.rows() - 1; row >= 0; row--) {
			if (image.get(row, (int) midPoint.x)[0] == 255) {
				bottomPoint = new Point(midPoint.x, row);
				break;
			}
		}
		// interpolates between each measurement
		double pixels = bottomPoint.y - midPoint.y;
		double zDistance = 0;
		double xDistance = 0;
		for (int i = pixelTable.length - 2; i >= 0; i--) {
			if (pixels < pixelTable[i]) {
				// gets the closest pixel measurement, plus the percentage of the way towards
				// the next measurement
				zDistance = i + 3 + ((pixelTable[i] - pixels) / (pixelTable[i] - pixelTable[i + 1]));
				xDistance = ((midPoint.x - (image.cols() / 2))) / (pixels / 1.4167);
				break;
			}
		}
		double angleError = Math.toDegrees(Math.asin(xDistance / zDistance));

		return new Vision(zDistance, angleError, midPoint, leftPoint, rightPoint, bottomPoint);
	}
	
	//Getter and setter wasteland

	public double getDistance() {
		return distance;
	}

	public double getAngleError() {
		return angleError;
	}

	public Point getMidPoint() {
		return midPoint;
	}

	public Point getLeftPoint() {
		return leftPoint;
	}

	public Point getRightPoint() {
		return rightPoint;
	}

	public Point getBottomPoint() {
		return bottomPoint;
	}
}
