package com.example.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class cartRecyclerView extends  RecyclerView.Adapter<cartRecyclerView.myViewHolder>{

    List<Product> products;
    TextView total_cart;

    public cartRecyclerView(List<Product> products, TextView total_cart) {
        this.products = products;
        this.total_cart = total_cart;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.id.setText(String.valueOf(products.get(position).getPid()));
        holder.pName.setText(products.get(position).getP_name());
        holder.pQuantity.setText(products.get(position).getQnt());
        holder.pPrice.setText(products.get(position).getPrice());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = AppDatabase.getDbInstance(holder.id.getContext()); // JUST GET AN ITEM FROM THE view holder as you need to give and context\
                ProductDao productDao = db.productDao();

                productDao.deleteById(products.get(position).getPid());
                products.remove(position);

                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

                updatePrice(); // update the data in the total cart dynamically
            }
        });

        holder.inc_btn.setOnClickListener(new View.OnClickListener() { // for increasing quantity.
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(products.get(position).getQnt());
                qnt++;
                products.get(position).setQnt(String.valueOf(qnt));

                // change the data and  again update it
                notifyDataSetChanged();
                updatePrice();
            }
        });

        holder.dec_btn.setOnClickListener(new View.OnClickListener() { // for decreasing quantity.
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(products.get(position).getQnt());
                qnt--;
                products.get(position).setQnt(String.valueOf(qnt));

                // change the data and  again update it
                notifyDataSetChanged();
                updatePrice();
            }
        });
    }


    // this will dynamically delete your data from the cartView
    private void updatePrice() {
        int sum =0, i;
        // setting the total in the below cart.
        for (i =0; i<products.size(); i++){
            sum = sum + (Integer.parseInt(products.get(i).getPrice()) *Integer.parseInt( products.get(i).getQnt()));
        }
        total_cart.setText("Total Amount : INR "+ sum);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class  myViewHolder extends RecyclerView.ViewHolder{
        TextView  id, pName, pPrice, pQuantity;
        ImageButton deleteBtn;
        ImageButton inc_btn, dec_btn;

        public  myViewHolder(View itemView){
            super(itemView);
            id =  itemView.findViewById(R.id.recid);
            pName = itemView.findViewById(R.id.recpname);
            pPrice = itemView.findViewById(R.id.recpprice);
            pQuantity = itemView.findViewById(R.id.recqnt);
            deleteBtn = itemView.findViewById(R.id.delbtn);

            inc_btn = itemView.findViewById(R.id.inc_btn);
            dec_btn = itemView.findViewById(R.id.dec_btn);
        }
    }
}
