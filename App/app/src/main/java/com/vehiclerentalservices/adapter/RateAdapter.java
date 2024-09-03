package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.R;
import com.vehiclerentalservices.model.RateReview;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.MyViewAdapter>{

    RateReview[] data;
    Context context;

    public RateAdapter(RateReview[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rate_review_row, parent, false);
        return  new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        try {
            RateReview rateReview = data[position];
            holder.rate.setText(rateReview.getRate());
            holder.review.setText(rateReview.getReview());
            holder.Uname.setText(rateReview.getUser().getName());
        }catch (Exception e){
            e.printStackTrace();
        }

        //String name = rateReview.getUser().getName();
       // Log.e("aaaa", "onBindViewHolde: "+name );

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder{
        TextView Uname, rate, review;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);

            Uname = itemView.findViewById(R.id.UserRateName);
            rate = itemView.findViewById(R.id.rateVehicle);
            review = itemView.findViewById(R.id.reviewVehicle);
        }
    }
}
