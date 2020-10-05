package com.gmail.elnora.fet.hw_2_layouts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View  {
    private Paint paintLeftTopSector;
    private Paint paintRightTopSector;
    private Paint paintLeftBottomSector;
    private Paint paintRightBottomSector;
    private Paint paintCenterCircle;

    private int bigCircleRadius;
    private int centerCircleRadius;
    private int centerX;
    private int centerY;

    interface OnTouchEvent {
        void onTouchEvent(float eventX, float eventY);
    }

    private OnTouchEvent listener;

    public void setListener(OnTouchEvent listener) {
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paintLeftTopSector = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLeftTopSector.setColor(Color.RED);

        paintRightTopSector = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRightTopSector.setColor(Color.GREEN);

        paintLeftBottomSector = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLeftBottomSector.setColor(Color.BLUE);

        paintRightBottomSector = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRightBottomSector.setColor(Color.YELLOW);

        paintCenterCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCenterCircle.setColor(getRandomColor());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        changeColor(eventX, eventY);
        if (listener != null) {
            listener.onTouchEvent(eventX, eventY);
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    private void changeColor(float eventX, float eventY) {
        //if touched the center circle -> change color in all sectors including center circle
        if ((eventX > centerX - centerCircleRadius && eventX < centerX + centerCircleRadius) && (eventY > centerY - centerCircleRadius && eventY < centerY + centerCircleRadius)) {
            paintRightBottomSector.setColor(getRandomColor());
            paintLeftBottomSector.setColor(getRandomColor());
            paintLeftTopSector.setColor(getRandomColor());
            paintRightTopSector.setColor(getRandomColor());
            paintCenterCircle.setColor(getRandomColor());
        } else if (eventX > centerX - bigCircleRadius && eventX < centerX) { //if touched right side
            if (eventY > centerY - bigCircleRadius && eventY < centerY) { //top side
                paintLeftTopSector.setColor(getRandomColor());
            } else if (eventY < centerY + bigCircleRadius && eventY > centerY) { //bottom side
                paintLeftBottomSector.setColor(getRandomColor());
            }
        } else if (eventX > centerX && eventX < centerX + bigCircleRadius) { //if touched left side
            if (eventY > centerY - bigCircleRadius && eventY < centerY) { //top side
                paintRightTopSector.setColor(getRandomColor());
            } else if ((eventY < centerY + bigCircleRadius && eventY > centerY)) { //bottom side
                paintRightBottomSector.setColor(getRandomColor());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius, centerX + bigCircleRadius, centerY + bigCircleRadius, 0, 90, true, paintRightBottomSector);
        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius, centerX + bigCircleRadius, centerY + bigCircleRadius, 90, 90, true, paintLeftBottomSector);
        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius, centerX + bigCircleRadius, centerY + bigCircleRadius, 180, 90, true, paintLeftTopSector);
        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius, centerX + bigCircleRadius, centerY + bigCircleRadius, 270, 90, true, paintRightTopSector);
        canvas.drawCircle(centerX, centerY, centerCircleRadius, paintCenterCircle);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bigCircleRadius = Math.min(w, h) / 3;
        centerCircleRadius = Math.min(w, h) / 8;
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
