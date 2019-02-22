package edu.stanford.cs108.mobiledraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static edu.stanford.cs108.mobiledraw.MainActivity.ERASE_MODE;
import static edu.stanford.cs108.mobiledraw.MainActivity.OVAL_MODE;
import static edu.stanford.cs108.mobiledraw.MainActivity.RECT_MODE;
import static edu.stanford.cs108.mobiledraw.MainActivity.SELECT_MODE;

public class DrawingView extends View {

    private ArrayList<GObject> shapes = new ArrayList<GObject>();
    private float x, y, height, width;
    private GObject selected;
    private Button updateBtn;
    private boolean firstEvent;

    /*
    Constructor
     */
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        firstEvent = true;

    }

    private void updateSelectedShape() {
        if (selected == null) {
            Toast.makeText(getContext(), "No selection made", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView xText = (EditText) ((Activity)getContext()).findViewById(R.id.xCoorText);
        TextView yText = (EditText) ((Activity)getContext()).findViewById(R.id.yCoorText);
        TextView widthText = (EditText) ((Activity)getContext()).findViewById(R.id.widthText);
        TextView heightText = (EditText) ((Activity)getContext()).findViewById(R.id.heightText);

        selected.setxCoor(Float.parseFloat((xText.getText()).toString()));
        selected.setyCoor(Float.parseFloat((yText.getText()).toString()));
        selected.setHeight(Float.parseFloat((heightText.getText()).toString()));
        selected.setwidth(Float.parseFloat((widthText.getText()).toString()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (GObject shape : shapes) {
            boolean isSelected = false;
            if (shape == selected) isSelected = true;
            shape.drawShape(canvas, isSelected);
        }
            updateBottomInfo();
    }


    private void updateBottomInfo() {
        TextView xText = (EditText) ((Activity)getContext()).findViewById(R.id.xCoorText);
        TextView yText = (EditText) ((Activity)getContext()).findViewById(R.id.yCoorText);
        TextView widthText = (EditText) ((Activity)getContext()).findViewById(R.id.widthText);
        TextView heightText = (EditText) ((Activity)getContext()).findViewById(R.id.heightText);

        if (selected == null) { //Clears bottom info if nothing is selected
            xText.setText("");
            yText.setText("");
            widthText.setText("");
            heightText.setText("");
            return;
        }

        xText.setText(String.valueOf(selected.getxCoor()));
        yText.setText(String.valueOf(selected.getyCoor()));
        widthText.setText(String.valueOf(selected.getWidth()));
        heightText.setText(String.valueOf(selected.getHeight()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (firstEvent) {
            updateBtn = (Button) ((Activity)getContext()).findViewById(R.id.updateBtn);
            updateBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSelectedShape();
                    invalidate();
                }
            });
            firstEvent = false;
        }

        int currMode = MainActivity.getMode();
        switch (currMode) {
            case RECT_MODE: manageShapeDrawing(event);
                break;
            case OVAL_MODE: manageShapeDrawing(event);
                break;
            case SELECT_MODE: manageSelectionEvent(event);
                invalidate();
                break;
            case ERASE_MODE: manageEraseEvent(event);
        }

        return true;
    }

    private void manageEraseEvent(MotionEvent event) {
        manageSelectionEvent(event); //Sets selected value
        if (selected != null) {
            shapes.remove(selected);
            invalidate();
        }
        selected = null;
    }

    private void manageSelectionEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                GObject selObject = getObjectAt(event.getX(), event.getY());
                selected = selObject;
                break;
        }
    }

    private GObject getObjectAt(float x, float y) {
        GObject result = null;
        for (int i = shapes.size() - 1; i >= 0; i--) { //loops backwards to latest GObject that matches criteria
            GObject currShape = shapes.get(i);
            if (x >= currShape.getxCoor() && x <= (currShape.getxCoor() + currShape.getWidth())) { //if x within shape x values
                if (y >= currShape.getyCoor() && y <= (currShape.getyCoor() + currShape.getHeight())){
                    return currShape;
                }
            }
        }
        return result;
    }

    /*
    Handles motionEvents when in shape drawing mode
     */
    private void manageShapeDrawing(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //store x/y start coor
                x = event.getX();
                y = event.getY();
                break;

            case MotionEvent.ACTION_UP: //add shape to shapes array then draw
                width = event.getX() - x;
                height = event.getY() - y;
                if (event.getX() < x) {
                    width = x - event.getX();
                    x = event.getX();
                }
                if (event.getY() < y) {
                    height = y - event.getY();
                    y = event.getY();
                }
                if (MainActivity.getMode() == RECT_MODE) {
                    shapes.add(new GRect(x, y, width, height));
                } else shapes.add(new GOval(x, y, width, height));
                selected = shapes.get(shapes.size()-1); //Set selected to the most recent GObject drawn
                invalidate();
                break;
        }
    }
}
