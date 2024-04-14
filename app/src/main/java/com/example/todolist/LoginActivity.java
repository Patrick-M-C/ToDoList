package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    // Hardcoded username and password for demonstration purposes
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialize views
        editTextUsername = findViewById(R.id.editText_username);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(); // Call the login method when the button is clicked
            }
        });
    }

    // Method to handle login logic
    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if username and password match the hardcoded values
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            // Login successful, navigate to the LoginSuccessActivity
            Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
            startActivity(intent); // Start the LoginSuccessActivity
            finish(); // Finish the login activity to prevent going back to it
        } else {
            // Invalid credentials, display a toast message
            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
