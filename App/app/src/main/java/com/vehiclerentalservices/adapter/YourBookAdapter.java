package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.R;
import com.vehiclerentalservices.model.Booking;

public class YourBookAdapter extends RecyclerView.Adapter<YourBookAdapter.MyViewAdapter>{

    Booking[] data;
    Context context;

    public YourBookAdapter(Booking[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.your_booking_row, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        Booking booking = data[position];
        holder.Vname.setText(booking.getVehicle().getName());
        holder.date.setText("Date : "+booking.getDateFrom());
        int flag = booking.getFlag();
        if (flag == 0){
            holder.status.setText("Status : Pending");
        }

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public  class MyViewAdapter extends RecyclerView.ViewHolder{

        TextView Vname, date, status;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            Vname = itemView.findViewById(R.id.VnameYourBook);
            date = itemView.findViewById(R.id.dateYourBook);
            status = itemView.findViewById(R.id.statusYourBook);
        }
    }
}
