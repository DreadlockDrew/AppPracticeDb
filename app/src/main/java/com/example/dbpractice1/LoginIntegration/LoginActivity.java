package com.example.dbpractice1.LoginIntegration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dbpractice1.R;

public class LoginActivity extends AppCompatActivity {

    private LoginDatabase loginDatabase;

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginDatabase = new LoginDatabase(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Please enter both username and password.");
            return;
        }

        if (loginDatabase.doesUserExist(username)) {
            String storedPassword = loginDatabase.getStoredPassword(username);
            if (storedPassword.equals(password)) {
                showToast("Login successful!");
                // Proceed to next activity
            } else {
                showToast("Incorrect password.");
            }
        } else {
            showToast("Username not found. Creating a new entry...");
            loginDatabase.insertUser(username,password);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}