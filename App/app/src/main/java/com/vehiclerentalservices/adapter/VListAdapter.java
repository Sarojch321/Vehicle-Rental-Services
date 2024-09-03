package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.R;
import com.vehiclerentalservices.VehicleDetailActivity;
import com.vehiclerentalservices.model.Vehicle;

public class VListAdapter extends RecyclerView.Adapter<VListAdapter.MyViewAdapter>{

    Vehicle[] data;
    Context context;

    public VListAdapter(Vehicle[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vehicle_row, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        Vehicle vehicle = data[position];
        holder.Vname.setText(vehicle.getName());
        holder.Vamount.setText(vehicle.getAmount());
        int id = vehicle.getId();
        holder.VPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VehicleDetailActivity.class);
                intent.putExtra("VID", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        // image ko kam baki x
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder{
        TextView Vname, Vamount;
        ImageView Vphoto;
        ImageButton VPhotoBtn;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            Vname = itemView.findViewById(R.id.vehicleTitleL);
            Vamount = itemView.findViewById(R.id.vehiclePriceL);
            Vphoto = itemView.findViewById(R.id.vehicleImageL);
            VPhotoBtn = itemView.findViewById(R.id.vehicleBtnL);
        }
    }
}
