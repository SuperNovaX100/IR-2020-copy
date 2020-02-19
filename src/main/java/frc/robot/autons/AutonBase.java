/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import frc.robot.Robot;
import frc.robot.tasks.TaskBase;

/**
 * Add your docs here.
 */
public class AutonBase {
    protected TaskBase[] tasks;
    protected String name;
    protected int currentTask;

    public AutonBase(String name, TaskBase[] tasks) {
        this.name = name;
        this.tasks = tasks;
        Robot.autons.add(this);
    }

    public void start() {
        currentTask = 0;
    }

    public void periodic() {
        if (currentTask < tasks.length - 1 && tasks[currentTask].periodic()) {
            tasks[currentTask].done();
            currentTask += 1;
        }
    }

    public void done() {
        tasks[currentTask].done();
    }

    public String getName() {
        return name;
    }
}
