package com.bolshakov.diploma.admin_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bolshakov.diploma.adapters.RecyclerViewConfig;
import com.bolshakov.diploma.database_helper.FirebaseDatabaseHelper;
import com.bolshakov.diploma.databinding.FragmentFirst2Binding;
import com.bolshakov.diploma.models.Hardware;

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


        binding.graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GraphActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}