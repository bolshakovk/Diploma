package com.bolshakov.diploma.configs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bolshakov.diploma.R;
import com.bolshakov.diploma.models.Hardware;

import java.util.List;

public class RecyclerViewConfig {
    private Context context;

    private HardwareAdapter hardwareAdapter;
    public void setConfig(RecyclerView recyclerView, Context c, List<Hardware> hardwareList, List<String> keys){
        context = c;
        hardwareAdapter = new HardwareAdapter(hardwareList, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(hardwareAdapter);
    }


    class HardwareItemView extends RecyclerView.ViewHolder {

        private TextView tName;
        private TextView tCost;
        private TextView tDescription;
        private String key;

        public HardwareItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).
                    inflate(R.layout.hardware_list_item, parent, false));

            tName = (TextView)itemView.findViewById(R.id.nameTextView);
            tCost = (TextView)itemView.findViewById(R.id.costTextView);
            tDescription = (TextView)itemView.findViewById(R.id.descriptionTextView);
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
    }
}
