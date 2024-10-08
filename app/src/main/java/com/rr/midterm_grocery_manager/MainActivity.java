package com.rr.midterm_grocery_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;

import com.rr.midterm_grocery_manager.ViewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getUser().observe(this, firebaseUser -> {
                if(firebaseUser != null){
            Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
            startActivity(intent);
            finish();;
            }
        } );

        userViewModel.getLoginFailed().observe(this, loginFailed ->{
            if(loginFailed){
                Toast.makeText(this,"Login failed, Check username and password", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(v ->{
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please, insert all information", Toast.LENGTH_SHORT).show();
                return;
            }
            userViewModel.loginUser(email, password);
        });

        signupButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please, insert all information", Toast.LENGTH_SHORT).show();
                return;
            }
            userViewModel.registerUser(email,password, this);


        });


    }
}