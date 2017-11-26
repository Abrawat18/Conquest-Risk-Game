package com.app_team11.conquest.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.utility.FileManager;

/**
 * Created by RADHEY on 11/25/2017.
 */

public class GamePlayerTypeActivity extends Activity implements View.OnClickListener {

    /**
     * {@inheritDoc}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_type);
        findViewById(R.id.btn_add_player_type).setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_player_type:
                Spinner mySpinner = (Spinner) findViewById(R.id.planets_spinner);
                String text = mySpinner.getSelectedItem().toString();
                Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        }
    }
}
