package edu.stanford.cs108.mobiledraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    public static final int SELECT_MODE = 0;
    public static final int RECT_MODE = 1;
    public static final int OVAL_MODE = 2;
    public static final int ERASE_MODE = 3;

    private static int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mode = RECT_MODE;
    }

    public void ovalMode(View view) {
        mode = OVAL_MODE;
    }

    public void eraseMode(View view) {
        mode = ERASE_MODE;
    }

    public void rectMode(View view) {
        mode = RECT_MODE;
    }

    public void selectMode(View view) {
        mode = SELECT_MODE;
    }

    public static int getMode() {
        return mode;
    }
}
