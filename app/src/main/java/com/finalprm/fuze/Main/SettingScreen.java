package com.finalprm.fuze.Main;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.finalprm.fuze.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingScreen extends AppCompatActivity {

    private EditText edtName, edtPhone, edtBio;
    private ProgressBar spinner;
    private Button enter;
    private ImageButton back;
    private ImageView profileImage;
    private Toolbar toolbar;
    private SeekBar age;
    private Spinner gender, favorite;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private TextView textAge;

    private String userId, name, phone, profileImageUrl, userAge, bio, userGender, userFavorite;
    private int genderIndex, favoriteIndex;
    private Uri resultUri;


    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        spinner = (ProgressBar)findViewById(R.id.pBar);
        edtName = findViewById(R.id.name);
        edtPhone = findViewById(R.id.phone);
        edtBio = findViewById(R.id.edtBio);
        textAge = findViewById(R.id.age);
        age = (SeekBar) findViewById(R.id.age_range);
        gender = (Spinner) findViewById(R.id.gender_spinner);
        favorite = (Spinner) findViewById(R.id.favorite_spinner);
        enter = findViewById(R.id.confirm);
        back = findViewById(R.id.settingsBack);
        profileImage = findViewById(R.id.profileImage);
        toolbar = findViewById(R.id.settingsToolbar);
        //gender
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);
        //favorite
        ArrayAdapter<CharSequence> favoriteAdapter = ArrayAdapter.createFromResource(this,
                R.array.favorite, android.R.layout.simple_spinner_item);
        favoriteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favorite.setAdapter(favoriteAdapter);
        spinner.setVisibility(View.GONE);


    }

    private void bindingAction() {
        enter.setOnClickListener(this::onEnterClick);
        back.setOnClickListener(this::onBackClick);
        profileImage.setOnClickListener(this::onImageClick);
    }

    private void onImageClick(View view) {
        if (!checkPermission()) {
            Toast.makeText(getApplicationContext(), "Please allow access to continue!", Toast.LENGTH_SHORT).show();
            requestPermission();
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }
    }

    private void onBackClick(View view) {
        spinner.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SettingScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
        spinner.setVisibility(View.GONE);
        return;
    }

    private void onEnterClick(View view) {
        saveUserInformation();
        Toast.makeText(getApplicationContext(), "Enter checked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SettingScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bindingView();
        bindingAction();
        spinner.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAge.setText(progress + " years-old");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null && mAuth.getCurrentUser()!= null) {
            userId = mAuth.getCurrentUser().getUid();
        }
        else {
            finish();
        }
        databaseReference = FirebaseDatabase.getInstance("https://fuze-c6271-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(userId);
        getUserInfomation();

    }

    //shows options for menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ContactUs:
                Toast.makeText(this,"Contact click",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(SettingScreen.this)
                    .setTitle("Contact Us")
                    .setMessage("Contact us: 2soul.com")
                    .setNegativeButton("Dismiss", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
                    break;
            case R.id.logout:
                spinner.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Logout user",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(SettingScreen.this, FirstScreen.class);
                startActivity(intent);
                finish();
                spinner.setVisibility(View.GONE);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(getApplicationContext(), "Please allow access to continue!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserInfomation() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        name = map.get("name").toString();
                        edtName.setText(name);
                    }
                    if(map.get("phone")!=null){
                        phone = map.get("phone").toString();
                        edtPhone.setText(phone);
                    }
                    if(map.get("bio")!=null){
                        bio = map.get("bio").toString();
                        edtBio.setText(bio);
                    }
                    else
                        userFavorite = "";
                    if(map.get("age")!=null){
                        userAge = map.get("age").toString();
                        age.setProgress(Integer.parseInt(userAge));
                    }
                    if(map.get("gender") != null){
                        userGender = map.get("gender").toString();
                    }
                    else
                        userGender = "";
                    if(map.get("favorite") != null){
                        userFavorite = map.get("favorite").toString();
                    }
                    else
                        userFavorite = "";

                    String[] genderArrays = getResources().getStringArray(R.array.gender);
                    genderIndex = 0;
                    String[] favoriteArrays = getResources().getStringArray(R.array.favorite);
                    favoriteIndex = 0;

                    for(int i = 0; i< genderArrays.length; i++){
                        if(userGender.equals(genderArrays[i]))
                            genderIndex = i;
                    }
                    for(int i = 0; i< favoriteArrays.length; i++){
                        if(userFavorite.equals(favoriteArrays[i]))
                            favoriteIndex = i;
                    }
                    gender.setSelection(genderIndex);
                    favorite.setSelection(favoriteIndex);

                    if(map.get("profileImageUrl")!=null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        Picasso.with(SettingScreen.this).load(profileImageUrl).placeholder(R.drawable.loading_image).error(R.drawable.profile_image).into(profileImage);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveUserInformation() {
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        bio = edtBio.getText().toString();
        userAge = age.getProgress() + "";
        userGender = gender.getSelectedItem().toString();
        userFavorite = favorite.getSelectedItem().toString();


        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("bio",bio);
        userInfo.put("age",userAge);
        userInfo.put("gender", userGender);
        userInfo.put("favorite", userFavorite);
        databaseReference.updateChildren(userInfo);
        if(resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uri.isComplete());
                    Uri downloadUrl = uri.getResult();
                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl.toString());
                    databaseReference.updateChildren(userInfo);
                    finish();
                    return;
                }
            });

        }else{
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            profileImage.setImageURI(resultUri);
        }
    }

}
