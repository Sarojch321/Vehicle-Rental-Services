package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.HomeFragment;
import com.vehiclerentalservices.R;
import com.vehiclerentalservices.VehicleDetailActivity;
import com.vehiclerentalservices.model.Vehicle;

public class VAdapter extends RecyclerView.Adapter<VAdapter.myViewadapter>{

    Vehicle[] data;
    Context context;

    public VAdapter(Vehicle[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_row, parent, false);
        return new myViewadapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewadapter holder, int position) {
        Vehicle vehicle = data[position];
        holder.name.setText(vehicle.getName());
        holder.price.setText(vehicle.getAmount());
        int vId = vehicle.getId();
        holder.BookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VehicleDetailActivity.class);
                intent.putExtra("VID", vId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //image and btn k kam karna baki ba

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class myViewadapter extends RecyclerView.ViewHolder{
        TextView name, price, BookBtn;
        ImageView image;
        public myViewadapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vehicleName);
            price = itemView.findViewById(R.id.vehiclePrice);
            BookBtn = itemView.findViewById(R.id.bookingBtn);

            image = itemView.findViewById(R.id.image_Id);

        }
    }
}
