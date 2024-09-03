package com.vehiclerentalservices.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehiclerentalservices.R;

import java.util.List;

public class BrowseVPhoto extends RecyclerView.Adapter<BrowseVPhoto.MyViewAdapter>{

    private final List<Bitmap> imageBitmaps;

    public BrowseVPhoto(List<Bitmap> imageBitmaps) {
        this.imageBitmaps = imageBitmaps;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vehicle_photo_row_for_browse, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        Bitmap bitmap = imageBitmaps.get(position);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public  class MyViewAdapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.vehiclePhoto);
        }
    }
}
