package com.example.fuelstationmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataModel extends RecyclerView.Adapter<DataModel.MyViewHolder> {
    private Context context;
    private ArrayList number_plate,type,capacity,rmcapacity;

    public DataModel(Context context, ArrayList number_plate, ArrayList type, ArrayList capacity, ArrayList rmcapacity) {
        this.context = context;
        this.number_plate = number_plate;
        this.type = type;
        this.capacity = capacity;
        this.rmcapacity = rmcapacity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.number_plate.setText(String.valueOf(number_plate.get(position)));
        holder.type.setText(String.valueOf(type.get(position)));
        holder.capacity.setText(String.valueOf(capacity.get(position)));
        holder.rmcapacity.setText(String.valueOf(rmcapacity.get(position)));
    }

    @Override
    public int getItemCount() {
        return number_plate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView number_plate,type,capacity,rmcapacity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            number_plate=itemView.findViewById(R.id.out_number_plate);
            type=itemView.findViewById(R.id.out_type);
            capacity=itemView.findViewById(R.id.out_capacity);
            rmcapacity=itemView.findViewById(R.id.out_Remaining_capacity);
        }
    }
}
