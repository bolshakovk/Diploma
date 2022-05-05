package com.bolshakov.diploma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HardwareAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<Hardware> hardwares;

    public HardwareAdapter(Context context, ArrayList<Hardware> hardwares) {
        this.context = context;
        this.hardwares = hardwares;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hardwares.size();
    }

    @Override
    public Object getItem(int i) {
        return hardwares.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    Hardware getHardware(int i){
        return ((Hardware) getItem(i));
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = lInflater.inflate(R.layout.item_layout, viewGroup, false);
        }
        Hardware hardware = getHardware(i);
        ((TextView) view.findViewById(R.id.textViewParam)).setText("hardware.params");
        ((TextView) view.findViewById(R.id.textViewCost)).setText("hardware.cost");
        return view;
    }
}
