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
import java.util.List;

/**
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultAdapter extends BaseAdapter {

    private List<TournamentResultModel> resultList;
    private LayoutInflater inflater;

    public TournamentResultAdapter(Context context, List<TournamentResultModel> resultList) {
        this.resultList = resultList;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public TournamentResultModel getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_result,parent, false);
            viewHolder=new ViewHolder();
            viewHolder.winnerValue= (TextView) convertView.findViewById(R.id.text_winner_player);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.winnerValue.setText(getItem(position).getPlayerWon().getPlayerStrategyType());
        return convertView;
    }

    class ViewHolder{
        private TextView winnerValue;
    }
}
