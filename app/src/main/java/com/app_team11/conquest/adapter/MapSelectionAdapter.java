package com.app_team11.conquest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.MapFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Adapter for setting the data in MapSelectionAdapter
 * Created by Jaydeep9101 on 17-Oct-17.
 */

public class MapSelectionAdapter extends BaseAdapter {

    private List<MapFile> mapFileList;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Constructor for MapSelectionAdapter
     * @param mapFileList contains the list of map files
     * @param context the running activity instance
     */
    public MapSelectionAdapter(List<MapFile> mapFileList, Context context) {
        this.mapFileList = mapFileList;
        this.context = context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * returns the size of the mapfilelist
     * @return map file list length
     */
    @Override
    public int getCount() {
        return mapFileList.size();
    }
    /**
     * returns the item in the list for the given position
     * @return map file list position
     */
    @Override
    public MapFile getItem(int position) {
        return mapFileList.get(position);
    }
    /**
     * returns the position number
     * @return map file list item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Method gets the view
     * @param position position of the view
     * @param convertView View to be loaded
     * @param parent group parent
     * @return convertView converted view is returned
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            convertView= inflater.inflate(R.layout.list_row_map_selection,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textMapName = (TextView) convertView.findViewById(R.id.text_map_name);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textMapName.setText(getItem(position).getMapFiles().getName());
        if(getItem(position).isSelected()){
            viewHolder.textMapName.setBackgroundColor(Color.YELLOW);
        }
        else
            viewHolder.textMapName.setBackgroundColor(Color.BLACK);
        return convertView;
    }

    /**
     * Holds the view for the MapSelectionAdapter
     */
    class ViewHolder{
        TextView  textMapName;
    }

}
