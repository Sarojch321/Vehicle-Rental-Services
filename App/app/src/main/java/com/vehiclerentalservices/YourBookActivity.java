package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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
import com.vehiclerentalservices.adapter.YourBookAdapter;
import com.vehiclerentalservices.model.Booking;

import java.util.Arrays;

public class YourBookActivity extends AppCompatActivity {

    TextView topic;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_book);

        topic = findViewById(R.id.txtTopic);
        topic.setText("Your Booking");
        recyclerView = findViewById(R.id.RecyclerViewYourBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        int flag = 0;

        SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
        int Userid = pref.getInt("userId", 0);
        String Furl = "http://192.168.43.231:8080/api/vehicleRentalServices/booking/status/"+flag;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Furl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Booking[] bookings = gson.fromJson(response, Booking[].class);
                        Booking[] filteredBookings = new Booking[bookings.length];
                        int count = 0;
                        for (Booking booking : bookings){
                            if (booking.getUser().getId() == Userid){
                                filteredBookings[count++] = booking;
                            }
                        }
                        filteredBookings = Arrays.copyOf(filteredBookings, count);
                        recyclerView.setAdapter(new YourBookAdapter(filteredBookings, getApplicationContext()));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}