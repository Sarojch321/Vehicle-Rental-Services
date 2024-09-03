package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehiclerentalservices.adapter.UserApproveAdapter;
import com.vehiclerentalservices.model.User;

public class ApproveUserByAdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_user_by_admin);

        recyclerView = findViewById(R.id.UserAppByAdminRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtTopic = findViewById(R.id.txtTopic);
        txtTopic.setText("Approve User");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/approve/list/"+1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            User[] users = gson.fromJson(response, User[].class);
                            recyclerView.setAdapter(new UserApproveAdapter(users, getApplicationContext()));
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}