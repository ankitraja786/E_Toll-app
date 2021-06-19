package com.example.nitish235.newsignin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.URI;

public class Main2Activity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    Button trip;
    TextView nameTV;
    Button plan;
    TextView emailTV;
    TextView idTV;
    //ImageView photoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        plan = findViewById(R.id.trippay);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //place the button click code here.
                startActivity(new Intent(Main2Activity.this,MapsActivity.class));
            }
        });
        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        idTV = findViewById(R.id.id);
        trip=(Button)findViewById(R.id.trippay);
        //photoIV = findViewById(R.id.photo);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Main2Activity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            nameTV.setText("Name: " + personName);
            emailTV.setText("Email: " + personEmail);
            idTV.setText("ID: " + personId);
            //Glide.with(this).load(personPhoto).into(photoIV);
        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent newmapintent=new Intent(Main2Activity.this,MapsActivity.class);
                startActivity(newmapintent);

           }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Main2Activity.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
