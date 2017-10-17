package com.app_team11.conquest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app_team11.conquest.R;

import java.io.File;
import java.util.List;

/**
 * Created by Jaydeep9101 on 17-Oct-17.
 */

public class MapSelectionAdapter extends BaseAdapter {

    private File[] mapFileList;
    private Context context;
    private LayoutInflater inflater;

    public MapSelectionAdapter(File[] mapFileList, Context context) {
        this.mapFileList = mapFileList;
        this.context = context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mapFileList.length;
    }

    @Override
    public File getItem(int position) {
        return mapFileList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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
        viewHolder.textMapName.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHolder{
        TextView  textMapName;
    }

}
