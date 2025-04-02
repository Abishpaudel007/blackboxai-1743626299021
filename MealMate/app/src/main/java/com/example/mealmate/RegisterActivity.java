package com.example.mealmate;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private AppDatabase database;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        database = AppDatabase.getDatabase(this);

        findViewById(R.id.btnRegister).setOnClickListener(v -> attemptRegistration());
    }

    private void attemptRegistration() {
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
            if (database.userDao().checkEmailExists(email) > 0) {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show());
                return;
            }

            // In production, use proper password hashing (e.g., BCrypt)
            User newUser = new User(email, password);
            database.userDao().insert(newUser);

            runOnUiThread(() -> {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish(); // Return to login
            });
        });
    }

    public void onLoginClicked(View view) {
        finish(); // Close registration and return to login
    }
}