package com.app_team11.conquest.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.CardListAdapter;
import com.app_team11.conquest.adapter.GameLogAdapter;
import com.app_team11.conquest.adapter.PlayerListAdapter;
import com.app_team11.conquest.controller.AttackPhaseController;
import com.app_team11.conquest.controller.FortificationPhaseController;
import com.app_team11.conquest.controller.ReinforcementPhaseController;
import com.app_team11.conquest.controller.StartUpPhaseController;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Cards;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.ObserverType;
import com.app_team11.conquest.model.PhaseViewModel;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.utility.GamePhaseManager;
import com.app_team11.conquest.utility.MathUtility;
import com.app_team11.conquest.utility.ReadMapUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Game Play Activity view
 * Created by RADHEY on 10/15/2017
 * version 1.0.0
 */


public class GamePlayActivity extends Activity implements View.OnTouchListener, View.OnClickListener, Observer {

    private GameMap map;
    private SurfaceView surface;
    private Canvas canvas;
    private SurfaceOnTouchListner surfaceOnTouchListner;
    private ListView listPlayer;
    private Button btnStopAttack;
    private Button btnStopFortification;
    private Button btnNewAttack;
    private PlayerListAdapter playerListAdapter;
    private Button btnTradeInCards;
    private Toast commonToast;
    private List<String> phaseViewList = new ArrayList<>();
    private CardListAdapter cardListAdapter;
    private GameLogAdapter phaseViewAdapter;
    private LinearLayout linearWorldDominationView;
    private List<TextView> textViewPlayerDominationList = new ArrayList<>();
    private ListView listPhaseView;
    private Button btnGameSave;
    private EditText editFileName;


    /**
     * {@inheritDoc}
     *
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
        listPhaseView = (ListView) findViewById(R.id.list_phase_view);
        btnStopAttack = (Button) findViewById(R.id.btn_stop_attack);
        btnGameSave = (Button) findViewById(R.id.btn_save_map);
        btnStopFortification = (Button) findViewById(R.id.btn_stop_fortification);
        btnNewAttack = (Button) findViewById(R.id.btn_new_attack);
        btnTradeInCards = (Button) findViewById(R.id.btn_tradeIn_cards);
        findViewById(R.id.btn_show_log).setOnClickListener(this);
        linearWorldDominationView = (LinearLayout) findViewById(R.id.linear_world_domination_view);
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);
        btnStopAttack.setOnClickListener(this);
        btnStopFortification.setOnClickListener(this);
        btnNewAttack.setOnClickListener(this);
        btnGameSave.setOnClickListener(this);
        btnTradeInCards.setOnClickListener(this);
        FileManager.getInstance().writeLog("Game Play View Initialized !!");
    }

    /**
     * method to disable the fortification button during the game play phase
     */
    private void initialization() {
        PhaseViewModel.getInstance().clearString();
        phaseViewAdapter = new GameLogAdapter(this, phaseViewList);
        listPhaseView.setAdapter(phaseViewAdapter);
        PhaseViewModel.getInstance().addObserver(this);
        commonToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        getDataFromBundleAndInitializeMap();
    }

    /**
     * Update phase view
     */
    private void updateCreatePhaseView() {
        phaseViewList = PhaseViewModel.getInstance().getListPhaseViewContent();
        phaseViewAdapter.setGameLogList(phaseViewList);
        notifyPhaseViewAdapter();
    }

    /**
     * method to initialize map for game play, set the number of players
     */
    private void getDataFromBundleAndInitializeMap() {
        String filePathToLoad = null;
        String loadSavedMapPath = null;
        Intent intent = getIntent();
        List<String> playerList = null;
        int noOfPlayer = 0;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                filePathToLoad = bundle.getString(Constants.KEY_FILE_PATH);
                playerList = bundle.getStringArrayList(Constants.KEY_PLAYER_LIST);
                loadSavedMapPath = bundle.getString(Constants.KEY_LOAD_SAVED_MAP_PATH);
                noOfPlayer = bundle.getInt(Constants.KEY_NO_OF_PLAYER);
                FileManager.getInstance().writeLog("Number of players for game play - " + noOfPlayer);
            }
        }

        if (!TextUtils.isEmpty(loadSavedMapPath)) {
            setMap(FileManager.getInstance().readObjectFromFile(loadSavedMapPath));
            if (getMap() != null) {
                getMap().loadPlayerStrategyToGame();
            }
        } else if (!TextUtils.isEmpty(filePathToLoad) && noOfPlayer > 0) {
            FileManager.getInstance().writeLog("Initializing map for game play...");
            setMap(new ReadMapUtility(this).readFile(filePathToLoad));
            getMap().addPlayerToGame(noOfPlayer, playerList);
        }
        if (getMap() != null) {
            initializePlayerAdapter();
            loadGamePhase();
        } else {
            Toast.makeText(this, "Invalid input please try again later !!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get player turn from game map
     *
     * @return
     */
    public Player getPlayerTurn() {
        return getMap().getPlayerTurn();
    }

    /**
     * method to enable the button for fortification start
     */
    public void onStartupPhaseFinished() {
        Toast.makeText(this, "Reinforcement Phase Started !!", Toast.LENGTH_SHORT).show();
        if (getMap() != null) {
            if (getMap().getPlayerList().size() > 0) {
                setPlayerTurn(getMap().getPlayerList().get(0));
                getMap().getPlayerTurn().addObserver(this);
            }
            changeGamePhase();
        }
    }

    /**
     * event on Completing Reinforcement Phase
     */
    public void onReInforcementPhaseCompleted() {
        FileManager.getInstance().writeLog("Reinforcement Phase Completed");
        changeGamePhase();
    }


    /**
     * event on stopping Attack Phase
     */
    public void onAttackPhaseStopped() {
        FileManager.getInstance().writeLog("Attack phase stopped !!");
        if (AttackPhaseController.getInstance().isPhaseWonFlag()) {
            Cards randomCard = getMap().getRandomCardFromDeck();
            getMap().getPlayerTurn().getOwnedCards().add(randomCard); //adding the card to the player
            getMap().removeCardFromDeck(randomCard); //removing from the deck of cards
        }
        changeGamePhase();
    }

    /**
     * event on Completing Fortification Phase
     */
    public void onFortificationPhaseStopped() {
        FileManager.getInstance().writeLog("Fortification Phase Completed");
        setNextPlayerTurn();
        changeGamePhase();
    }

    /**
     * Changing game phase
     */
    public void changeGamePhase() {
        if (getMap() != null) {
            getMap().getGamePhaseManager().changePhase();
            loadGamePhase();
        }
    }

    public void loadGamePhase() {
        switch (getMap().getGamePhaseManager().getCurrentPhase()) {
            case GamePhaseManager.PHASE_STARTUP:
                showMap();
                btnStopAttack.setVisibility(View.GONE);
                btnNewAttack.setVisibility(View.GONE);
                btnStopFortification.setVisibility(View.GONE);
                FileManager.getInstance().writeLog("Game Startup phase starting...");
                StartUpPhaseController.getInstance().setContext(this).startStartUpPhase();
                break;
            case GamePhaseManager.PHASE_REINFORCEMENT:
                showMap();
                btnStopAttack.setVisibility(View.GONE);
                btnNewAttack.setVisibility(View.GONE);
                btnStopFortification.setVisibility(View.GONE);
                FileManager.getInstance().writeLog("Reinforcement phase starting...");
                ReinforcementPhaseController.getInstance().setContext(this).startReInforceMentPhase();
                break;
            case GamePhaseManager.PHASE_ATTACK:
                btnStopAttack.setVisibility(View.VISIBLE);
                btnNewAttack.setVisibility(View.VISIBLE);
                btnStopFortification.setVisibility(View.GONE);
                Toast.makeText(this, "Attack Phase Started !!", Toast.LENGTH_SHORT).show();
                FileManager.getInstance().writeLog("Attack phase starting...");
                AttackPhaseController.getInstance().setContext(this).startAttackPhase();
                break;
            case GamePhaseManager.PHASE_FORTIFICATION:
                btnStopAttack.setVisibility(View.GONE);
                btnNewAttack.setVisibility(View.GONE);
                btnStopFortification.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Fortification Phase Started !!", Toast.LENGTH_SHORT).show();
                FileManager.getInstance().writeLog("Fortification Phase starting...");
                FortificationPhaseController.getInstance().setContext(this).startFortificationPhase();
                break;
        }
    }


    /**
     * method to initialize the world domination view
     */
    public void initializeDominationView(int playerCount) {

        FileManager.getInstance().writeLog("Initializing world Domination view...");
        for (int i = 1; i <= playerCount; i++) {
            TextView tv = new TextView(this);
            tv.setText("P" + i);
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(setLayoutParamsForPlayer(20));
            linearWorldDominationView.addView(tv);
            textViewPlayerDominationList.add(tv);

        }
        FileManager.getInstance().writeLog("World Domination view Initialized!!");
    }

    /**
     * method to update the world domination view
     */
    public void updateDominationView() {
        int totalTerr = getMap().getTerritoryList().size();
        int[] playerColor = getResources().getIntArray(R.array.continentColor);
        FileManager.getInstance().writeLog("Updating world domination view...");
        for (Player player : getMap().getPlayerList()) {
            int totalPlayerTerrCount = getMap().getTerrForPlayer(player).size();
            int percDomination = (totalPlayerTerrCount * 100 / totalTerr);
            textViewPlayerDominationList.get(player.getPlayerId() - 1).setLayoutParams(setLayoutParamsForPlayer(percDomination));
            textViewPlayerDominationList.get(player.getPlayerId() - 1).setBackgroundColor(playerColor[playerColor.length - player.getPlayerId()]);
        }
        FileManager.getInstance().writeLog("Updated world domination view!!");
    }

    /**
     * Layout parameters for players
     *
     * @param playerDominationPercent
     * @return lp
     */
    private LinearLayout.LayoutParams setLayoutParamsForPlayer(int playerDominationPercent) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = playerDominationPercent;
        return lp;
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

    /**
     * Action to be performed when touch on surface
     *
     * @param surfaceOnTouchListner
     */
    public void setSurfaceOnTouchListner(SurfaceOnTouchListner surfaceOnTouchListner) {
        this.surfaceOnTouchListner = surfaceOnTouchListner;
    }

    /**
     * Sets the map for view
     *
     * @param map
     */
    public void setMap(GameMap map) {
        this.map = map;
        if (map == null || !map.isGraphConnected()) {
            toastMessageFromBackground(Constants.TOAST_ERROR_GRAPH_NOT_CONNECTED);
            finish();
        }
    }

    /**
     * Returns the map view
     *
     * @return map
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * method to initialize the territory object with points from the screen
     *
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
     *
     * @param player player object whose turn is to be set
     */
    public void setPlayerTurn(Player player) {
        getMap().setPlayerTurn(player);
        getMap().changeCurrentPlayerTurn(player);
        FileManager.getInstance().writeLog("Player turn ->" + getMap().getPlayerTurn().getPlayerId());
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
     * method to set the turn of the player in fortification phase
     */
    public void setNextPlayerTurn() {
        int nextPlayerTurnId = (getMap().getPlayerTurn().getPlayerId()) % getMap().getPlayerList().size();
        getMap().changeCurrentPlayerTurn(getMap().getPlayerList().get(nextPlayerTurnId));
        getMap().setPlayerTurn(getMap().getPlayerList().get(nextPlayerTurnId));
        getMap().getPlayerTurn().addObserver(this);
        playerListAdapter.notifyDataSetChanged();
    }

    /**
     * method to create the toast
     *
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
        if (map != null) {
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
            if (canvas != null) {
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

        }
    }

    /**
     * Initializing surface Call back
     */
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            showMap();
        }

        /**
         * Initializing the surface destroy
         * @param holder
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            holder.removeCallback(surfaceCallback);
        }

        /**
         * Initializing the change in surface
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    };

    /**
     * {@inheritDoc}
     *
     * @param v     view
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
     *
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stop_attack:
                onAttackPhaseStopped();
                break;
            case R.id.btn_save_map:
                saveGame();
                break;
            case R.id.btn_stop_fortification:
                onFortificationPhaseStopped();
                break;
            case R.id.btn_new_attack:
                AttackPhaseController.getInstance().startAttackPhase();
                break;
            case R.id.btn_tradeIn_cards:
                if (getMap().getPlayerTurn().getOwnedCards().size() > 0)
                    showCardTradePopUp();
                else
                    Toast.makeText(this, "You have no cards yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_show_log:
                Intent intent = new Intent(this, GameLogActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    public void saveGame() {
        editFileName = new EditText(this);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Please enter game name")
                .setConfirmText("Ok")
                .setCustomView(editFileName)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        File objectFile = null;
                        if (!TextUtils.isEmpty(editFileName.getText())) {
                            objectFile = FileManager.getInstance().getSerializableFilePath(editFileName.getText().toString() + ".ser");
                            boolean isGameSaved = FileManager.getInstance().writeObjectIntoFile(getMap(), objectFile);
                            if (isGameSaved) {
                                Toast.makeText(GamePlayActivity.this, Constants.GAME_SAVE_SUCCESS_MEG, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * Method is called when the back is pressed for the startup phase
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * On Resume from some events
     */
    @Override
    protected void onResume() {
        super.onResume();
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);
        showMap();
    }

    /**
     * Update the view
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            if (arg != null) {
                ObserverType observerType = (ObserverType) arg;
                if (observerType != null) {
                    if (observerType.getObserverType() == ObserverType.WORLD_DOMINATION_TYPE) {
                        updateDominationView();
                        return;
                    }
                }
            }
        } catch (Exception ex) {

        }

        notifyCardListAdapter();
        updateCreatePhaseView();
    }

    /**
     * Notify the card list
     */
    public void notifyCardListAdapter() {
        if (cardListAdapter != null) {
            cardListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Notify the phase view adapater
     */
    public void notifyPhaseViewAdapter() {
        if (phaseViewAdapter != null) {
            phaseViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Show card trade
     */
    public void showCardTradePopUp() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_player_cards);
        cardListAdapter = new CardListAdapter(this, getMap().getPlayerTurn().getOwnedCards());
        dialog.setTitle("Trade-In Cards");
        GridView cardGrid = (GridView) dialog.findViewById(R.id.grid_card);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_tradeIn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cards> selectedCardList = new ArrayList<Cards>();
                for (Cards card : getMap().getPlayerTurn().getOwnedCards()) {
                    if (card.isSelected()) {
                        selectedCardList.add(card);
                    }
                }
                if (selectedCardList.size() == 3) {
                    FileManager.getInstance().writeLog("Trading card for Player " + getMap().getPlayerTurn().getPlayerId());
                    ReinforcementPhaseController.getInstance().calculateReinforcementArmyForPlayer(selectedCardList);

                }
            }
        });
/**
 * On click listner for cards
 */
        cardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int noOfSelectedCards = 0;
                if (!getMap().getPlayerTurn().getOwnedCards().get(position).isSelected()) {
                    for (Cards card : getMap().getPlayerTurn().getOwnedCards()) {
                        if (noOfSelectedCards >= 3) {
                            toastMessageFromBackground(Constants.TOAST_MSG_MAX_CARDS_SELECTION_ERROR);
                            break;
                        }
                        if (card.isSelected()) {
                            noOfSelectedCards++;
                        }
                    }
                }
                if (noOfSelectedCards < 3 || getMap().getPlayerTurn().getOwnedCards().get(position).isSelected()) {
                    getMap().getPlayerTurn().getOwnedCards().get(position).setSelected(!getMap().getPlayerTurn().getOwnedCards().get(position).isSelected());
                }
                cardListAdapter.notifyDataSetChanged();
            }
        });
        cardGrid.setAdapter(cardListAdapter);
        dialog.show();

    }

}
