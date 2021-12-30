package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button addNewItem = findViewById(R.id.addItemButton);

        addNewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //take data from EditText fields
                EditText itemNameToAdd = findViewById(R.id.newItemName);
                EditText itemIDToAdd = findViewById(R.id.newItemID);
                EditText itemQtyToAdd = findViewById(R.id.newItemQty);

                //store in variables
                String newName = itemNameToAdd.getText().toString();
                int newID = Integer.parseInt(itemIDToAdd.getText().toString());
                int newQty = Integer.parseInt(itemQtyToAdd.getText().toString());

                Item addThisItem = new Item(newName, newID, newQty);

                //get the inventory database singleton
                InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

                boolean itemAdded = db.addItem(addThisItem);

                if(itemAdded) {
                    //if item is successfully added, go back to inventory screen
                    startActivity(new Intent(AddItem.this, InventoryDataPage.class));
                } else {
                    //display an error message
                    TextView addItemDisplayText = findViewById(R.id.addItemError);
                    addItemDisplayText.setText("This item ID already exists in the inventory.");
                }

            }
        });
    }
}