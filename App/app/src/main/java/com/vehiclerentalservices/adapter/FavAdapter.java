package com.vehiclerentalservices.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.vehiclerentalservices.R;
import com.vehiclerentalservices.VehicleDetailActivity;
import com.vehiclerentalservices.model.Favorite;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.myViewholder>{

    Favorite[] data;
    Context context;

    public FavAdapter(Favorite[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_row, parent,false);
        return new myViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        Favorite favorite = data[position];
        holder.favname.setText(favorite.getVehicle().getName());
        holder.favamount.setText(favorite.getVehicle().getAmount());
        int flag = favorite.getVehicle().getStatus();
        if (flag == 0){
            holder.favstatus.setText("Available");
        } else if (flag == 1) {
            holder.favstatus.setText("Booked");
        }
        int id = favorite.getVehicle().getId();
        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VehicleDetailActivity.class);
                intent.putExtra("VID", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        // image ko remaining x

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class  myViewholder extends RecyclerView.ViewHolder {
        TextView favname, favamount, favstatus;
        TextView bookBtn;
        ImageView img;
        @SuppressLint("WrongViewCast")
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            favname = itemView.findViewById(R.id.favVName);
            favamount = itemView.findViewById(R.id.favVPrice);
            favstatus = itemView.findViewById(R.id.favVStatus);
            img = itemView.findViewById(R.id.favVehicleImage);
            bookBtn = itemView.findViewById(R.id.favBookBtn);
        }
    }
}
