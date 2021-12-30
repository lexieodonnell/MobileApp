package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        //onClick listener for the delete button
        Button deleteItemButton = findViewById(R.id.deleteButton);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText itemIDDelete = findViewById(R.id.deleteID);
                int id = Integer.parseInt(itemIDDelete.getText().toString());

                //get the inventory database singleton
                InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

                db.deleteItem(id);

                startActivity(new Intent(DeleteItem.this, InventoryDataPage.class));
            }
        });
    }
}