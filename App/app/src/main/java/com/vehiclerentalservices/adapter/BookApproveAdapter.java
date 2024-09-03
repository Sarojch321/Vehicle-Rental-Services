package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.ApproveBookDetailsActivity;
import com.vehiclerentalservices.R;
import com.vehiclerentalservices.model.Booking;

public class BookApproveAdapter extends RecyclerView.Adapter<BookApproveAdapter.MyViewAdapter> {

    Booking[] data;
    Context context;

    public BookApproveAdapter(Booking[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bookingapprove_row, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        Booking booking = data[position];
        holder.Vname.setText(booking.getVehicle().getName());
        holder.Vamount.setText(booking.getTotalAmount());
        holder.VDate.setText(booking.getDateFrom());
        int id = booking.getId();
        holder.NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApproveBookDetailsActivity.class);
                intent.putExtra("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder{

        TextView Vname, VDate, Vamount;
        ImageButton NextBtn;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            Vname = itemView.findViewById(R.id.setVehicleName);
            VDate = itemView.findViewById(R.id.setDate);
            Vamount = itemView.findViewById(R.id.setAmount);
            NextBtn = itemView.findViewById(R.id.nextBtn);
        }
    }
}
