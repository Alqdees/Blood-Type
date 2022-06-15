package com.Blood.types.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Blood.types.Model.Model;
import com.Blood.types.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterBelow> {

    private Context context;
    private ArrayList<Model> models;
    //Constructor
    public RecyclerViewAdapter(Context context,ArrayList<Model> models){

        this.context = context;
        this.models = models;

    }


    @NonNull
    @Override
    public AdapterBelow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);

        return new AdapterBelow(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBelow holder, int position) {
        Model model = models.get(position);
        holder.name.setText("model.getName()");
        holder.number.setText("model.getNumber()");
        holder.type.setText("model.getBlood_type()");


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AdapterBelow extends RecyclerView.ViewHolder{
        private TextView name,number,type;
        public AdapterBelow(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.Number);
            type = itemView.findViewById(R.id.type);

        }
    }
}
