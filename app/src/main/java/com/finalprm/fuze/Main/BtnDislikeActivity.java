package com.finalprm.fuze.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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


        dislike = findViewById(R.id.dislike);

        Intent intent = getIntent();
        String profileUrl = intent.getStringExtra("url");

        Picasso.with(mContext).load(profileUrl).placeholder(R.drawable.loading_image).error(R.drawable.profile_image).into(dislike);

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
