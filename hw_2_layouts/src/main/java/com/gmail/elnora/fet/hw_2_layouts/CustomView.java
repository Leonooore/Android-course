package com.gmail.elnora.fet.hw_2_layouts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Random;

public class CustomView extends View  {
    Paint paintBigCircle;
    Paint paintCenterCircle;
    private int bigRadius;
    private int centerRadius;
    private int centerX;
    private int centerY;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paintBigCircle = new Paint();
        paintCenterCircle = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintBigCircle.setColor(Color.YELLOW);
        canvas.drawArc(centerX - bigRadius, centerY - bigRadius, centerX + bigRadius, centerY + bigRadius, 0, 90, true, paintBigCircle);

        paintBigCircle.setColor(Color.BLUE);
        canvas.drawArc(centerX - bigRadius, centerY - bigRadius, centerX + bigRadius, centerY + bigRadius, 90, 90, true, paintBigCircle);

        paintBigCircle.setColor(Color.RED);
        canvas.drawArc(centerX - bigRadius, centerY - bigRadius, centerX + bigRadius, centerY + bigRadius, 180, 90, true, paintBigCircle);

        paintBigCircle.setColor(Color.GREEN);
        canvas.drawArc(centerX - bigRadius, centerY - bigRadius, centerX + bigRadius, centerY + bigRadius, 270, 90, true, paintBigCircle);

        paintCenterCircle.setColor(getRandomColor());
        canvas.drawCircle(centerX, centerY, centerRadius, paintCenterCircle);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bigRadius = Math.min(w, h) / 3;
        centerRadius = Math.min(w, h) / 8;
        centerX = w / 2;
        centerY = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getRandomColor() {
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        return Color.rgb(r, g, b);
    }
}
