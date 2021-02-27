package com.example.assignment;

import android.app.Activity;
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

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private List<ListsEntity> listsEntities = new ArrayList<>();
    private myDatabase database;
    private Activity context;

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
        database = myDatabase.getINSTANCE(context);

        ListsEntity Entity = listsEntities.get(position);
        // replace the data
        holder.nameView.setText(Entity.getListName());
        holder.discView.setText(Entity.getDescription());
        holder.remove.setOnClickListener(v -> {

            // Onclicklistener  @override was converted to lambda ( to remember)
            Executors.newSingleThreadExecutor().execute(()->{
                ListsEntity Entity1 = listsEntities.get(position);
                // Delete the record from the database
                database.listsDao().delete(Entity1);
                listsEntities.remove(position);});

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listsEntities.size());
        });

    }

    @Override
    public int getItemCount() {
        return listsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView nameView;
        private final TextView discView;
        private ImageView remove;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.nameView = view.findViewById(R.id.listsName);
            this.discView = view.findViewById(R.id.listsDisc);
            this.remove = view.findViewById(R.id.removeList);
        }
    }

    public void setListsEntities(List<ListsEntity> listsEntities) {
        this.listsEntities = listsEntities;
        notifyDataSetChanged();
    }

    public ListsEntity getDataAt(int position) {
        return listsEntities.get(position);
    }
}

