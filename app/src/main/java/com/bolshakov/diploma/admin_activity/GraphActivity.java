package com.bolshakov.diploma.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bolshakov.diploma.configs.RecyclerViewConfig;
import com.bolshakov.diploma.database_helper.FirebaseDatabaseHelper;
import com.bolshakov.diploma.models.Hardware;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    int count;
    List<Hardware> tmpList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FirebaseDatabaseHelper().readHardware(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Hardware> hardwareList, List<String> keys) {
                count = keys.size();
                tmpList = hardwareList;
                Log.d("tag",  "count= " + count);
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
        setContentView(new DrawView(this));
    }
    class DrawView extends View{
        Paint p;
        public DrawView(Context context) {
            super(context);
            p = new Paint();
            p.setStrokeWidth(10);
        }


        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas){
            for (Hardware item : tmpList){
                if (item.isActive){
                    p.setColor(Color.GREEN);
                    canvas.drawCircle(500, 100, 50, p);
                }else{
                    p.setColor(Color.RED);
                    canvas.drawCircle(200 , 100,50, p);
                }

            }
        }
    }
}