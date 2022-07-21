package com.finalprm.fuze.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finalprm.fuze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstScreen extends AppCompatActivity
{
    private Button mLogin, mRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

    }

    private void bindingAction() {
        mLogin.setOnClickListener(this::onLoginClick);
        mRegister.setOnClickListener(this::onRegisterClick);

    }

    private void onRegisterClick(View view) {
        Intent intent = new Intent(FirstScreen.this, Register.class);
        startActivity(intent);
        finish();
        return;
    }

    private void onLoginClick(View view) {
        Intent intent = new Intent(FirstScreen.this, Login.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        bindingView();
        bindingAction();

        if(mAuth != null){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user !=null && user.isEmailVerified()){
                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            else {
                System.out.println("User is null");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }

}
