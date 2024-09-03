package com.vehiclerentalservices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vehiclerentalservices.Jwt.JwtUtils;
import com.vehiclerentalservices.adapter.FavAdapter;
import com.vehiclerentalservices.adapter.VAdapter;
import com.vehiclerentalservices.model.Favorite;
import com.vehiclerentalservices.model.User;
import com.vehiclerentalservices.model.Vehicle;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    TextView seeAllBtn, bike, car;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Vehicle[] vehicles = gson.fromJson(response, Vehicle[].class);
                            recyclerView.setAdapter(new VAdapter(vehicles, getActivity().getApplicationContext()));
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network error !!", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

        seeAllBtn = view.findViewById(R.id.SeeAll);
        bike = view.findViewById(R.id.bike);
        car = view.findViewById(R.id.car);

        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VehicleActivity.class);
                intent.putExtra("IsAll", "yes");
                startActivity(intent);
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VehicleActivity.class);
                intent.putExtra("IsAll", "no");
                intent.putExtra("type", "Bike");
                startActivity(intent);
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VehicleActivity.class);
                intent.putExtra("IsAll", "no");
                intent.putExtra("type", "Car");
                startActivity(intent);
            }
        });

        return view;
    }
}