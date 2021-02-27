package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ProductsEntity> productsEntities = new ArrayList<>();

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
        ProductsEntity Entity = productsEntities.get(position);
        // replace the data
        holder.textview.setText(Entity.getProductGrade());
        holder.textview.setText(Entity.getProductName());
    }

    @Override
    public int getItemCount() {
        return productsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.textview = view.findViewById(R.id.productView);
        }
    }

    public void setProductsEntities(List<ProductsEntity> productsEntities) {
        this.productsEntities = productsEntities;
        notifyDataSetChanged();
    }

}
