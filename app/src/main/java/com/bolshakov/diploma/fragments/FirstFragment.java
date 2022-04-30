package com.bolshakov.diploma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bolshakov.diploma.AdminActivity;
import com.bolshakov.diploma.EmployerActivity;
import com.bolshakov.diploma.R;
import com.bolshakov.diploma.User;
import com.bolshakov.diploma.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseUser currentUser = mAuth.getCurrentUser();
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    User user;
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

        binding.registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentUser != null){

                    mAuth.signInWithEmailAndPassword(binding.usernameLoginText.getText().toString(), binding.passwordLoginText.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Intent intent;
                                        User user = snapshot.getValue(User.class);
                                        Toast.makeText(getActivity(), user.membership, Toast.LENGTH_LONG).show();
                                        if (user.membership.equals("employer")){
                                            intent = new Intent(getActivity(), EmployerActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            intent = new Intent(getActivity(), AdminActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Toast.makeText(getActivity(), "user  not null", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "user null", Toast.LENGTH_LONG).show();
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