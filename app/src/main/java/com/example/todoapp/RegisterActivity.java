package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.Data.UserDataBase;
import com.example.todoapp.model.User;
import com.example.todoapp.Data.UserDao;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    Button buttonRegister;

    TextView textViewLogin;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordConf = editTextCnfPassword.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    editTextUsername.setError("Username is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required.");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Please enter a valid email address.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required.");
                    return;
                }
                if (TextUtils.isEmpty(passwordConf)) {
                    editTextCnfPassword.setError("Re-type Password.");
                    return;
                }
                if (password.length()<6){
                    editTextPassword.setError("Password must be more than 6 characters");
                    return;
                }
                if (!password.equals(passwordConf)){
                    editTextCnfPassword.setError("Both password doesn't match");
                    return;
                }

                User us = userDao.getUser(email);
                if (us != null) {
                    editTextEmail.setError("Email already Registered");
                }else {
                    User user = new User(userName,password,email);
                    userDao.insert(user);
                    //Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    //startActivity(moveToLogin);
                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    editTextCnfPassword.setText("");
                }


                if (!password.equals(passwordConf)) {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
