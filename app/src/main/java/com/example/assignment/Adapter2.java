package com.example.assignment;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;


public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private List<ListsEntity> listEntity = new ArrayList<>();
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

        ListsEntity Entity = listEntity.get(position);
        // replace the data
        holder.nameView.setText(Entity.getListName());
        holder.discView.setText(Entity.getDescription());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ListsEntity Entity = listEntity.get(holder.getAdapterPosition());

                    database.listsDao().delete(Entity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listEntity.size();
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

    public void setListEntity(List<ListsEntity> listEntity) {
        this.listEntity = listEntity;
        notifyDataSetChanged();
    }

    public ListsEntity getDataAt(int position) {
        return listEntity.get(position);
    }
}

