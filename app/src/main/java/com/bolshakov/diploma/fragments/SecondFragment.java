package com.bolshakov.diploma.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bolshakov.diploma.AdminActivity;
import com.bolshakov.diploma.EmployerActivity;
import com.bolshakov.diploma.User;
import com.bolshakov.diploma.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    public DatabaseReference databaseReference;
    String role;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentSecondBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        binding.employerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "Employer";
            }
        });
        binding.radioAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "Admin";
            }
        });
        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        binding.employerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "Employer";
            }
        });
        binding.radioAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "Admin";
            }
        });
    }

    @SuppressLint("ResourceType")
    private void registerUser(){
        String email = binding.emailRegText.getText().toString().trim();
        String password = binding.passwordRegText.getText().toString().trim();
        String confirmPassword = binding.passwordConfirmText.getText().toString().trim();
        RadioButton employerRadioButton = binding.employerRadioButton;


        if (password.isEmpty()){
            binding.passwordRegText.setError("password is required!");
            binding.passwordRegText.requestFocus();
            return;
        }
        if (password.length() < 6){
            binding.passwordRegText.setError("password length must be over 6 symbols!");
            binding.passwordRegText.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()){
            binding.passwordConfirmText.setError("password is required!");
            binding.passwordConfirmText.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)){
            binding.passwordConfirmText.setError("passwords not equals!");
            binding.passwordConfirmText.requestFocus();
            return;
        }
        if (email.isEmpty()){
            binding.emailRegText.setError("Email is required!");
            binding.emailRegText.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailRegText.setError("Enter a valid email!");
            binding.emailRegText.requestFocus();
            return;
        }
        if (binding.radioGroup.getCheckedRadioButtonId() <= 0){
            binding.employerRadioButton.setError("Role is required!");
            binding.employerRadioButton.requestFocus();
            return;
        }





        binding.progressRegBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(email, role);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                binding.progressRegBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity(), "User has NOT been registered successfully", Toast.LENGTH_LONG).show();
                                binding.progressRegBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}