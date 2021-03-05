package com.example.assignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
    private MyDatabase database;
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
        database = MyDatabase.getINSTANCE(context);
        View view = holder.getView();
        ListsEntity entity = listsEntities.get(position);
        // replace the data
        holder.nameView.setText(entity.getListName());
        holder.discView.setText(entity.getDescription());

        if (entity.getColor().equals("Yellow")) {
            holder.getView().setBackgroundColor(Color.parseColor("#fffcbb"));
        } else if (entity.getColor().equals("Teal")){
            holder.getView().setBackgroundColor(Color.parseColor("#bbf1f1"));
        }

        holder.remove.setOnClickListener(v -> {

            // Onclicklistener  @override onclick (on the remove button) was converted to lambda ( to remember)
            Executors.newSingleThreadExecutor().execute(()->{
                ListsEntity entity1 = listsEntities.get(position);
                // Delete the record from the lists table
                database.listsDao().delete(entity1);
                listsEntities.remove(position);});

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listsEntities.size());

        });

        // set an onclick listener that get the position of the data and sends it to a the individual list activity
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(()->{
                    //Get the position
                    ListsEntity entity1 = listsEntities.get(position);
                    // go to the ProductDescriptionActivity and pass the code of the product to the activity
                    Intent intent = new Intent(v.getContext(), IndividualListsActivity.class);
                    intent.putExtra("id", entity1.getListId());
                    v.getContext().startActivity(intent);
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return listsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView nameView;
        private final TextView discView;
        private final ImageView remove;
        private final View myView;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.nameView = view.findViewById(R.id.listsName);
            this.discView = view.findViewById(R.id.listsDisc);
            this.remove = view.findViewById(R.id.removeList);
            this.myView = view;
        }
        public View getView() {
            return myView;
        }
    }

    public void setListsEntities(List<ListsEntity> listsEntities) {
        this.listsEntities = listsEntities;
        notifyDataSetChanged();
    }

//    public ListsEntity getDataAt(int position) {
//        return listsEntities.get(position);
//    }
}

