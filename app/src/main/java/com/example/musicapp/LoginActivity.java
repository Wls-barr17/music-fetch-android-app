package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonLogin;
    private TextView textRegister;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();

        // Referencias UI
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);

        buttonLogin = findViewById(R.id.Btn_login);
        textRegister = findViewById(R.id.Register);

        // Evento botón login
        buttonLogin.setOnClickListener(v -> loginUser());

        // Ir a Register
        textRegister.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            RegisterActivity.class
                    );

            startActivity(intent);

        });

        // Auto login si ya está autenticado
        FirebaseUser currentUser =
                auth.getCurrentUser();

        if (currentUser != null) {

            startActivity(
                    new Intent(
                            LoginActivity.this,
                            FavoritesActivity.class
                    )
            );

            finish();

        }
    }

    private void loginUser() {

        String email =
                editTextEmail
                        .getText()
                        .toString()
                        .trim();

        String password =
                editTextPassword
                        .getText()
                        .toString()
                        .trim();

        // Validación email

        if (TextUtils.isEmpty(email)) {

            editTextEmail.setError(
                    "Ingrese el correo"
            );

            editTextEmail.requestFocus();

            return;

        }

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            editTextEmail.setError(
                    "Correo inválido"
            );

            editTextEmail.requestFocus();

            return;

        }

        // Validación password

        if (TextUtils.isEmpty(password)) {

            editTextPassword.setError(
                    "Ingrese la contraseña"
            );

            editTextPassword.requestFocus();

            return;

        }

        if (password.length() < 6) {

            editTextPassword.setError(
                    "Mínimo 6 caracteres"
            );

            editTextPassword.requestFocus();

            return;

        }

        // Login Firebase

        auth.signInWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(
                                LoginActivity.this,
                                "Login exitoso",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent =
                                new Intent(
                                        LoginActivity.this,
                                        FavoritesActivity.class
                                );

                        startActivity(intent);

                        finish();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                "Correo o contraseña incorrectos",
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }
}