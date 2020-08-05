package com.gmail.elnora.fet.hw_4_customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private Paint paintLeftTopSector = new Paint();
    private Paint paintRightTopSector = new Paint();
    private Paint paintLeftBottomSector = new Paint();
    private Paint paintRightBottomSector = new Paint();
    private Paint paintSmallCircle = new Paint();
    private int widthCenter;
    private int heightCenter;
    private int smallCircleRadius;
    private int bigCircleRadius;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public CustomView(Context context) {
        super(context);
        initPaintColor();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintColor();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaintColor();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaintColor();
    }

    private void initPaintColor() {
        paintLeftTopSector.setColor(Color.RED);
        paintRightTopSector.setColor(Color.GREEN);
        paintLeftBottomSector.setColor(Color.BLUE);
        paintRightBottomSector.setColor(Color.YELLOW);
        paintSmallCircle.setColor(getRandomColor());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthCenter = w / 2;
        heightCenter = h / 2;
        smallCircleRadius = Math.min(w, h) / 8;
        bigCircleRadius = Math.min(w, h) / 3;
        left = widthCenter - bigCircleRadius;
        top = heightCenter - bigCircleRadius;
        right = widthCenter + bigCircleRadius;
        bottom = heightCenter + bigCircleRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(left, top, right, bottom, 0, 90, true, paintRightBottomSector);
        canvas.drawArc(left, top, right, bottom, 90, 90, true, paintLeftBottomSector);
        canvas.drawArc(left, top, right, bottom, 180, 90, true, paintLeftTopSector);
        canvas.drawArc(left, top, right, bottom, 270, 90, true, paintRightTopSector);
        canvas.drawCircle(widthCenter, heightCenter, smallCircleRadius, paintSmallCircle);
    }

    private int getRandomColor() {
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        return Color.rgb(r, g, b);
    }
}
