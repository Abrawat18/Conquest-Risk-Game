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
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class PlayerListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<Player> playerList;

    public PlayerListAdapter(Context context, List<Player> playerList) {
        this.playerList = playerList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Player getItem(int position) {
        return playerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_player,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textPlayerName = (TextView) convertView.findViewById(R.id.text_player_name);
            viewHolder.textPlayerUnAssignedArmy = (TextView) convertView.findViewById(R.id.text_player_unassigned_army);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textPlayerName.setText("Player: "+getItem(position).getPlayerId());
        viewHolder.textPlayerUnAssignedArmy.setText(getItem(position).getAvailableArmyCount()+"");
        viewHolder.textPlayerName.setTypeface(null, Typeface.BOLD);
        if(getItem(position).isMyTurn()){
            viewHolder.textPlayerName.setTextColor(Color.GREEN);
        }else{
            viewHolder.textPlayerName.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    class ViewHolder{
        private TextView textPlayerName;
        private TextView textPlayerUnAssignedArmy;
    }
}
