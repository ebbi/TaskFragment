package com.example.taskfragment.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.taskfragment.data.Task;

public class MainViewModel extends ViewModel {

    // refactor to liveData
    private Task mTask;

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        this.mTask = task;
    }

}
