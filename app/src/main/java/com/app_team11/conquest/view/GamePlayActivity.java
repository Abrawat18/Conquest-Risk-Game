package com.app_team11.conquest.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.PlayerListAdapter;
import com.app_team11.conquest.controller.FortificationPhaseController;
import com.app_team11.conquest.controller.ReinforcementPhaseController;
import com.app_team11.conquest.controller.StartUpPhaseController;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.MathUtility;

/**
 * Created by RADHEY on 10/15/2017
 * version 1.0.0
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
    private Button btnTradeInCards;
    private Toast commonToast;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        if (savedInstanceState == null) {
            initializeView();

            initialization();
        }

    }

    /**
     * method to initialize the view for Game play activity on the screen
     */
    private void initializeView() {
        listPlayer = (ListView) findViewById(R.id.list_player);
        btnStartFortificationPhase = (Button) findViewById(R.id.btn_start_fortification_phase);
        btnTradeInCards = (Button) findViewById(R.id.btn_tradeIn_cards);
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);
        btnStartFortificationPhase.setOnClickListener(this);
        btnTradeInCards.setOnClickListener(this);
    }

    /**
     * method to disable the fortification button during the game play phase
     */
    private void initialization() {
        disableButtonFortificationPhase();
        StartUpPhaseController.getInstance().setContext(this).startStartUpPhase();
        commonToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    /**
     * method to enable the button for fortification start
     */
    public void onStartupPhaseFinished() {
        Toast.makeText(this,"ReInforcement Phase Started !!",Toast.LENGTH_SHORT).show();
        ReinforcementPhaseController.getInstance().setContext(this).startReInforceMentPhase();
        enableButtonFortificationPhase();
    }

    /**
     * method to initialize the player adapter with data
     */
    public void initializePlayerAdapter() {
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

    /**
     * method to initialize the territory object with points from the screen
     * @param x x-coordinate for the selected location for territory on map
     * @param y y-coordinate for the selected location for territory on map
     * @return territory object
     */
    public Territory getTerritoryAtSelectedPoint(int x, int y) {
        for (Territory territory : map.getTerritoryList()) {
            double distanceFromTerritory = MathUtility.getInstance().getDistance(x, y, territory.getCenterPoint().x, territory.getCenterPoint().y);
            if (distanceFromTerritory < Constants.TERRITORY_RADIUS) {
                return territory;
            }
        }
        return null;
    }

    /**
     * method to set the turn of the player in start up and reinforcement phase
     * @param player player object whose turn is to be set
     */
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

    /**
     * method to update the data in adapter
     */
    public void notifyPlayerListAdapter() {
        playerListAdapter.notifyDataSetChanged();
    }

    /**
     *method to set the turn of the player in fortification phase
     *
     */
    public void setNextPlayerTurn() {
        int nextPlayerTurnId = (playerTurn.getPlayerId()) % getMap().getPlayerList().size();
        getMap().changeCurrentPlayerTurn(getMap().getPlayerList().get(nextPlayerTurnId));
        playerTurn = getMap().getPlayerList().get(nextPlayerTurnId);
        playerListAdapter.notifyDataSetChanged();
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    /**
     *  method to create the toast
     * @param msg message string
     */
    public void toastMessageFromBackground(final String msg) {
        commonToast.setText(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commonToast.show();
            }
        });
    }

    /**
     * method to initialise objects and load the map on the screen
     */
    public void showMap() {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(15);
        TextPaint paintText = new TextPaint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(25f);
        paintText.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        TextPaint paintNoOfArmies = new TextPaint();
        paintNoOfArmies.setColor(Color.BLACK);
        paintNoOfArmies.setTextSize(35f);
        paintText.setTypeface(Typeface.create("Arial", Typeface.BOLD));

        canvas = surface.getHolder().lockCanvas();
        for (Territory territory : map.getTerritoryList()) {
            Paint paint = new Paint();
            paint.setColor(territory.getContinent().getContColor());
            canvas.drawCircle(territory.getCenterPoint().x, territory.getCenterPoint().y, Constants.TERRITORY_RADIUS, paint);
            for (Territory territoryNeighbour : territory.getNeighbourList()) {
                canvas.drawLine(territory.getCenterPoint().x, territory.getCenterPoint().y, territoryNeighbour.getCenterPoint().x, territoryNeighbour.getCenterPoint().y, linePaint);
            }
        }
        for (Territory territory : map.getTerritoryList()) {
            String playerID = Integer.toString(territory.getTerritoryOwner().getPlayerId());
            String noOfArmies = Integer.toString(territory.getArmyCount());
            canvas.drawText(("P" + playerID), (territory.getCenterPoint().x) - 30, (territory.getCenterPoint().y) - 20, paintText);
            canvas.drawText(noOfArmies, territory.getCenterPoint().x + 10, territory.getCenterPoint().y + 10, paintNoOfArmies);
        }
        surface.getHolder().unlockCanvasAndPost(canvas);

    }

    /**
     * method to disable button for fortification phase
     */
    private void disableButtonFortificationPhase() {
        btnStartFortificationPhase.setEnabled(false);
        btnStartFortificationPhase.setAlpha(0.4f);
    }

    /**
     * method to enable button for fortification phase
     */
    private void enableButtonFortificationPhase() {
        btnStartFortificationPhase.setEnabled(true);
        btnStartFortificationPhase.setAlpha(1f);
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

    /**
     * {@inheritDoc}
     * @param v view
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        surfaceOnTouchListner.onTouch(v, event);
        return false;
    }

    /**
     * {@inheritDoc}
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_fortification_phase:
                ReinforcementPhaseController.getInstance().stopReInforceMentPhase();
                FortificationPhaseController.getInstance().setContext(this).startFortificationPhase();
                break;
            case R.id.btn_tradeIn_cards:
                ReinforcementPhaseController.getInstance().showCardTradePopUp();
                break;
        }
    }

    /**
     * Method is called when the back is pressed for the startup phase
     */
    @Override
    public void onBackPressed() {
        StartUpPhaseController.getInstance().stopStartupPhase();
        super.onBackPressed();
    }
}
