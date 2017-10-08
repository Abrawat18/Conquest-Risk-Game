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
 * Created by Jaydeep9101 on 08-Oct-17.
 */

public class ContinentAdapter extends BaseAdapter {

    private List<Continent> continentList;
    private LayoutInflater inflater;

    public ContinentAdapter(Context context, List<Continent> continentList) {
        this.continentList = continentList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return continentList.size();
    }

    @Override
    public Continent getItem(int position) {
        return continentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_continent,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textContinentName = (TextView) convertView.findViewById(R.id.text_continent_name);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textContinentName.setText(getItem(position).getContName());
        return convertView;
    }

    class ViewHolder{
        private TextView textContinentName;
    }
}
