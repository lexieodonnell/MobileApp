package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button editItems = findViewById(R.id.editPageButton);

        editItems.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //take data from EditText field
                EditText editID = findViewById(R.id.editItemID);
                EditText updatedName = findViewById(R.id.editedName);
                EditText updatedQty = findViewById(R.id.editedQty);

                //store data in a variable
                String editedName = updatedName.getText().toString();
                int iDToEdit = Integer.parseInt(editID.getText().toString());
                int editedQty = Integer.parseInt(updatedQty.getText().toString());

                Item editThisItem = new Item(editedName, iDToEdit, editedQty);

                //get the inventory database singleton
                InventoryDatabase db = InventoryDatabase.getInstance(getApplicationContext());

                boolean itemEdited = db.updateItem(editThisItem);

                if(itemEdited) {
                    //if item is successfully updated, go back to inventory screen
                    startActivity(new Intent(EditItem.this, InventoryDataPage.class));
                } else {
                    //display an error message
                    TextView editItemDisplayText = findViewById(R.id.editError);
                    editItemDisplayText.setText("That ID number does not exist.");
                }
            }
        });

    }
}