package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Vector<Double> data;

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
        // replace the data
        double value = data.get(position);
        holder.textview.setText(String.valueOf(value));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.textview = view.findViewById(R.id.productView);
        }
    }

    public Adapter (Vector<Double> data) {
        this.data = data;
    }

}
