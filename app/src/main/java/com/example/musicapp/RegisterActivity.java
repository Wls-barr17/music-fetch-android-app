package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private RadioGroup radioGroupGender;

    private CheckBox checkStudent;

    private Spinner spinnerCountry;

    private Button buttonRegister;
    private TextView textLogin;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkStudent = findViewById(R.id.checkStudent);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        String[] countries = {
                "Colombia",
                "Mexico",
                "Argentina",
                "Peru",
                "Chile"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        countries
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spinnerCountry.setAdapter(adapter);

        // Referencias UI
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonRegister = findViewById(R.id.Btn_register);
        textLogin = findViewById(R.id.Login);

        // Evento botón registrar
        buttonRegister.setOnClickListener(v -> registerUser());

        // Volver al Login
        textLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);
            finish();

        });
    }

    private void registerUser() {

        String name =
                editTextName
                        .getText()
                        .toString()
                        .trim();

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

        // Validación nombre

        if (TextUtils.isEmpty(name)) {

            editTextName.setError(
                    "Ingrese el nombre"
            );

            editTextName.requestFocus();
            return;
        }

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

        // Validación genero

        int selectedId =
                radioGroupGender
                        .getCheckedRadioButtonId();

        if (selectedId == -1) {

            Toast.makeText(
                    this,
                    "Seleccione un género",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        RadioButton radioButton =
                findViewById(selectedId);

        String genero =
                radioButton
                        .getText()
                        .toString();

        // Checkbox

        boolean estudiante =
                checkStudent.isChecked();

        // Spinner

        String pais =
                spinnerCountry
                        .getSelectedItem()
                        .toString();

        // Crear usuario en Firebase

        auth.createUserWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        String uid =
                                auth
                                        .getCurrentUser()
                                        .getUid();

                        Map<String, Object> user =
                                new HashMap<>();

                        user.put("Nombre", name);

                        user.put("Correo", email);

                        user.put("Genero", genero);

                        user.put("Estudiante", estudiante);

                        user.put("Pais", pais);

                        user.put("Fecha_registro", new Date());

                        db.collection("Usuarios")
                                .document(uid)
                                .set(user)

                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(
                                            RegisterActivity.this,
                                            "Usuario registrado",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    startActivity(
                                            new Intent(
                                                    RegisterActivity.this,
                                                    LoginActivity.class
                                            )
                                    );

                                    finish();

                                })

                                .addOnFailureListener(e -> {

                                    Toast.makeText(
                                            RegisterActivity.this,
                                            "Error guardando usuario",
                                            Toast.LENGTH_LONG
                                    ).show();

                                });

                    } else {

                        String error =
                                task.getException()
                                        .getMessage();

                        Toast.makeText(
                                RegisterActivity.this,
                                error,
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

}
