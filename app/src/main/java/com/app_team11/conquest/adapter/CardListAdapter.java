package com.app_team11.conquest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.model.Cards;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Binds the card list view with its content
 * Created by Vasu on 01-11-2017.
 */

public class CardListAdapter extends BaseAdapter{

    private List<Cards> cardsList;
    private LayoutInflater inflater;

    /**
     * Set the list of cards
     * @param cardsList parameter defining the list of cards
     */
    public void setCardsList(List<Cards> cardsList) {
        this.cardsList = cardsList;
    }

    /**
     * Constructor initialization
     * @param context for context initialization
     * @param cardsList for defining list of cards
     */
    public CardListAdapter(Context context, List<Cards> cardsList){
        this.cardsList=cardsList;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the size of card list
     * @return cardsList card list size
     */
    @Override
    public int getCount() {
        return cardsList.size();
    }

    /**
     * Returns the position of card list
     * @param position defines the position of card
     * @return cardsList card position returns the position of card
     */
    @Override
    public Cards getItem(int position) {
        return cardsList.get(position);
    }

    /**
     * Returns the Id of item
     * @param position of the card
     * @return position returns the integer
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get current view
     * @param position position of view
     * @param convertView view ot be converted
     * @param parent ViewGroup parent
     * @return convertView the converted view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_cards,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textCardTerritoryName = (TextView) convertView.findViewById(R.id.text_territory_name);
            viewHolder.textCardMilitary = (TextView) convertView.findViewById(R.id.text_military_type);
            viewHolder.linearCard = (LinearLayout) convertView.findViewById(R.id.linear_card);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textCardTerritoryName.setText(getItem(position).getCardTerritory().getTerritoryName());
        viewHolder.textCardMilitary.setText(getItem(position).getArmyType());
        if(getItem(position).isSelected()){
            viewHolder.linearCard.setBackgroundColor(Color.YELLOW);
        }
        else
            viewHolder.linearCard.setBackgroundColor(Color.BLACK);
        return convertView;
    }

    /**
     * Holds the view
     */
    class ViewHolder{
        private TextView textCardTerritoryName;
        private TextView textCardMilitary;
        private LinearLayout linearCard;
    }

}

