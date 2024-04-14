package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        // Delay for 3 seconds before navigating to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMainActivity();
            }
        }, 1000);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginSuccessActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish this activity to prevent going back to it with back button
    }
}
