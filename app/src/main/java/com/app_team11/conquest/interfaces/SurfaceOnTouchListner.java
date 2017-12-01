package com.app_team11.conquest.interfaces;

import android.view.MotionEvent;
import android.view.View;

/**
 * This is the interface for the surface on touch listner
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public interface SurfaceOnTouchListner {
    /**
     * This is the method for on touch
     * @param v defines the view for the current state
     * @param event defines the event
     */
    public void onTouch(View v, MotionEvent event);
}
