package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.app_team11.conquest.R;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ReadMapUtility;

/**
 * Created by RADHEY on 10/15/2017.
 */


public class GamePlayActivity extends Activity implements View.OnTouchListener {

    private GameMap map;
    private SurfaceView surface;
    private Canvas canvas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initializeView();

        initialization();

    }

    private void initializeView() {
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);
    }

    private void initialization() {
        StartUpPhaseController.getInstance().setContext(this).startStartUpPhase();
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }

    private void showMap() {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        canvas = surface.getHolder().lockCanvas();
        for (Territory territory : map.getTerritoryList()) {
            canvas.drawCircle(territory.getCenterPoint().x, territory.getCenterPoint().y, Constants.TERRITORY_RADIUS, paint);
            for (Territory territoryNeighbour : territory.getNeighbourList()) {
                canvas.drawLine(territory.getCenterPoint().x, territory.getCenterPoint().y, territoryNeighbour.getCenterPoint().x, territoryNeighbour.getCenterPoint().y, linePaint);
            }
        }
        surface.getHolder().unlockCanvasAndPost(canvas);

    }

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            showMap();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            holder.removeCallback(surfaceCallback);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
