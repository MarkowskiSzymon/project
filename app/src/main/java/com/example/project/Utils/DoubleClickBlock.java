package com.example.project.Utils;

import android.view.View;

public class DoubleClickBlock {

    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 500);
    }
}
