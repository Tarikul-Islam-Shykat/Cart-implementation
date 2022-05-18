package com.example.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding am;
    String p_name, p_price, p_quantity;
    int p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(am.getRoot());
        getSupportActionBar().hide();

        // for adding item
        am.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p_id = Integer.parseInt(am.edtPid.getText().toString());
                p_name = am.edtPName.getText().toString();
                p_price = am.edtPPrice.getText().toString();
                p_quantity = am.edtPQuantity.getText().toString();

                saveProduct(p_id, p_name, p_price, p_quantity);
            }
        });

        // button for going to the cart
        am.btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowCart.class));
            }
        });
    }

    private void saveProduct(int p_id, String p_name, String p_price, String p_quantity) {

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        ProductDao productDao =  db.productDao(); // came from AppDatabase.java
        Boolean check =productDao.is_exist(p_id);  // checking so that same p_id dont exist

        if(check == false){
            Product product = new Product(p_id, p_name, p_price, p_quantity);

            db.productDao().insertAll(product);
            am.txtResult.setText("Stored Successfully");
            setBlank();
        }
        else
        {
            am.txtResult.setText("Already Exist");
            setBlank();
        }

    }


    //sets all the data to blank
    public void setBlank(){
        am.edtPid.setText("");
        am.edtPName.setText("");
        am.edtPPrice.setText("");
        am.edtPQuantity.setText("");
    }
}
/*
* this is the main activity.
* Functionality:
* 1. getting and setting the value of items in the room database.
*
* */