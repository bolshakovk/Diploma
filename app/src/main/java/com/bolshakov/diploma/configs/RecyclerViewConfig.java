package com.bolshakov.diploma.configs;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bolshakov.diploma.R;
import com.bolshakov.diploma.database_helper.FirebaseDatabaseHelper;
import com.bolshakov.diploma.databinding.FragmentFirst2Binding;
import com.bolshakov.diploma.models.Hardware;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class RecyclerViewConfig {
    private Context context;
    private HardwareAdapter hardwareAdapter;
    private RecyclerView ownRecyclerView;
    public ItemTouchHelper.SimpleCallback simpleCallback;

    public void setConfig(RecyclerView recyclerView, Context c, List<Hardware> hardwareList, List<String> keys) {
        context = c;
        hardwareAdapter = new HardwareAdapter(hardwareList, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(hardwareAdapter);
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                hardwareAdapter.removeItem(position);
                new FirebaseDatabaseHelper().deleteHardware(keys.get(position), new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Hardware> hardwareList, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Log.d("tag", "deleted: key = "  + keys + "position: " + position + "  keys.get(position): " + keys.get(position));
                    }
                });
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


    }

    class HardwareItemView extends RecyclerView.ViewHolder {

        private TextView tName;
        private TextView tCost;
        private TextView tDescription;
        private String key;
        private String value = "False";
        public HardwareItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).
                    inflate(R.layout.hardware_list_item, parent, false));

            tName = (TextView)itemView.findViewById(R.id.nameTextView);
            tCost = (TextView)itemView.findViewById(R.id.costTextView);
            tDescription = (TextView)itemView.findViewById(R.id.descriptionTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Hardware hardware = new Hardware();
                    hardware.name = tName.getText().toString();
                    hardware.cost = tCost.getText().toString();
                    hardware.params = tDescription.getText().toString();
                    hardware.isActive = "false";
                    if (hardware.isActive.equals("true")){
                        hardware.isActive = "false";
                    }
                    else if (hardware.isActive.equals("false")){
                        hardware.isActive = "true";
                    }

                    new FirebaseDatabaseHelper().updateHardware(key, hardware, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Hardware> hardwareList, List<String> keys) {
                            Toast.makeText(context.getApplicationContext(), value, Toast.LENGTH_LONG).show();
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

                    if (value.equals("False")){
                        value = "True";
                    } else if (value.equals("True")){
                        value = "False";
                    }
                    Toast.makeText(context.getApplicationContext(), value, Toast.LENGTH_LONG).show();
                }
            });
        }
        public void bind(Hardware hardware, String key){
            tName.setText(hardware.name);
            tCost.setText(hardware.cost);
            tDescription.setText(hardware.params);
            this.key = key;
        }
    }
    class HardwareAdapter extends RecyclerView.Adapter<HardwareItemView>{
        private List<Hardware> hardwareList;
        private List<String> keys;

        @NonNull
        @Override
        public HardwareItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HardwareItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HardwareItemView holder, int position) {
            holder.bind(hardwareList.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {
            return hardwareList.size();
        }

        public HardwareAdapter(List<Hardware> hardwareList, List<String> keys) {
            this.hardwareList = hardwareList;
            this.keys = keys;
        }

        public void removeItem(int pos) {
            hardwareList.remove(pos);
            notifyItemRemoved(pos);
        }
    }
}
