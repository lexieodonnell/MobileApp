package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class InventoryDataPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_data);

        InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

        TableLayout inventoryTable = findViewById(R.id.itemTableLayout);

        List<Item> list = db.getItems();

        for(Item currentItem: list) {
                TableRow tr = new TableRow(this);
                TextView column1 = new TextView(this);
                column1.setText(currentItem.getItemName());
                TextView column2 = new TextView(this);
                column2.setText(String.valueOf(currentItem.getItemID()));
                TextView column3 = new TextView(this);
                column3.setText(String.valueOf(currentItem.getItemQty()));
                tr.addView(column1);
                tr.addView(column2);
                tr.addView(column3);
                inventoryTable.addView(tr);
        }

        //button and listener for adding items
        Button addItemPageButton = findViewById(R.id.addItem);

        addItemPageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryDataPage.this, AddItem.class));
            }
        });

        //button and listener for deleting items
        Button deletePageButton = findViewById(R.id.deleteItemPageButton);

        deletePageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryDataPage.this, DeleteItem.class));
            }
        });

        Button editItemPageButton = findViewById(R.id.editItems);

        editItemPageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryDataPage.this, EditItem.class));
            }
        });

        Button permissionButton = findViewById(R.id.permission);

        permissionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryDataPage.this, PermissionPage.class));
            }
        });
    }


}