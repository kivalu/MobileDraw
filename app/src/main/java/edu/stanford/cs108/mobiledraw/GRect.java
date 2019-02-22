package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class GRect extends GObject {

    public GRect(float xCoor, float yCoor, float width, float height) {
        super(xCoor, yCoor, width, height);
    }

    @Override
    public void drawShape(Canvas canvas, boolean isSelected) {
        Paint paint = new Paint();

        float left = getxCoor();
        float right = getxCoor() + getWidth();
        float top = getyCoor();
        float bottom = getyCoor() + getHeight();

        RectF rect = new RectF(left, top, right, bottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(rect, paint);

        paint.setStyle(Paint.Style.STROKE);

        if (isSelected) {
            paint.setStrokeWidth(15.0f);
            paint.setColor(Color.BLUE);
        } else {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(5.0f);
        }
        canvas.drawRect(rect, paint);

    }
}
