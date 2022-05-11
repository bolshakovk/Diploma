package com.bolshakov.diploma.database_helper;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bolshakov.diploma.configs.Config;
import com.bolshakov.diploma.employer_activity.EmployerActivity;
import com.bolshakov.diploma.models.Hardware;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private DatabaseReference databaseReference;
    private List<Hardware> hardwareList = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Hardware> hardwareList, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void updateHardware(String key, Hardware hardware, final DataStatus dataStatus){
        hardware.id = key;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Config.DATA);
        databaseReference.child(key).setValue(hardware).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsUpdated();
            }
        });
    }
    public void addHardware(Hardware hardware, final DataStatus dataStatus){
        hardware.id = databaseReference.push().getKey();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Config.DATA);
        databaseReference.child(hardware.id).setValue(hardware)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsInserted();
                    }
                });

    }
    public void readHardware(final DataStatus dataStatus){
        databaseReference.child(Config.DATA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hardwareList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()){
                        keys.add(keyNode.getKey());
                        Hardware hardware = keyNode.getValue(Hardware.class);
                        hardwareList.add(hardware);
                }
                dataStatus.DataIsLoaded(hardwareList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void deleteHardware(String key, final DataStatus dataStatus){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Config.DATA);
        databaseReference.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
