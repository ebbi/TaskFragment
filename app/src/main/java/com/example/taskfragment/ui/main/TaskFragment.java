package com.example.taskfragment.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskfragment.R;
import com.example.taskfragment.data.Task;
import com.example.taskfragment.data.TaskRepository;

public class TaskFragment extends Fragment {

    private static final String LOG_TAG = TaskFragment.class.getSimpleName();

    private Task mTask = new Task();
    private final TaskRepository sTaskRepository = TaskRepository.getInstance();
    private MainViewModel mViewModel;

    private TextView mTextViewTitle;
    private TextView mTextViewDescription;
    private TextView mTextViewStatus;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d( LOG_TAG, "onCreateView");

// deprecated
// mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (mViewModel.getTask() == null){
            mTask = sTaskRepository.getFirstTask();
        } else {
            mTask = mViewModel.getTask();
        }

        final View view = inflater.inflate(R.layout.fragment_task, container, false);
        updateUI(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        Log.d( LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        // mViewModel = ViewModelProviders.of((getActivity())).get(MainViewModel.class);
        // TODO: Use the ViewModel

    }

    private void updateUI(View view) {

        mTextViewTitle = view.findViewById(R.id.textViewTitle);
        mTextViewDescription = view.findViewById(R.id.textViewDescription);
        mTextViewStatus = view.findViewById(R.id.textViewStatus);

        mTextViewTitle.setText(mTask.getTitle());
        mTextViewDescription.setText(mTask.getDescription());
        mTextViewStatus.setText(mTask.getStatus());

        Button mButtonNewTask = view.findViewById(R.id.buttonNewTask);
        mButtonNewTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mViewModel.setTask(mTask);

                TaskAddFragment taskAddFragment = TaskAddFragment.newInstance();
/* deprecated
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
*/
                assert getParentFragmentManager() != null;
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                transaction.replace(R.id.container, taskAddFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        Button mButtonNext = view.findViewById(R.id.buttonNext);
        mButtonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if ( sTaskRepository.isLast(mTask)) {

                    Toast toast = Toast.makeText(
                            getActivity(),
                            "Hurray! end of tasks, time to plant trees and read poetry!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {

                    mTask = sTaskRepository.getNextTask(mTask);
                    mViewModel.setTask(mTask);
                    mTextViewTitle.setText(mTask.getTitle());
                    mTextViewDescription.setText(mTask.getDescription());
                    mTextViewStatus.setText(mTask.getStatus());

                }
            }

        });

        Button mButtonDetail = view.findViewById(R.id.buttonDetail);
        mButtonDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance();
                taskDetailFragment.setViewModel(mViewModel);
/* deprecated
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
*/
                assert getParentFragmentManager() != null;
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                transaction.replace(R.id.container, taskDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        Button mButtonPrev = view.findViewById(R.id.buttonPrev);
        mButtonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if ( sTaskRepository.isFirst(mTask)) {

                    Toast toast = Toast.makeText(
                            getActivity(),
                            "First task!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {

                    mTask = sTaskRepository.getPrevTask(mTask);
                    mViewModel.setTask(mTask);
                    mTextViewTitle.setText(mTask.getTitle());
                    mTextViewDescription.setText(mTask.getDescription());
                    mTextViewStatus.setText(mTask.getStatus());

                }
            }

        });

    }

}
