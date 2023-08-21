package com.example.dbpractice1.DataIntegration;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbpractice1.R;

import java.util.List;

public class ItemActivity extends AppCompatActivity implements ItemAdapter.OnItemActionListener{

    private ItemDatabase database;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> items;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username="user1";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);

        // Initialize the database
        database = new ItemDatabase(this);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.ItemDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load items from the database
        loadItems();
    }

    private void loadItems() {
        items = database.getItemsByUsername(username); // Replace with the desired username

        // Set the adapter to the RecyclerView
        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemActionListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();  // Reload the list when the activity is resumed
    }

    public void refreshAdapter() {
        List<Item> updatedItems = database.getItemsByUsername(username);
        adapter.updateData(updatedItems);
    }


    @Override
    public void onDeleteItem(int itemId) {
        database.deleteItem(itemId);
        refreshAdapter();
    }

    @Override
    public void onEditItem(int itemId, String name, int quantity) {
        Log.d("ItemActivity", "Attempted to update:"+itemId+" to have values "+name+" and "+quantity);
        database.updateItem(itemId,name,quantity);
        refreshAdapter();
    }



}

