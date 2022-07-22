package com.finalprm.fuze.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprm.fuze.Card.Card;
import com.finalprm.fuze.Card.CardAdapter;
import com.finalprm.fuze.Matches.MatchesActivity;
import com.finalprm.fuze.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageButton likebtn, dislikebtn, profile, match;
    private Card cards_data[];
    private CardAdapter cardAdapter;
    private int i;
    private FirebaseAuth mAuth;
    private ProgressBar spinner;
    private String currentUserId, notification, sendMessageText;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private List<Card> rowItems;
    private TextView tv;
    private SwipeFlingAdapterView flingContainer;
    private FrameLayout cardFrame, moreFrame;
    private SlideLayout slideLayout;

    //Process
    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        profile = findViewById(R.id.profile);
        spinner = (ProgressBar)findViewById(R.id.pBar);
        match = findViewById(R.id.match);
        likebtn = findViewById(R.id.likebtn);
        dislikebtn = findViewById(R.id.dislikebtn);
        tv = (TextView)findViewById(R.id.noCardsBanner);
//        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        cardFrame = findViewById(R.id.card_frame);
        moreFrame = findViewById(R.id.more_frame);
        slideLayout = findViewById(R.id.pulsator);
    }

    private void bindingAction() {
        profile.setOnClickListener(this::onProfileClick);
        match.setOnClickListener(this::onMatchClick);

    }

    private void onMatchClick(View view) {
//        Toast.makeText(this, "Tap on match to change", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MatchesActivity.class);
        this.startActivity(intent);
    }

    private void onProfileClick(View view) {
//        Toast.makeText(this, "Tap on profile to change", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettingScreen.class);
        this.startActivity(intent);
    }

    public void DislikeBtn(View v) {
        if (rowItems.size() != 0) {
            Card card_item = rowItems.get(0);
            String userId = card_item.getUserId();
            databaseReference.child(userId).child("connections").child("nope").child(currentUserId).setValue(true);
//            Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();

            rowItems.remove(0);
            cardAdapter.notifyDataSetChanged();

            //Display a banner when no cards are available to display
            TextView tv = (TextView)findViewById(R.id.noCardsBanner);
            if(rowItems.size() == 0) {
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.INVISIBLE);
            }

            Intent btnClick = new Intent(MainActivity.this, BtnDislikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);

        }
    }

    public void LikeBtn(View v) {
        if (rowItems.size() != 0) {
            Card card_item = rowItems.get(0);
            String userId = card_item.getUserId();
            //check matches
            databaseReference.child(userId).child("connections").child("yeps").child(currentUserId).setValue(true);
            isConnectionMatch(userId);
//            Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();

            rowItems.remove(0);
            cardAdapter.notifyDataSetChanged();


            //Display a banner when no cards are available to display
            TextView tv = (TextView)findViewById(R.id.noCardsBanner);
            if(rowItems.size() == 0) {
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.INVISIBLE);
            }

            Intent btnClick = new Intent(MainActivity.this, BtnLikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        bindingView();
        bindingAction();
        spinner.setVisibility(View.GONE);
        slideLayout.start();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fuze-c6271-default-rtdb.asia-southeast1.firebasedatabase.app");
        databaseReference = database.getReference().child("Users");
        currentUserId = "XX";
        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null && mAuth.getCurrentUser() != null)
            currentUserId = mAuth.getCurrentUser().getUid();
        else{
            Toast.makeText(getApplicationContext(), "Auth failed", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        checkUserSex();
        rowItems = new ArrayList<Card>();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String id, name, bio, userAge, userGender, userFavorite, profileImageUrl;
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    id = dataSnapshot.getKey().toString();
                    if (map.get("name") != null) {
                        name = map.get("name").toString();
                    } else
                        name = "";
                    if (map.get("bio") != null) {
                        bio = map.get("bio").toString();
                    } else
                        bio = "";
                    if (map.get("age") != null) {
                        userAge = map.get("age").toString();
                    } else
                        userAge = "";
                    if (map.get("gender") != null) {
                        userGender = map.get("gender").toString();
                    } else
                        userGender = "";
                    if (map.get("favorite") != null) {
                        userFavorite = map.get("favorite").toString();
                    } else
                        userFavorite = "";
                    if (map.get("profileImageUrl") != null) {
                        profileImageUrl = map.get("profileImageUrl").toString();
                    } else
                        profileImageUrl = "default";
                    Card card = new Card(id, name, userAge, bio, profileImageUrl, userGender, userFavorite);
                    if(!currentUserId.equals(card.getUserId()))
                    rowItems.add(card);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardAdapter = new CardAdapter(this, R.layout.item, rowItems );
//        checkRowItem();

        final SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(cardAdapter);

//        Display a banner when no cards are available to display
//        TextView tv = (TextView)findViewById(R.id.noCardsBanner);
//        if(rowItems.size() == 0) {
//            tv.setVisibility(View.VISIBLE);
//        } else {
//            tv.setVisibility(View.INVISIBLE);
//        }


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                rowItems.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                Card obj = (Card) dataObject;
                String userId = obj.getUserId();
                databaseReference.child(userId).child("connections").child("nope").child(currentUserId).setValue(true);
//                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();

                //Display a banner when no cards are available to display
                TextView tv = (TextView)findViewById(R.id.noCardsBanner);
                if(rowItems.size() == 0) {
                    tv.setVisibility(View.VISIBLE);
                } else {
                    tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String userId = obj.getUserId();
                databaseReference.child(userId).child("connections").child("yeps").child(currentUserId).setValue(true);
                isConnectionMatch(userId);
//                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();

                //Display a banner when no cards are available to display
                TextView tv = (TextView)findViewById(R.id.noCardsBanner);
                if(rowItems.size() == 0) {
                    tv.setVisibility(View.VISIBLE);
                } else {
                    tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void isConnectionMatch(final String userId) {
        DatabaseReference currentUserConnectionsDb = databaseReference.child(currentUserId).child("connections").child("yes").child(userId);
        databaseReference.child(currentUserId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    sendMessageText = dataSnapshot.getValue().toString();
                else
                    sendMessageText = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(!currentUserId.equals(userId)) {
            currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                        Toast.makeText(MainActivity.this, "" +
//                                "New Match", Toast.LENGTH_LONG).show();
                        String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
                        Map mapLastTimeStamp = new HashMap<>();
                        long now  = System.currentTimeMillis();
                        String timeStamp = Long.toString(now);
                        mapLastTimeStamp.put("lastTimeStamp", timeStamp);

                        databaseReference.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUserId).child("ChatId").setValue(key);
                        databaseReference.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUserId).updateChildren(mapLastTimeStamp);

                        databaseReference.child(currentUserId).child("connections").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);
                        databaseReference.child(currentUserId).child("connections").child("matches").child(dataSnapshot.getKey()).updateChildren(mapLastTimeStamp);

                        notification = " ";

                        DatabaseReference notificationID = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("notificationKey");
                        notificationID.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


    private String userGender;
    private String oppositeUserGender;
    public void checkUserSex(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = databaseReference.child(user.getUid());

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("gender").getValue() != null){
                        userGender = dataSnapshot.child("gender").getValue().toString();
                        oppositeUserGender = userGender;
                        getOppositeSexUsers(oppositeUserGender);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getOppositeSexUsers(final String oppositeUserGender){

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && !dataSnapshot.getKey().equals(currentUserId)) {
                    if (dataSnapshot.child("gender").exists() && !dataSnapshot.child("connections").child("nope").hasChild(currentUserId) && !dataSnapshot.child("connections").child("yeps").hasChild(currentUserId) && !dataSnapshot.child("gender").getValue().toString().equals(oppositeUserGender)) {
                        String profileImageUrl = "default";
                        if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }else if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        Card item = new Card(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("age").getValue().toString(), dataSnapshot.child("bio").getValue().toString(),  profileImageUrl, dataSnapshot.child("gender").getValue().toString(), dataSnapshot.child("favorite").getValue().toString());
                        rowItems.add(item);
                        cardAdapter.notifyDataSetChanged();
                    }
                }
//                if(rowItems.size() == 0) {
//                    tv.setVisibility(View.VISIBLE);
//                } else {
//                    tv.setVisibility(View.INVISIBLE);
//                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void checkRowItem() {
        if (rowItems.isEmpty()) {
            moreFrame.setVisibility(View.VISIBLE);
            cardFrame.setVisibility(View.GONE);
        }
    }

}
