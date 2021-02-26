package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private final Vector<String> data;
    private final Vector<String> data2;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // replace the data
        String value = data.get(position);
        String value2 = data2.get(position);
        holder.nameView.setText(String.valueOf(value));
        holder.discView.setText(String.valueOf(value2));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView discView;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.nameView = view.findViewById(R.id.listsName);
            this.discView = view.findViewById(R.id.listsDisc);
        }
    }

    public Adapter2 (Vector<String> data, Vector<String> data2) {
        this.data  =  data;
        this.data2 =  data2;
    }
}