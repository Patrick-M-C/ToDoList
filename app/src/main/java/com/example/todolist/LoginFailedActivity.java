package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginFailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_failed);

        // Delay for 3 seconds before navigating back to LoginActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLoginActivity();
            }
        }, 3000);
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(LoginFailedActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish this activity to prevent going back to it with back button
    }
}
