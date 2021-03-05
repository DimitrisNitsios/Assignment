package com.example.assignment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {

    private List<ProductsEntity> productsEntities = new ArrayList<>();
    private MyDatabase database;
    private Activity context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.product_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        database = MyDatabase.getINSTANCE(context);
        View view = holder.getView();
        ProductsEntity Entity = productsEntities.get(position);
        // replace the data
        holder.nameView.setText(String.valueOf(Entity.getProductName()));
        holder.gradeView.setText(String.valueOf(Entity.getProductGrade()).toUpperCase());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(()->{
                    ProductsEntity entity1 = productsEntities.get(position);
                    // Delete the record from the products table
//                    database.productsDao().delete(entity1);
//                    productsEntities.remove(position);
                });
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, productsEntities.size());
            }
        });

        // set an onclick listener that get the position of the data and sends it to a the product description activity
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(()->{
                    //Get the position
                    ProductsEntity entity1 = productsEntities.get(position);
                    // go to the ProductDescriptionActivity and pass the code of the product to the activity
                    Intent intent = new Intent(v.getContext(), IndividualListsActivity.class);
                    intent.putExtra("id", entity1.getCode());
                    v.getContext().startActivity(intent);
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return productsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View myView;
        private final TextView nameView;
        private final TextView gradeView;
        private final ImageView remove;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.nameView = view.findViewById(R.id.product_view_name);
            this.gradeView = view.findViewById(R.id.product_view_grade);
            this.remove = view.findViewById(R.id.remove_from_list);
            myView = view;
        }
        public View getView() {
            return myView;
        }
    }

    public void setProductsEntities(List<ProductsEntity> productsEntities) {
        this.productsEntities = productsEntities;
        notifyDataSetChanged();
    }

}
