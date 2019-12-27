package com.example.taskfragment.data;

import java.util.ArrayList;

public class TaskRepository {

    private static TaskRepository sTodoRepository;

    private final ArrayList<Task> mTasks = new ArrayList<>();

    public static TaskRepository getInstance() {
        if (sTodoRepository == null) {
            sTodoRepository = new TaskRepository();
        }
        return sTodoRepository;
    }

    private TaskRepository(){
        initTestData();
    }

    private void initTestData() {

        for (int i=0; i < 5; i++){
            Task task = new Task(i,"Test title " + i,"Test description " + i,
                    "To be set!");
            mTasks.add(task);
        }
    }

    public Task getFirstTask() {
        return mTasks.get(0);
    }

    public Task getNextTask(Task task) {

        int index = mTasks.indexOf(task);
        if (index == -1 || index == mTasks.size()) {
            return task;
        }
        if (index < mTasks.size() - 1) {
            return mTasks.get(index + 1);
        } else {
            return mTasks.get(mTasks.size() - 1);
        }

    }

    public Task getPrevTask(Task task) {

        int index = mTasks.indexOf(task);
        if ((index == -1) || (index == 0)) {
            return task;
        }
        if (index < mTasks.size()) {
            return mTasks.get(index - 1);
        } else {
            return mTasks.get(0);
        }

    }

    public boolean isFirst(Task task) {
        return mTasks.indexOf(task) == 0;
    }

    public boolean isLast(Task task) {
        return mTasks.indexOf(task) >= mTasks.size() - 1;
    }

    public Task delete(Task task) {

        /* delete task and return the next task */
        Task nextTask = new Task(0,"Task title", "Task description", "Task status");
        if ( mTasks.size() == 1 ) {
            mTasks.remove(task);
            mTasks.add(nextTask);
        } else if ( isLast(task) ) {
            nextTask = getFirstTask();
            mTasks.remove(task);
        } else {
            nextTask = getNextTask(task);
            mTasks.remove(task);
        }

        return nextTask;
    }

    public void addTask(Task task) {
        mTasks.add(task);
    }
}
