package com.app_team11.conquest.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.GameMap;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

//Todo : editor menu : 1. add contenent 2. add teritory 3. on terirtory  selection : show contenent or select contenet , neighbour


public class MapEditorActivity extends Activity implements View.OnTouchListener {

    private GameMap map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_editor);
        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Do some drawing when surface is ready
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.RED);
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                canvas.drawCircle(120f,130f,100f,paint);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    private void setMap(GameMap map){
        this.map = map;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(this,"X:"+event.getX()+" Y:"+event.getY(),Toast.LENGTH_SHORT).show();
        return false;
    }
}
