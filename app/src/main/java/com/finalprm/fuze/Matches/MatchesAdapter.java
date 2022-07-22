package com.finalprm.fuze.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.finalprm.fuze.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchesAdapter
        extends RecyclerView.Adapter<MatchesViewHolders>
{
    private List<MatchesObject> matchesList;
    private Context context;


    public MatchesAdapter(List<MatchesObject> matchesList, Context context){
        this.matchesList = matchesList;
        this.context = context;
    }

    @Override
    public MatchesViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolders rcv = new MatchesViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesViewHolders holder, int position) {
        holder.mMatchId.setText(matchesList.get(position).getUserId());
        holder.mFavorite.setText(matchesList.get(position).getFavorite());
        holder.mProfile.setText(matchesList.get(position).getProfileImageUrl());
        holder.mGender.setText(matchesList.get(position).getGender());
        holder.mMatchName.setText(matchesList.get(position).getName());
        holder.mLastMessage.setText(matchesList.get(position).getLastMessage());
        String lastSeen = "";
        lastSeen  = matchesList.get(position).getLastSeen();

        if(lastSeen.equals("true"))
            holder.mNotificationDot.setVisibility(View.VISIBLE);
        else
            holder.mNotificationDot.setVisibility(View.INVISIBLE);
        holder.mLastTimeStamp.setText(matchesList.get(position).getLastTimestamp());
        if(!matchesList.get(position).getProfileImageUrl().equals("default")){
            Picasso.with(context).load(matchesList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }
    }

    @Override
    public int getItemCount() {
        return this.matchesList.size();
    }
}
