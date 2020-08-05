package com.gmail.elnora.fet.hw_4_customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class CustomView extends View {

    public interface OnTouchEventListener {
        void onTouchDown(int x, int y);
    }

    private Paint paintLeftTopSector = new Paint();
    private Paint paintRightTopSector = new Paint();
    private Paint paintLeftBottomSector = new Paint();
    private Paint paintRightBottomSector = new Paint();
    private Paint paintSmallCircle = new Paint();
    private int widthCenter;
    private int heightCenter;
    private int smallCircleRadius;
    private int bigCircleRadius;
    private int leftSideBigCircle;
    private int rightSideBigCircle;
    private int topSideBigCircle;
    private int bottomSideBigCircle;

    private OnTouchEventListener onTouchEventListener;

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
        leftSideBigCircle = widthCenter - bigCircleRadius;
        topSideBigCircle = heightCenter - bigCircleRadius;
        rightSideBigCircle = widthCenter + bigCircleRadius;
        bottomSideBigCircle = heightCenter + bigCircleRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(leftSideBigCircle, topSideBigCircle, rightSideBigCircle, bottomSideBigCircle, 0, 90, true, paintRightBottomSector);
        canvas.drawArc(leftSideBigCircle, topSideBigCircle, rightSideBigCircle, bottomSideBigCircle, 90, 90, true, paintLeftBottomSector);
        canvas.drawArc(leftSideBigCircle, topSideBigCircle, rightSideBigCircle, bottomSideBigCircle, 180, 90, true, paintLeftTopSector);
        canvas.drawArc(leftSideBigCircle, topSideBigCircle, rightSideBigCircle, bottomSideBigCircle, 270, 90, true, paintRightTopSector);
        canvas.drawCircle(widthCenter, heightCenter, smallCircleRadius, paintSmallCircle);
    }

    private int getRandomColor() {
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        return Color.rgb(r, g, b);
    }

    private void changeAllSectorColor() {
        paintRightBottomSector.setColor(getRandomColor());
        paintLeftBottomSector.setColor(getRandomColor());
        paintLeftTopSector.setColor(getRandomColor());
        paintRightTopSector.setColor(getRandomColor());
    }

    private void changeColor(int eventX, int eventY) {
        if ((eventX > widthCenter - smallCircleRadius && eventX < widthCenter + smallCircleRadius) &&
                (eventY > heightCenter - smallCircleRadius && eventY < heightCenter + smallCircleRadius)) {
                changeAllSectorColor();
        } else if (eventX > leftSideBigCircle && eventX < widthCenter) { //if touched left side of circle
            if (eventY > topSideBigCircle && eventY < heightCenter) { //left top side
                paintLeftTopSector.setColor(getRandomColor());
            } else if (eventY < bottomSideBigCircle && eventY > heightCenter) { //left bottom side
                paintLeftBottomSector.setColor(getRandomColor());
            }
        } else if (eventX > widthCenter && eventX < rightSideBigCircle) { //if touched right side of circle
            if (eventY > topSideBigCircle && eventY < heightCenter) { //right top side
                paintRightTopSector.setColor(getRandomColor());
            } else if ((eventY < bottomSideBigCircle && eventY > heightCenter)) { //right bottom side
                paintRightBottomSector.setColor(getRandomColor());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        changeColor(eventX, eventY);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onTouchEventListener != null) {
                onTouchEventListener.onTouchDown(eventX, eventY);
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void setOnTouchEventListener(OnTouchEventListener onTouchEventListener) {
        this.onTouchEventListener = onTouchEventListener;
    }

}
