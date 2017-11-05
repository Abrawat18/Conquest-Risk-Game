package com.app_team11.conquest.controller;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.CardListAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Cards;
import com.app_team11.conquest.model.ReinforcementType;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.view.GamePlayActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class ReInforcementPhaseController implements SurfaceOnTouchListner {

    private Context context;
    private static StartUpPhaseController startUpPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private static ReInforcementPhaseController reInforcementPhaseController;
    private int needToPlaceArmy;
    private List<Cards> cardList = new ArrayList<Cards>();
    private List<Territory> needToPlaceTerrArmyList;

    private ReInforcementPhaseController() {

    }

    /**
     * singleton implementation for Reinforcement controller
     *
     * @return returns the class object
     */
    public static ReInforcementPhaseController getInstance() {
        if (reInforcementPhaseController == null) {
            reInforcementPhaseController = new ReInforcementPhaseController();
        }
        return reInforcementPhaseController;
    }

    /**
     * setting the context variable for reinforcement phase
     *
     * @param context
     * @return
     */
    public ReInforcementPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    /**
     * method used to start the reinforcement phase in the play game
     */
    public void startReInforceMentPhase() {
        Territory terr1 = new Territory("Anguilla");
        Territory terr2 = new Territory("Armenia");
        Territory terr3 = new Territory("Bangladesh");
        cardList.add(new Cards(terr1, "infantry"));
        cardList.add(new Cards(terr2, "artillery"));
        cardList.add(new Cards(terr3, "cavalry"));
        cardList.add(new Cards(terr2, "artillery"));
        cardList.add(new Cards(terr1, "infantry"));
        cardList.add(new Cards(terr3, "cavalry"));
        cardList.add(new Cards(terr1, "infantry"));
        cardList.add(new Cards(terr2, "artillery"));
        cardList.add(new Cards(terr3, "cavalry"));
        cardList.add(new Cards(terr2, "artillery"));

        if (getActivity().getMap().getPlayerList().size() > 0) {
            getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
            waitForSelectTerritory = true;
            calculateReinforcementArmyForPlayer(null);
        }
    }

    /**
     * method to stop the reinforcement phase
     */
    public void stopReInforceMentPhase() {
        waitForSelectTerritory = false;
    }

    /**
     * {@inheritDoc}
     *
     * @param v
     * @param event
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                if (needToPlaceArmy > 0) {
                    askUserToPlaceNoOfArmyToSelectedTerritory(false);
                    getActivity().notifyPlayerListAdapter();
                } else if (getActivity().getPlayerTurn().getAvailableCardTerrCount() > 0) {
                    boolean isTerrFound = false;
                    for (Territory terrObj : needToPlaceTerrArmyList) {
                        if (selectedTerritory.getTerritoryName().equals(terrObj.getTerritoryName().toString())) {
                            isTerrFound = true;
                            askUserToPlaceNoOfArmyToSelectedTerritory(true);
                            getActivity().notifyPlayerListAdapter();
                            break;
                        }
                    }
                    if (!isTerrFound)
                        getActivity().toastMessageFromBackground("Please select the territory for which the card is exchanged");
                }
            } else {
                getActivity().toastMessageFromBackground("Place army on correct territory !!");
            }
        }

    }

    /**
     * method to take inputs from user for placing the army in selected territory in reinforcement phase
     */
    private void askUserToPlaceNoOfArmyToSelectedTerritory(final boolean isMatchedCardTerrArmy) {
        final EditText editNoOfArmy = new EditText(getActivity());
        editNoOfArmy.setInputType(InputType.TYPE_CLASS_NUMBER);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Enter no of army")
                .setConfirmText("Ok")
                .setCustomView(editNoOfArmy)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editNoOfArmy.getText().toString())) {
                            int requestedToPlaceArmy = Integer.parseInt(editNoOfArmy.getText().toString());
                            if ((needToPlaceArmy >= requestedToPlaceArmy && !isMatchedCardTerrArmy) || (getActivity().getPlayerTurn().getAvailableCardTerrCount() >= requestedToPlaceArmy && isMatchedCardTerrArmy)) {
                                ConfigurableMessage configurableMessage = selectedTerritory.addArmyToTerr(requestedToPlaceArmy, isMatchedCardTerrArmy);
                                if (configurableMessage.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                    if (!isMatchedCardTerrArmy)
                                        needToPlaceArmy -= requestedToPlaceArmy;
                                    getActivity().notifyPlayerListAdapter();
                                    getActivity().showMap();
                                }
                                if (needToPlaceArmy == 0 && getActivity().getPlayerTurn().getAvailableCardTerrCount() == 0) {
                                    getActivity().setNextPlayerTurn();
                                    calculateReinforcementArmyForPlayer(null);
                                }
                                getActivity().toastMessageFromBackground("Select territory to place Army :" + needToPlaceArmy);
                            } else {
                                getActivity().toastMessageFromBackground("Invalid army place count");
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * method to call the function for calculation of reinforcement army on the start of the player turn
     */
    private void calculateReinforcementArmyForPlayer(List<Cards> tradeInCardList) {
        if (getActivity().getPlayerTurn().getOwnedCards().size() < 5 || (tradeInCardList != null && tradeInCardList.size() == 3)) {
            ReinforcementType reinforcementType = getActivity().getPlayerTurn().calcReinforcementArmy(getActivity().getMap(), getActivity().getMap().getNoOfCardTradedCount(), tradeInCardList);
            needToPlaceArmy = reinforcementType.getOtherTotalReinforcement();
            needToPlaceTerrArmyList = null;
            if (reinforcementType.getMatchedTerrCardReinforcement() != 0) {
                needToPlaceTerrArmyList = reinforcementType.getMatchedTerritoryList();
                getActivity().getPlayerTurn().setAvailableCardTerrCount(reinforcementType.getMatchedTerrCardReinforcement());
            }
            if (null != tradeInCardList)
                getActivity().getMap().increaseCardTradedCount();

            getActivity().getPlayerTurn().setAvailableArmyCount(needToPlaceArmy);
            getActivity().toastMessageFromBackground("Place Army:" + needToPlaceArmy);
            getActivity().notifyPlayerListAdapter();
        }
    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

    public void showCardTradePopUp() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_player_cards);
        final CardListAdapter cardListAdapter = new CardListAdapter(getActivity(), cardList);
        dialog.setTitle("Trade-In Cards");
        GridView cardGrid = (GridView) dialog.findViewById(R.id.grid_card);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_tradeIn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cards> selectedCardList = new ArrayList<Cards>();
                for (Cards card : cardList) {
                    if (card.isSelected()) {
                        selectedCardList.add(card);
                    }
                }
                if (selectedCardList.size() == 3) {
                    calculateReinforcementArmyForPlayer(selectedCardList);
                }
            }
        });

        cardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int noOfSelectedCards = 0;
                if (!cardList.get(position).isSelected()) {
                    for (Cards card : cardList) {
                        if (card.isSelected()) {
                            noOfSelectedCards++;
                        }
                        if (noOfSelectedCards > 3) {
                            getActivity().toastMessageFromBackground(Constants.TOAST_MSG_MAX_CARDS_SELECTION_ERROR);
                            break;
                        }
                    }
                }
                if (noOfSelectedCards <= 3 || cardList.get(position).isSelected()) {
                    cardList.get(position).setSelected(!cardList.get(position).isSelected());
                }
                cardListAdapter.notifyDataSetChanged();
            }
        });
//        cardGrid.setAdapter(new CardListAdapter(getActivity(), getActivity().getPlayerTurn().getOwnedCards()));
        cardGrid.setAdapter(cardListAdapter);
        dialog.show();

    }
}
