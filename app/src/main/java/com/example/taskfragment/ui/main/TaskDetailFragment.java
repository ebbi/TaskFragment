package com.example.taskfragment.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskfragment.R;
import com.example.taskfragment.data.Task;
import com.example.taskfragment.data.TaskRepository;

public class TaskDetailFragment extends Fragment {

    private static final String LOG_TAG = TaskDetailFragment.class.getSimpleName();

    private Task mTask = new Task();
    private final TaskRepository sTaskRepository = TaskRepository.getInstance();
    private MainViewModel mViewModel;

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert getActivity() != null;
// deprecated: mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mTask = mViewModel.getTask();

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        updateUI(view);
        return view;

    }

    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private final View.OnClickListener mTaskListener = new View.OnClickListener() {

        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.buttonSubmit:
                    doSubmit();
                    break;

                case R.id.radioButtonPending:
                    mTask.setStatus(getString(R.string.pending));
                    break;

                case R.id.radioButtonComplete:
                    mTask.setStatus(getString(R.string.complete));
                    break;

                case R.id.radioButtonDelete:
                    mTask = sTaskRepository.delete(mTask);
                    doSubmit();
                    break;

                default:
                    break;
            }
        }
    };

    private void doSubmit() {

        Log.d(LOG_TAG, "doSubmit() returning Status: " + mTask.getStatus() );

        mViewModel.setTask(mTask);

        TaskFragment taskFragment = TaskFragment.newInstance();

/* deprecated
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
*/
        assert getParentFragmentManager() != null;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, taskFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void updateUI(View view) {

        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(mTask.getTitle());

        TextView textViewDetail = view.findViewById(R.id.textViewDetail);
        textViewDetail.setText(mTask.getDescription());

        RadioButton radioButtonPending = view.findViewById(R.id.radioButtonPending);
        radioButtonPending.setChecked((mTask.getStatus().equals(getString(R.string.pending))));
        radioButtonPending.setOnClickListener(mTaskListener);

        RadioButton radioButtonComplete = view.findViewById(R.id.radioButtonComplete);
        radioButtonComplete.setChecked((mTask.getStatus().equals(getString(R.string.complete))));
        radioButtonComplete.setOnClickListener(mTaskListener);

        RadioButton radioButtonDelete = view.findViewById(R.id.radioButtonDelete);
        radioButtonDelete.setChecked((mTask.getStatus().equals(getString(R.string.delete))));
        radioButtonDelete.setOnClickListener(mTaskListener);

        Button submitButton = view.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(mTaskListener);

    }

}
