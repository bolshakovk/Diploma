package com.bolshakov.diploma.login_activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bolshakov.diploma.databinding.FragmentSecondBinding;
import com.bolshakov.diploma.models.User;
import com.bolshakov.diploma.configs.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String role;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
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
        binding.employerRadioButton.setOnClickListener(view1 ->
                role = "Employer");
        binding.radioAdminButton.setOnClickListener(view12 ->
                role = "Admin");
        binding.buttonDone.setOnClickListener(view13 ->
                registerUser());
        binding.employerRadioButton.setOnClickListener(view14 ->
                role = "Employer");
        binding.radioAdminButton.setOnClickListener(view15 ->
                role = "Admin");
    }

    @SuppressLint("ResourceType")
    private void registerUser(){
        String email = binding.emailRegText.getText().toString().trim();
        String password = binding.passwordRegText.getText().toString().trim();
        String confirmPassword = binding.passwordConfirmText.getText().toString().trim();


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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                User user = new User(email, role);
                FirebaseDatabase.getInstance().getReference(Config.USERS)
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                binding.progressRegBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity(), "User has NOT been registered successfully", Toast.LENGTH_LONG).show();
                                binding.progressRegBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}