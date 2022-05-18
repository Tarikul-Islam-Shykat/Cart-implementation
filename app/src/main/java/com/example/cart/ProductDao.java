package com.example.cart;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    // inserting user
    @Insert
    void insertAll(Product... product);

    // getting all the elements
    @Query("SELECT * FROM Product")
    List<Product> getAllProduct();

    // deleting products from the element
    @Query("DELETE FROM Product where pid =:id")
    void deleteById(int id);

    //  cheking if same id exist
    @Query("SELECT EXISTS (SELECT * FROM Product WHERE pid = :productId)")
    Boolean is_exist(int productId);

}
