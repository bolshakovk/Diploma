package com.bolshakov.diploma.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bolshakov.diploma.admin_activity.AdminActivity;
import com.bolshakov.diploma.databinding.FragmentFirstBinding;
import com.bolshakov.diploma.employer_activity.EmployerActivity;
import com.bolshakov.diploma.R;
import com.bolshakov.diploma.configs.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public DatabaseReference databaseReference;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert binding.registerView != null;

        binding.registerView.setOnClickListener(view1 ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        assert binding.logInButton != null;
        binding.logInButton.setOnClickListener(view12 ->
                loginUser());
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
        assert binding.progressBar != null;
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                currentUser = mAuth.getCurrentUser();
                assert currentUser != null;
                Log.d("tag", currentUser.getUid());
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(Config.USERS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent;
                        if (snapshot.child(currentUser.getUid()).exists()){
                            if (String.valueOf(snapshot.child(currentUser.getUid()).child(Config.MEMBERSHIP)).contains(Config.ADMIN)) {
                                intent = new Intent(getActivity(), AdminActivity.class);
                                startActivity(intent);
                                binding.progressBar.setVisibility(View.GONE);
                            }else if (String.valueOf(snapshot.child(currentUser.getUid()).child(Config.MEMBERSHIP)).contains(Config.EMP)){
                                intent = new Intent(getActivity(), EmployerActivity.class);
                                startActivity(intent);
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
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}