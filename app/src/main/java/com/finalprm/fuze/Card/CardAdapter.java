package com.finalprm.fuze.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.finalprm.fuze.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CardAdapter extends ArrayAdapter<Card>{

    Context context;

    public CardAdapter(Context context, int resourceId, List<Card> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Card card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageView mNeedImage = (ImageView) convertView.findViewById(R.id.favorite);
        ImageView mGiveImage = (ImageView) convertView.findViewById(R.id.gender);

        name.setText(card_item.getName());
            if(card_item.getGender().equals("Male"))
                mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_male));
            else if(card_item.getGender().equals("Female"))
                mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_female));
            else
                mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_gay));

//            give image
            if(card_item.getFavorite().equals("Funny"))
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.funny));
            else if(card_item.getFavorite().equals("Foodie"))
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.foodie));
            else if(card_item.getFavorite().equals("Dance"))
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dance));
            else if(card_item.getFavorite().equals("Netflix"))
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.netflix));
            else if(card_item.getFavorite().equals("Youtube"))
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.youtube_tv));
            else
                mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.none));


                Picasso.with(convertView.getContext()).load(card_item.getProfileImageUrl()).placeholder(R.drawable.loading_image).error(R.drawable.profile_image).into(image);

        return convertView;

    }
}
