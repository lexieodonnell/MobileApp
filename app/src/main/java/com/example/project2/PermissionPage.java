package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PermissionPage extends AppCompatActivity {

    private final int REQUEST_SMS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_page);

        Button getPermission = findViewById(R.id.accept);
        Button goBack = findViewById(R.id.goBackButton);



        getPermission.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                    String SMSPermissions = Manifest.permission.SEND_SMS;
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), SMSPermissions)
                            != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PermissionPage.this,
                                    new String[] { SMSPermissions }, REQUEST_SMS);
                    }
                }
        });

        goBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(PermissionPage.this, InventoryDataPage.class));
            }
        });
    }
}