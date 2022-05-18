package com.example.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ShowCart extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView total_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        getSupportActionBar().hide();
        total_cart = findViewById(R.id.rateview);
        getRoomData();
    }

    public  void getRoomData()
    {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        ProductDao productDao = db.productDao();

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));      // setting the recycler view in this acitivity.

        //List<User> users =userDao.getAllUsers(); // getting all user using the method in userDao.

        //recyclerViewAdapter adapter = new recyclerViewAdapter(users);
        //recyclerView.setAdapter(adapter);

        List<Product> products = productDao.getAllProduct();
        cartRecyclerView adapter = new cartRecyclerView(products,total_cart);
        recyclerView.setAdapter(adapter);

        int sum =0, i;

        // setting the total in the below cart.
        for (i =0; i<products.size(); i++){
            sum = sum + (Integer.parseInt(products.get(i).getPrice()) *Integer.parseInt( products.get(i).getQnt()));
        }
        total_cart.setText("Total Amount : INR "+sum);
    }
}

/*
* From mainActivity.java btn_go_to_card will lead here
* Functionality:
* 1. shows the data in the cart view, also the total amount of every product.
* 2. Adapter takes care of the item
* 3. Last for loop takes care of the total number of item inside te cart.
*
* */