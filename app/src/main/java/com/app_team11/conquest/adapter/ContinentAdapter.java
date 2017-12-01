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
 * Adapter for setting the data in ContinentAdapter
 * Created by Jaydeep9101 on 08-Oct-17.
 */

public class ContinentAdapter extends BaseAdapter {

    private List<Continent> continentList;
    private LayoutInflater inflater;

    /**
     * Parameterized constructor which takes the context and continent list as input
     * @param context to get running activity instance
     * @param continentList continent list of the map
     */
    public ContinentAdapter(Context context, List<Continent> continentList) {
        this.continentList = continentList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the count of continent size
     * @return continentList size of continent list
     */
    @Override
    public int getCount() {
        return continentList.size();
    }

    /**
     * Returns the position of continent in continent list
     * @param position of the continent
     * @return position integer value for selected continent
     */
    @Override
    public Continent getItem(int position) {
        return continentList.get(position);
    }

    /**
     * Returns the position of item
     * @param position of the item
     * @return position returns the item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns the view
     * @param position of the view
     * @param convertView the view which has to be converted to
     * @param parent type ViewGroup parent
     * @return convertView the converted view
     */
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

    /**
     * Continent view holder for the continent adapter
     */
    class ViewHolder{
        private TextView textContinentName;
    }
}
