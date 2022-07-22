package com.finalprm.fuze.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprm.fuze.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button mLogin;
    private EditText mEmail, mPassword;
    private TextView mForgotPassword;
    private boolean loginBtnClicked;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        mLogin = (Button) findViewById(R.id.login);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mForgotPassword = (TextView) findViewById(R.id.forgotPasswordButton);
    }

    private void bindingAction() {
        mLogin.setOnClickListener(this::onLoginClick);
        mForgotPassword.setOnClickListener(this::onForgotPassClick);
    }

    private void onForgotPassClick(View view) {
        Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    private void onLoginClick(View view) {
        loginBtnClicked = true;
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        if (isStringNull(email) || isStringNull(password)) {
            Toast.makeText(Login.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
//                  Verify account
//                               if(mAuth.getCurrentUser().isEmailVerified()) {
//                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    return;
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
//                                }
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            });

        }
    }

    private boolean isStringNull(String string) {
        System.out.println("isStringNull: checking string if null.");
        return string.isEmpty();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user !=null && user.isEmailVerified() && !loginBtnClicked){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }

    @Override
    public void onBackPressed() {
        Intent btnClick = new Intent(Login.this, FirstScreen.class);
        startActivity(btnClick);
        finish();
        return;
    }

}
