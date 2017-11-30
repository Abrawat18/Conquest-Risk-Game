package com.app_team11.conquest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.TournamentResultModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Adapter for passing data from view to XML for the result of tournament
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultAdapter extends BaseAdapter {

    private List<String> resultList;
    private LayoutInflater inflater;

    /**
     * Constructor for tournament result adapter
     * @param context
     * @param resultList as List<String>
     */
    public TournamentResultAdapter(Context context, List<String> resultList) {
        this.resultList = resultList;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gives the count of the result list
     *
     * @return size of result list
     */
    @Override
    public int getCount() {
        return resultList.size();
    }

    /**
     * @param position gives the position of the result list
     * @return returnList gives the position of the return list
     */
    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    /**
     * @param position gives the position of the item
     * @return position place of item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position    place of view
     * @param convertView view is displayed as per the null or already existing
     * @param parent      inherits the view from parent
     * @return convertView dispays the view on that position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.winnerValue = (TextView) convertView.findViewById(R.id.text_winner_player);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.winnerValue.setText(getItem(position));
        return convertView;
    }

    /**
     * This class holds the view
     */
    class ViewHolder {
        private TextView winnerValue;
    }
}
