package com.vehiclerentalservices;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehiclerentalservices.Jwt.JwtUtils;
import com.vehiclerentalservices.adapter.BookApproveAdapter;
import com.vehiclerentalservices.model.Booking;
import com.vehiclerentalservices.model.User;
import com.vehiclerentalservices.model.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingRequestFragment extends Fragment {

    RecyclerView recyclerView;

    public BookingRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_request, container, false);
        recyclerView = view.findViewById(R.id.approveRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String jwtToken = SharedPreferencesUtil.getToken(getContext());

        String username = JwtUtils.extractFromToken(jwtToken);

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/email/"+username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int id = 0;
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            User[] user = gson.fromJson(response, User[].class);
                            for (User user1 : user) {
                                id = user1.getId();
                            }

                            String bookUrl = "http://192.168.43.231:8080/api/vehicleRentalServices/booking/status/user/"+id+"/"+0;
                            StringRequest request = new StringRequest(Request.Method.GET, bookUrl,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            GsonBuilder gsonBuilder1 = new GsonBuilder();
                                            Gson gson1 = gsonBuilder1.create();
                                            Booking[] bookings = gson1.fromJson(response, Booking[].class);
                                            recyclerView.setAdapter(new BookApproveAdapter(bookings, getActivity().getApplicationContext()));
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext(), "Error "+ error, Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(request);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network error !! " +error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

        return view;
    }
}