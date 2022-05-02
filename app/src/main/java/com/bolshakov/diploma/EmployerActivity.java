package com.bolshakov.diploma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.bolshakov.diploma.databinding.ActivityEmployerBinding;

public class EmployerActivity extends AppCompatActivity {
    public ActivityEmployerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(manager, "myDialog");
            }
        });
    }
}