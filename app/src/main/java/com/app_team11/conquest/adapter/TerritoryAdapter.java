package com.app_team11.conquest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.Territory;

import java.util.List;

/**
 * Adapter for setting the data in Territory
 * Created by Jaydeep9101 on 08-Oct-17.
 */

public class TerritoryAdapter extends BaseAdapter {
    private List<Territory> territoryList;
    private LayoutInflater inflater;

    /**
     * Territory adapter
     * @param context
     * @param territoryList
     */
    public TerritoryAdapter(Context context, List<Territory> territoryList) {
        this.territoryList = territoryList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the count for territory list size
     * @return size of territory list
     */
    @Override
    public int getCount() {
        return territoryList.size();
    }

    /**
     * Returns the territory position
     * @param position
     * @return position of territory
     */
    @Override
    public Territory getItem(int position) {
        return territoryList.get(position);
    }

    /**
     * Returns the item id for the territory
     * @param position
     * @return position of item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * View for teritory
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_territory,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textTerritoryName = (TextView) convertView.findViewById(R.id.text_territory_name);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textTerritoryName.setText(getItem(position).getTerritoryName());
        return convertView;
    }

    /**
     * Sets the territory list adapter
     * @param territoryList
     */
    public void setTerritoryList(List<Territory> territoryList) {
        this.territoryList = territoryList;
    }

    /**
     * View Holder for territory
     */
    class ViewHolder{
        private TextView textTerritoryName;
    }
}
