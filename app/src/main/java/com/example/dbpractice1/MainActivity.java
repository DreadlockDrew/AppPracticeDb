package com.example.dbpractice1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dbpractice1.DataIntegration.ItemActivity;
import com.example.dbpractice1.DataIntegration.ItemDatabase;
import com.example.dbpractice1.LoginIntegration.LoginActivity;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowItems = findViewById(R.id.btnShowItems);
        btnShowItems.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                // Launch ItemActivity
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });



        ItemDatabase db = new ItemDatabase(this);

        //db.insertItem("user1", "2", "Apple");
        //db.insertItem("user1", "5", "Banana");
        //db.insertItem("user2", "7", "Orange");


        //db.clearTable();

        /*

        List<Item> itemsForUser1 = db.getItemsByUsername("user1");
        for(Item item : itemsForUser1) {
            Log.d("MainActivity", "Item ID: " + item.getItemID() + ", Item Name: " + item.getItemName() + ", Quantity: " + item.getItemQuantity());
        }
        */

    }
    /*
    @Override //Old one used to test logins. Logins successful
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginPageButton); // Replace with the actual ID of your login button

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }
*/
    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
