package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.R;
import com.vehiclerentalservices.model.Booking;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewAdapter>{

    Booking[] data;
    Context context;

    public BookAdapter(Booking[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_row, parent, false);
        return new myViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewAdapter holder, int position) {
        Booking booking = data[position];
        holder.name.setText(booking.getVehicle().getName());
        holder.date.setText(booking.getDateFrom());
        holder.price.setText(booking.getTotalAmount());

        // image k kam baki ba
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class myViewAdapter extends RecyclerView.ViewHolder{
        TextView date, name, price;
        ImageView img;

        public myViewAdapter(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.hDate);
            name = itemView.findViewById(R.id.hVehicleName);
            price = itemView.findViewById(R.id.hVehiclePrice);
            img = itemView.findViewById(R.id.hVehicleImage);
        }
    }
}
