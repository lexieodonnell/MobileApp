package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //take data from the EditText fields
        EditText userNameField = (EditText)findViewById(R.id.userNameField);
        EditText passwordField = (EditText)findViewById(R.id.passwordField);

        //pull in the buttons
        Button loginButton = findViewById(R.id.loginButton);
        Button newProfile = findViewById(R.id.createUserButton);

        //listen for click of the "Login" button
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //pull text entered in login fields
                String enteredUserName = userNameField.getText().toString();
                String enteredPassword = passwordField.getText().toString();

                //get the inventory database singleton
                InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

                if(db.checkPassword(enteredUserName, enteredPassword)) {
                    startActivity(new Intent(Login.this, InventoryDataPage.class));
                } else {
                    TextView loginDisplayText = findViewById(R.id.errorMessage);
                    loginDisplayText.setText("The password or username is incorrect.");
                }

            }
        });

        newProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pull text entered in login fields
                String enteredUserName = userNameField.getText().toString();
                String enteredPassword = passwordField.getText().toString();

                //get the inventory database singleton
                InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

                boolean userAdded = db.addUsers(enteredUserName, enteredPassword);

                if(userAdded) {
                    startActivity(new Intent(Login.this, InventoryDataPage.class));
                } else {
                    TextView loginDisplayText = findViewById(R.id.errorMessage);
                    loginDisplayText.setText("This username already exists.");
                }
            }

        });

    }
}