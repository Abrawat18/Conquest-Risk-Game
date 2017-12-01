package com.app_team11.conquest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.Player;

import java.util.List;

/**
 * Adapter for setting the data in PlayerListAdapter
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class PlayerListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<Player> playerList;

    /**
     * Adapter for setting the data in PlayerListAdapter
     * @param context the running activity instance
     * @param playerList the list of players of the map
     */
    public PlayerListAdapter(Context context, List<Player> playerList) {
        this.playerList = playerList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return size of player list
     */
    @Override
    public int getCount() {
        return playerList.size();
    }

    /**
     * @param position parameter for the position of player list
     * @return player list position
     */
    @Override
    public Player getItem(int position) {
        return playerList.get(position);
    }

    /**
     * @param position parameter for the position of player list
     * @return [position integer value for the player position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position view position
     * @param convertView view to be loaded
     * @param parent group parent
     * @return convertView returns the converted view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_player,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textPlayerName = (TextView) convertView.findViewById(R.id.text_player_name);
            viewHolder.textPlayerUnAssignedArmy = (TextView) convertView.findViewById(R.id.text_player_unassigned_army);
            viewHolder.textPlayerTerrArmy = (TextView) convertView.findViewById(R.id.text_player_unassigned_terr_army);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textPlayerName.setText("P"+getItem(position).getPlayerId()+": "+getItem(position).getPlayerStrategyType());
        viewHolder.textPlayerName.setTextSize(12);
        viewHolder.textPlayerUnAssignedArmy.setText(getItem(position).getAvailableArmyCount()+"");
        viewHolder.textPlayerTerrArmy.setText(getItem(position).getAvailableCardTerrCount()+"");
        viewHolder.textPlayerName.setTypeface(null, Typeface.BOLD);
        if(getItem(position).isMyTurn()){
            viewHolder.textPlayerName.setTextColor(Color.GREEN);
        }else{
            viewHolder.textPlayerName.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    /**
     * View Holder for Player List
     */
    class ViewHolder{
        private TextView textPlayerName;
        private TextView textPlayerUnAssignedArmy;
        private TextView textPlayerTerrArmy;
    }
}
