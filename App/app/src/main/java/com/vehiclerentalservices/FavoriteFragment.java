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
import com.vehiclerentalservices.adapter.FavAdapter;
import com.vehiclerentalservices.model.Favorite;
import com.vehiclerentalservices.model.User;

import java.lang.reflect.Type;
import java.util.List;


public class FavoriteFragment extends Fragment {
    int id;
    RecyclerView favRecycler;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        favRecycler = view.findViewById(R.id.favRecyclerView);
        favRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        String jwtToken = SharedPreferencesUtil.getToken(getContext());

        String username = JwtUtils.extractFromToken(jwtToken);

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/email/"+username;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    User[] user = gson.fromJson(response, User[].class);
                    for (User user1 : user) {
                        id = user1.getId();
                    }
                    String urls = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+id+"/favorite";

                    StringRequest request1 = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                Favorite[] favorites = gson.fromJson(response, Favorite[].class);
                                favRecycler.setAdapter(new FavAdapter(favorites, getActivity().getApplicationContext()));
                            }catch (Exception e){

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request1);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error : " + error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
        return view;
    }
}