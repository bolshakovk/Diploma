package com.bolshakov.diploma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    String choice;

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
                choice = "employer";
            }
        });
        binding.radioAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = "Admin";
            }
        });
        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = databaseReference.getKey();
                String name = binding.usernameRegText.getText().toString();
                String password = binding.passwordRegText.getText().toString();
                String confirmPassword = binding.passwordConfirmText.getText().toString();
                String email = binding.emailRegText.getText().toString();
                String admin = binding.radioAdminButton.getText().toString();
                User user = new User(id, name,password,email, choice);
                if (!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)
                        && !TextUtils.isEmpty(email) && binding.radioGroup.getCheckedRadioButtonId() != -1){

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Intent intent;
                            if (task.isSuccessful()){
                                databaseReference.setValue(user);

                                if (user.membership.equals("Admin")){
                                    intent = new Intent(getActivity(), AdminActivity.class);
                                }else {
                                    intent = new Intent(getActivity(), EmployerActivity.class);
                                }
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Sign up success", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                                Toast.makeText(getActivity(), "Sing up failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Что то не ввел", Toast.LENGTH_SHORT).show();
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