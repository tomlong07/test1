package com.finalprm.fuze.Main;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finalprm.fuze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoadingScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        bindingView();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");
            myRef.setValue("Hello, World!");

            tv.setText("da chay");

//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                Intent i = new Intent(LoadingScreen.this, FirstScreen.class);
//                startActivity(i);
//
//                finish();
//            }
//        }, 2000);
    }

    private void bindingView() {
        tv=findViewById(R.id.text_name);
    }

}

