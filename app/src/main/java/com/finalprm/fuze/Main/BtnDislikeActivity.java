package com.finalprm.fuze.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.finalprm.fuze.R;
import com.squareup.picasso.Picasso;


public class BtnDislikeActivity extends AppCompatActivity {
    private static final String TAG = "BtnDislikeActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = BtnDislikeActivity.this;
    private ImageView dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_dislike);


        dislike = findViewById(R.id.profileImage);

        Intent intent = getIntent();
        String profileUrl = intent.getStringExtra("url");

        switch (profileUrl) {
            case "default":
                Glide.with(mContext).load(R.drawable.default_man).into(dislike);
                break;
            default:
                Glide.with(mContext).load(profileUrl).into(dislike);
                Picasso.with(mContext).load(profileUrl).placeholder(R.drawable.loading_image).error(R.drawable.profile_image).into(dislike);
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent mainIntent = new Intent(BtnDislikeActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        }).start();
    }

}
