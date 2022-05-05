package com.bolshakov.diploma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.bolshakov.diploma.databinding.ActivityEmployerBinding;
import com.bolshakov.diploma.fragments.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class EmployerActivity extends AppCompatActivity {
    public ActivityEmployerBinding binding;
    ArrayList<Hardware> hardwares = new ArrayList<>();
    HardwareAdapter hardwareAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        for (int i = 1; i<5; i++){
            hardwares.add(new Hardware("param", 123));
        }
        hardwareAdapter = new HardwareAdapter(this, hardwares);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(hardwareAdapter);

        binding.addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(manager, "myDialog");
            }
        });
        binding.exitActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EmployerActivity.this, MainActivity.class));
            }
        });
    }

}