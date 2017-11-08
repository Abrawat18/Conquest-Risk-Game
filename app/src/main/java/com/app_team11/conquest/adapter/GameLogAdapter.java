package com.app_team11.conquest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.Continent;

import java.util.List;

/**
 * Adapter for setting the data in GameLogAdapter
 * Created by Abhishek on 04-Nov-17.
 */

public class GameLogAdapter extends BaseAdapter {

    private List<String> gameLogList;
    private LayoutInflater inflater;

    public GameLogAdapter(Context context, List<String> gameLogList) {
        this.gameLogList = gameLogList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the count for game log lost
     * @return game log list
     */
    @Override
    public int getCount() {
        return gameLogList.size();
    }

    /**
     * returns the item position
     * @param position
     * @return item position
     */
    @Override
    public String getItem(int position) {
        return gameLogList.get(position);
    }

    /**
     * Returns the item position
     * @param position
     * @return item position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns the view
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_game_log, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textGameLog = (TextView) convertView.findViewById(R.id.text_game_log);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textGameLog.setText(getItem(position));
        return convertView;
    }

    /**
     * setter method for game list
     * @param gameLogList
     */
    public void setGameLogList(List<String> gameLogList) {
        this.gameLogList = gameLogList;
    }

    /**
     * View Holder
     */
    class ViewHolder {
        private TextView textGameLog;
    }
}
