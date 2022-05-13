package com.bolshakov.diploma.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

public class Edge extends View {
    private Paint paint = new Paint();

    public Edge(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        canvas.drawLine(50,100,600,600, paint);

    }
}
