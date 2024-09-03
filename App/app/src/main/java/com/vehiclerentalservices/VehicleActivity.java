package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehiclerentalservices.adapter.VListAdapter;
import com.vehiclerentalservices.model.Vehicle;

public class VehicleActivity extends AppCompatActivity {

    TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        RecyclerView vehicleRecycler = findViewById(R.id.vehicleRecycler);
        vehicleRecycler.setLayoutManager(new LinearLayoutManager(this));
        topic = findViewById(R.id.txtTopic);
        topic.setText("Vehicle");

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String status = intent.getStringExtra("IsAll");
        if (status.equals("yes")){
            // get all vehicle wala code
            String url = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                Vehicle[] vehicles = gson.fromJson(response, Vehicle[].class);
                                vehicleRecycler.setAdapter(new VListAdapter(vehicles, getApplicationContext()));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VehicleActivity.this, "Something went wrong !! "+error, Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }

        if (status.equals("no")){
           String type = intent.getStringExtra("type");
           // get by type wala code
            String TypeUrl = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle/searchs/"+type;

            StringRequest request = new StringRequest(Request.Method.GET, TypeUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                Vehicle[] vehicles = gson.fromJson(response, Vehicle[].class);
                                vehicleRecycler.setAdapter(new VListAdapter(vehicles, getApplicationContext()));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VehicleActivity.this, "Something went wrong !! "+error, Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }
    }
}