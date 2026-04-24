package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user =
                auth.getCurrentUser();

        if (user != null) {

            // Usuario ya logueado

            startActivity(
                    new Intent(
                            MainActivity.this,
                            SearchActivity.class
                    )
            );

        } else {

            // Usuario no logueado

            startActivity(
                    new Intent(
                            MainActivity.this,
                            LoginActivity.class
                    )
            );

        }

        finish();
    }
}