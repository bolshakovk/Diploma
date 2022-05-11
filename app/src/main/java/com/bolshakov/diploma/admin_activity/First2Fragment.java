package com.bolshakov.diploma.admin_activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bolshakov.diploma.R;
import com.bolshakov.diploma.configs.RecyclerViewConfig;
import com.bolshakov.diploma.database_helper.FirebaseDatabaseHelper;
import com.bolshakov.diploma.databinding.FragmentFirst2Binding;
import com.bolshakov.diploma.models.Hardware;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class First2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentFirst2Binding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirst2Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.recyclerViewHardware;



        new FirebaseDatabaseHelper().readHardware(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Hardware> hardwareList, List<String> keys) {
                new RecyclerViewConfig().setConfig(recyclerView, getActivity(), hardwareList, keys);
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


        binding.graphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(First2Fragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}