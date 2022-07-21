package com.finalprm.fuze.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private Button mRegister;
    private ProgressBar spinner;
    private EditText mEmail, mPassword, mName, mPhone;
    private CheckBox checkbox;
    private TextView textView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mName = (EditText) findViewById(R.id.name);
        mPhone = (EditText) findViewById(R.id.phone);
        mRegister = (Button) findViewById(R.id.register);
        textView = (TextView)findViewById(R.id.textView2);
        checkbox = (CheckBox)findViewById(R.id.checkBox1);
        //optional spinner

        spinner = (ProgressBar)findViewById(R.id.pBar);
        spinner.setVisibility(View.GONE);

        checkbox.setText("");
        textView.setText(Html.fromHtml("I have read and agree to the " +
                "<a href='https://www.websitepolicies.com/policies/view/nizu0aVh'>Terms & Conditions</a>"));
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void bindingAction() {
        mRegister.setOnClickListener(this::onRegisterClick);
    }

    private void onRegisterClick(View view) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String name = mName.getText().toString();
        Boolean tnc = checkbox.isChecked();
        String phone = mPhone.getText().toString();

        if (checkInputs(email, name, password, tnc, phone)) {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Registration successfully. Please check your email for verification. ", Toast.LENGTH_LONG).show();
                                    String userId = mAuth.getCurrentUser().getUid();
                                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

                                    Log.d("DB_debug", FirebaseDatabase.getInstance().getReference().getDatabase() + "");
                                    Map userInfo = new HashMap<>();
                                    userInfo.put("name", name);
                                    userInfo.put("profileImageUrl", "default");
                                    userInfo.put("phone", phone);
                                    currentUserDb.updateChildren(userInfo);

                                    //clear the fields
                                    mEmail.setText("");
                                    mName.setText("");
                                    mPassword.setText("");
                                    mPhone.setText("");
                                    Intent btnClick = new Intent(Register.this, FirstScreen.class);
                                    startActivity(btnClick);
                                    finish();
                                    return;

                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bindingView();
        bindingAction();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                spinner.setVisibility(View.VISIBLE);
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && user.isEmailVerified()){
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    spinner.setVisibility(View.GONE);
                    return;
                }
                spinner.setVisibility(View.GONE);

            }
        };
    }

    private boolean checkInputs(String email, String username, String password, Boolean tnc, String phone) {
        if (email.equals("") || username.equals("") || password.equals("") || phone.equals("")) {
            Toast.makeText(Register.this, "All fields must be filed out.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Checks if the email id is valid or not.
        if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Invalid email address, enter valid email id and click on Continue", Toast.LENGTH_SHORT).show();
            return false;

        }

        if(!tnc) {
            Toast.makeText(getApplicationContext(), "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Pattern.matches("[a-zA-Z] + ", phone)) {
            return phone.length() > 9 && phone.length() <=13;
        }

        return true;
    }
}
