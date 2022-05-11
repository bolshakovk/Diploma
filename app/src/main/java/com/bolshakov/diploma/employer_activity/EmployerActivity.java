package com.bolshakov.diploma.employer_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bolshakov.diploma.configs.Config;
import com.bolshakov.diploma.database_helper.FirebaseDatabaseHelper;
import com.bolshakov.diploma.databinding.ActivityEmployerBinding;
import com.bolshakov.diploma.login_activity.MainActivity;
import com.bolshakov.diploma.models.Hardware;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EmployerActivity extends AppCompatActivity {
    public ActivityEmployerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
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

    public static boolean isDigit(String digit){
        int intValue;
        try {
            intValue = Integer.parseInt(digit);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private void addData(){
        String name = binding.hardwareNameText.getText().toString().trim();
        String param = binding.hardwareParamText.getText().toString().trim();
        String cost = binding.hardwareCostText.getText().toString().trim();
        int value;
        if (name.isEmpty()){
            binding.hardwareNameText.setError("Name required!");
            binding.hardwareNameText.requestFocus();
            return;
        }
        if (param.isEmpty()){
            binding.hardwareParamText.setError("Parameters required!");
            binding.hardwareParamText.requestFocus();
        }
        if (cost.isEmpty()){
            binding.hardwareCostText.setError("Cost required!");
            binding.hardwareCostText.requestFocus();
        }else{
            if (!isDigit(cost)){
                binding.hardwareCostText.setError("Cost must be numeric!");
                binding.hardwareCostText.requestFocus();
            }
        }/*
        databaseReference = FirebaseDatabase.getInstance().getReference(Config.DATA);
        MutableLiveData<Exception> mutableLiveData = new MutableLiveData<>();
        LiveData<Exception> liveData = new LiveData<Exception>() {
            @Nullable
            @Override
            public Exception getValue() {
                return mutableLiveData.getValue();
            }
        };
        Hardware hardware = new Hardware(name, param, cost);
        hardware.id = databaseReference.push().getKey();
        databaseReference.child(hardware.id).setValue(hardware).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mutableLiveData.setValue(null);
                }else {
                    mutableLiveData.setValue(task.getException());
                }
            }
        });*/
        Hardware hardware = new Hardware(name, param, cost, "true");
        new FirebaseDatabaseHelper().addHardware(hardware, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Hardware> hardwareList, List<String> keys) {
                Toast.makeText(EmployerActivity.this, "Data inserted succsessfully ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}