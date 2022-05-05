package com.bolshakov.diploma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bolshakov.diploma.R;
import com.bolshakov.diploma.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public DatabaseReference databaseReference;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert binding.registerView != null;
        binding.registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String email = binding.usernameLoginText.getText().toString().trim();
        String password = binding.passwordLoginText.getText().toString().trim();
        if (email.isEmpty()){
            binding.usernameLoginText.setError("Email is required!");
            binding.usernameLoginText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.usernameLoginText.setError("Email is incorrect!");
            binding.usernameLoginText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.passwordLoginText.setError("Password is required!");
            binding.passwordLoginText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordLoginText.setError("Password requires minimum 6 character!");
            binding.passwordLoginText.requestFocus();
        }
        mAuth = FirebaseAuth.getInstance();
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    currentUser = mAuth.getCurrentUser();
                    Log.d("tag", currentUser.getUid());
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(currentUser.getUid()).exists()){
                                Log.d("tag", "here is somebody");
                                Log.d("tag", String.valueOf(snapshot.child(currentUser.getUid()).child("membership")));
                                if (String.valueOf(snapshot.child(currentUser.getUid()).child("membership")).contains("Admin")) {
                                    Log.d("tag", "somebody with membership Admin");
                                    binding.progressBar.setVisibility(View.GONE);
                                }else if (String.valueOf(snapshot.child(currentUser.getUid()).child("membership")).contains("Employer")){
                                    Log.d("tag", "somebody with membership Employer");
                                    binding.progressBar.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Failed to Login! Check your credentials", Toast.LENGTH_LONG).show();
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