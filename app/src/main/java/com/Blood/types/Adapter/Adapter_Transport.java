package com.Blood.types.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Blood.types.Model.ModelTransport;
import com.Blood.types.R;

import java.util.ArrayList;

public class Adapter_Transport extends RecyclerView.Adapter<Adapter_Transport.AdapterTransport> {

    private ArrayList<ModelTransport> modelTransports;
    private Context context;


    public Adapter_Transport(Context context, ArrayList<ModelTransport> list){
        this.context = context;
        this.modelTransports= list;
    }
    @NonNull
    @Override
    public AdapterTransport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.cardview_transport,parent,false);
        return new AdapterTransport(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransport holder, int position) {
        ModelTransport models = modelTransports.get(position);
        String name = models.getName();
        String number= models.getNumber();
        String title = models.getTitle();
        String line = models.getLine();

        holder.tv_name.setText(name);
        holder.tv_number.setText(number);
        holder.tv_title.setText(title);
        holder.tv_line.setText(line);
        holder.getAdapterPosition();


    }

    @Override
    public int getItemCount() {
       return modelTransports.size();
    }

    class AdapterTransport extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_number,tv_title,tv_line;


        public AdapterTransport(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.name);
            tv_number=itemView.findViewById(R.id.Number);
            tv_title= itemView.findViewById(R.id.title);
            tv_line=itemView.findViewById(R.id.line);


        }
    }
}
