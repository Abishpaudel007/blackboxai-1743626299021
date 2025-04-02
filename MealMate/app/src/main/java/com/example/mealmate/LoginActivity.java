package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private AppDatabase database;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        database = AppDatabase.getDatabase(this);

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            User user = database.userDao().getUserByEmail(email);
            runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                } else if (!user.passwordHash.equals(password)) { // In production, use BCrypt
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            });
        });
    }

    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}