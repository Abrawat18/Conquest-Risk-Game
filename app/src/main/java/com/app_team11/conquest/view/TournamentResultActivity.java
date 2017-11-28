
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
        import com.app_team11.conquest.global.Constants;
        import com.app_team11.conquest.model.TournamentResultModel;
        import com.app_team11.conquest.utility.FileManager;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultActivity extends Activity {

    private GridView gridResultTable;
    private TournamentResultAdapter resultAdapter;
    private Bundle bundle;
    private int numberOfGames;

    /**
     * {@inheritDoc}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        numberOfGames=bundle.getInt(Constants.KEY_TOURNAMENT_GAMES_COUNT);
        setContentView(R.layout.activity_tournament_result);
        gridResultTable = (GridView) findViewById(R.id.grid_result);

        initializeView();
    }

    private void initializeView() {
        List<TournamentResultModel> resultList = new ArrayList<TournamentResultModel>(); //this needs to be fetched
        resultAdapter = new TournamentResultAdapter(this,resultList);
        gridResultTable.setNumColumns(numberOfGames);
        gridResultTable.setAdapter(resultAdapter);

    }

}