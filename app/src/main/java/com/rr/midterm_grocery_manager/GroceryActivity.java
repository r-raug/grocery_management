package com.rr.midterm_grocery_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rr.midterm_grocery_manager.data.database.AppDatabaseHelper;

import java.util.List;

public class GroceryActivity extends AppCompatActivity {
    private EditText groceryInput;
    private Spinner itemSpinner;
    private Button saveButton;
    private Button deleteButton;
    private RecyclerView recyclerView;
    private GroceryAdapter groceryAdapter;
    private AppDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        groceryInput = findViewById(R.id.groceryInput);
        itemSpinner = findViewById(R.id.itemSpinner);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new AppDatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryAdapter = new GroceryAdapter(databaseHelper.getAllGroceryItems(), this);
        recyclerView.setAdapter(groceryAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groceryName = groceryInput.getText().toString();
                String category = itemSpinner.getSelectedItem().toString();

                if (!groceryName.isEmpty()) {
                    GroceryItem item = new GroceryItem();
                    item.setName(groceryName);
                    item.setCategory(category);
                    databaseHelper.addGroceryItem(item);

                    groceryInput.setText("");
                    updateRecyclerView();
                    Toast.makeText(GroceryActivity.this, "Item saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GroceryActivity.this, "Please enter a name for the item.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = groceryAdapter.getSelectedPosition();
                if (selectedPosition != -1) {
                    GroceryItem itemToDelete = groceryAdapter.getItem(selectedPosition);
                    databaseHelper.deleteGroceryItem(itemToDelete.getId());
                    groceryAdapter.removeItem(selectedPosition);
                    groceryAdapter.setSelectedPosition(-1);
                    Toast.makeText(GroceryActivity.this, "Item deleted!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(GroceryActivity.this, "Please select an item to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRecyclerView() {
        List<GroceryItem> groceryList = databaseHelper.getAllGroceryItems();
        groceryAdapter.updateGroceryList(groceryList);
    }
}
