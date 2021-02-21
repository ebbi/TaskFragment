package com.example.taskfragment.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskfragment.R;
import com.example.taskfragment.data.Task;
import com.example.taskfragment.data.TaskRepository;

public class TaskAddFragment extends Fragment {

    private static final String LOG_TAG = TaskAddFragment.class.getSimpleName();

    private Task mTask = new Task();
    private final TaskRepository sTaskRepository = TaskRepository.getInstance();
    private MainViewModel mViewModel;

    public static TaskAddFragment newInstance() {
        return new TaskAddFragment();
    }

    public TaskAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_add_task, container, false);
        Log.d( LOG_TAG, "onCreateView");

// deprecated: mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        final View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        updateUI(view);
        return view;

    }

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;

    private void updateUI(View view) {

        mEditTextTitle = view.findViewById(R.id.editTextTitle);
        mEditTextDescription = view.findViewById(R.id.editTextDescription);

        Button mButtonAddTask = view.findViewById(R.id.buttonAddTask);
        mButtonAddTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());


                sTaskRepository.addTask(mTask);
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
        });

    }

}
