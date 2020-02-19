/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Add your docs here.
 */
public class RunTasksInParallel implements TaskBase {
    private List<TaskBase> unfinishedTasks;
    public RunTasksInParallel(TaskBase ... tasks) {
        unfinishedTasks = new ArrayList<TaskBase>();
        for (TaskBase task : tasks) {
            unfinishedTasks.add(task);
        }
    }

    @Override
    public void start() {
        for (TaskBase task : unfinishedTasks) {
            task.start();
        }
    }

    @Override
    public boolean periodic() {
        for (TaskBase task : unfinishedTasks) {
            if (task.periodic()) {
                task.done();
                unfinishedTasks.remove(task);
            }
        }
        return unfinishedTasks.isEmpty();
    }

    @Override
    public void done() {}
}
