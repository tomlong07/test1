package com.finalprm.fuze.Main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprm.fuze.Card.Card;
import com.finalprm.fuze.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class LoadingScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        tv = findViewById(R.id.text_name);

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://fuze-c6271-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference();

        myRef.child("Users").addChildEventListener(new ChildEventListener() {
            List<Card> cards;
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
                        profileImageUrl = "";
                    Card card = new Card(id, name, userAge, bio, profileImageUrl, userGender, userFavorite);
//                    cards.add(card);
                    tv.setText(card.toString() + "\n");
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

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(LoadingScreen.this, FirstScreen.class);
                startActivity(i);

                finish();
            }
        }, 500);
    }

}

