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

    /**
     * Constructor for GameLogAdapter
     * @param context the running activity instance
     * @param gameLogList game log string contents
     */
    public GameLogAdapter(Context context, List<String> gameLogList) {
        this.gameLogList = gameLogList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *return game log list size
     */
    @Override
    public int getCount() {
        return gameLogList.size();
    }

    /**
     *
     * @param position for the game log list
     * @return item position in game list
     */
    @Override
    public String getItem(int position) {
        return gameLogList.get(position);
    }

    /**
     * @param position for the game log
     * @return position of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position position of the view
     * @param convertView view to be converted to
     * @param parent parent of ViewGroup type
     * @return convertView returns the converted view
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
     * @param gameLogList parameter for the list of game log which is set
     */
    public void setGameLogList(List<String> gameLogList) {
        this.gameLogList = gameLogList;
    }

    /**
     * View Holder for game log adapter
     */
    class ViewHolder {
        private TextView textGameLog;
    }
}
