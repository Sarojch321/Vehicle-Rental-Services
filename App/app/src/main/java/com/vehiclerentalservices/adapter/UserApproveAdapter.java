package com.vehiclerentalservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vehiclerentalservices.ApproveBookDetailsActivity;
import com.vehiclerentalservices.ApproveUserByAdminActivity;
import com.vehiclerentalservices.MainActivity;
import com.vehiclerentalservices.R;
import com.vehiclerentalservices.model.User;

import org.json.JSONObject;

public class UserApproveAdapter extends RecyclerView.Adapter<UserApproveAdapter.MyViewAdapter>{

    User[] data;
    Context context;

    public UserApproveAdapter(User[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.approve_user_by_admin_row, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        User user = data[position];
        holder.Uname.setText(user.getName());
        holder.Uadd.setText(user.getAddress());
        holder.Udob.setText(user.getDob());
        holder.Uphn.setText(user.getMobile());

        //photo k kam baki

        int id = user.getId();
        holder.ApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/status/"+id;

                JSONObject obj = new JSONObject();
                try{
                    obj.put("status", 2);

                }catch (Exception e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "failed to approve user !! "+ error, Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public  class MyViewAdapter extends RecyclerView.ViewHolder{
        ImageView UPhoto, LUPhoto;
        TextView Uname,Uadd, Uphn, Udob, ApproveBtn;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);

            UPhoto = itemView.findViewById(R.id.PhotoForApp);
            Uname = itemView.findViewById(R.id.NameForApp);
            Uadd = itemView.findViewById(R.id.AddressForApp);
            Uphn = itemView.findViewById(R.id.MobileForApp);
            Udob = itemView.findViewById(R.id.DobForApp);
            LUPhoto = itemView.findViewById(R.id.LicenseForApp);
            ApproveBtn = itemView.findViewById(R.id.BtnForApp);
        }
    }
}
