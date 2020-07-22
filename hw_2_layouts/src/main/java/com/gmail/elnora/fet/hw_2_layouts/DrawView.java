package com.gmail.elnora.fet.hw_2_layouts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

public class DrawView extends View {
    Paint paint;
    private int bigRadius;
    private int smallRadius;
    private int cx;
    private int cy;

    public DrawView(Context context) {
        super(context);
        paint = new Paint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawArc(cx - bigRadius,cy - bigRadius,cx + bigRadius,cy + bigRadius, 0, 90, true, paint);

        paint.setColor(Color.BLUE);
        canvas.drawArc(cx - bigRadius,cy - bigRadius,cx + bigRadius,cy + bigRadius, 90, 90, true, paint);

        paint.setColor(Color.RED);
        canvas.drawArc(cx- bigRadius,cy - bigRadius,cx + bigRadius,cy + bigRadius, 180, 90, true, paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(cx - bigRadius,cy - bigRadius,cx + bigRadius,cy + bigRadius, 270, 90, true, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(cx, cy, smallRadius, paint);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bigRadius = Math.min(w, h) / 3;
        smallRadius= Math.min(w, h) / 8;
        cx = w / 2;
        cy = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
