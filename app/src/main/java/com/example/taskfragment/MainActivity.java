package com.example.taskfragment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taskfragment.ui.main.TaskFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TaskFragment.newInstance())
                    .commitNow();
        }

    }
}
