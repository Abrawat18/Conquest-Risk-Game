package com.app_team11.conquest.view;

import android.content.Context;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class FortificationPhase {

    private Context context;

    public FortificationPhase(Context context) {
        this.context = context;
    }

    public GamePlayActivity getActivity(){
        return (GamePlayActivity) context;
    }
}
