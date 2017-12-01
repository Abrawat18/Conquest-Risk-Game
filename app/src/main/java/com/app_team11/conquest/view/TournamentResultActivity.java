
package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.TournamentResultAdapter;
import com.app_team11.conquest.controller.MainDashboardController;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.TournamentResultModel;
import com.app_team11.conquest.utility.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity gives the result of the tournament in the form of the table which is dynamic in nature.
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultActivity extends Activity {

    private GridView gridResultTable;
    private TournamentResultAdapter resultAdapter;
    private Bundle bundle;
    private int numberOfGames;
    private List<String> resultList;

    /**
     * {@inheritDoc}
     *  This method is called on creation of the activity which allows user to choose the player type
     * @param savedInstanceState saves the view in instance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        numberOfGames = bundle.getInt(Constants.KEY_NUMBER_GAMES);
        resultList = bundle.getStringArrayList(Constants.KEY_TOURNAMENT_RESULT_LIST);
        setContentView(R.layout.activity_tournament_result);
        gridResultTable = (GridView) findViewById(R.id.grid_result);
        findViewById(R.id.btn_show_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDashboardController.getInstance().openGameLog();
            }
        });
        initializeView();
    }

    /**
     * This class initializes the view
     */
    private void initializeView() {

        resultAdapter = new TournamentResultAdapter(this, resultList);
        gridResultTable.setNumColumns(numberOfGames + 1);
        gridResultTable.setAdapter(resultAdapter);

    }

}
