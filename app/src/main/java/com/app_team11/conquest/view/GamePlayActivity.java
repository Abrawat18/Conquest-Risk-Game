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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.PlayerListAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.MathUtility;

/**
 * Created by RADHEY on 10/15/2017.
 */


public class GamePlayActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private GameMap map;
    private SurfaceView surface;
    private Canvas canvas;
    private SurfaceOnTouchListner surfaceOnTouchListner;
    private Player playerTurn;
    private ListView listPlayer;
    private Button btnStartFortificationPhase;
    private PlayerListAdapter playerListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        if (savedInstanceState == null) {
            initializeView();

            initialization();
        }

    }


    private void initializeView() {
        listPlayer = (ListView) findViewById(R.id.list_player);
        btnStartFortificationPhase = (Button) findViewById(R.id.btn_start_fortification_phase);
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);

        btnStartFortificationPhase.setOnClickListener(this);
    }

    private void initialization() {
        StartUpPhaseController.getInstance().setContext(this).startStartUpPhase();
    }

    public void initializePlayerAdapter(){
        if (getMap().getPlayerList() != null) {
            playerListAdapter = new PlayerListAdapter(this, getMap().getPlayerList());
            listPlayer.setAdapter(playerListAdapter);
        }
    }

    public void setSurfaceOnTouchListner(SurfaceOnTouchListner surfaceOnTouchListner) {
        this.surfaceOnTouchListner = surfaceOnTouchListner;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }


    public Territory getTerritoryAtSelectedPoint(int x, int y) {
        for (Territory territory : map.getTerritoryList()) {
            double distanceFromTerritory = MathUtility.getInstance().getDistance(x, y, territory.getCenterPoint().x, territory.getCenterPoint().y);
            if (distanceFromTerritory < Constants.TERRITORY_RADIUS) {
                return territory;
            }
        }
        return null;
    }


    public void setPlayerTurn(Player player) {
        playerTurn = player;
        getMap().changeCurrentPlayerTurn(player);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playerListAdapter.notifyDataSetChanged();
            }
        });
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void toastMessageFromBackground(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMap() {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(15);
        canvas = surface.getHolder().lockCanvas();
        for (Territory territory : map.getTerritoryList()) {
            Paint paint = new Paint();
            paint.setColor(territory.getContinent().getContColor());
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
        surfaceOnTouchListner.onTouch(v, event);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_fortification_phase:
                break;
        }
    }
}
