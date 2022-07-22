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
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Card card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageView gender = (ImageView) convertView.findViewById(R.id.gender);
        ImageView favorite = (ImageView) convertView.findViewById(R.id.favorite);
        TextView bio = (TextView) convertView.findViewById(R.id.bio_item);


        name.setText(card_item.getName() + " | " + card_item.getAge());
            if(card_item.getGender().equals("Male"))
                gender.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_male));
            else if(card_item.getGender().equals("Female"))
                gender.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_female));
            else
                gender.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_gay));

            if(card_item.getFavorite().equals("Funny"))
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.funny));
            else if(card_item.getFavorite().equals("Foodie"))
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.foodie));
            else if(card_item.getFavorite().equals("Dance"))
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dance));
            else if(card_item.getFavorite().equals("Netflix"))
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.netflix));
            else if(card_item.getFavorite().equals("Youtube"))
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.youtube_tv));
            else
                favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.none));


                Picasso.with(convertView.getContext()).load(card_item.getProfileImageUrl()).placeholder(R.drawable.loading_image).error(R.drawable.profile_image).into(image);

        return convertView;

    }
}
