package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehiclerentalservices.adapter.RateAdapter;
import com.vehiclerentalservices.model.RateReview;
import com.vehiclerentalservices.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VehicleDetailActivity extends AppCompatActivity {

    RecyclerView recyclerViewrate, recyclerViewphoto;
    TextView bookBtn,deleteBtn, Vtitle, FavoriteBtn, Vtype, Vamount, VdriverNeed, Vstatus, Vdistrict, Vcity, Vward, Vtole;
    TextView RUser, Rrate, Rreview, postBtn;
    EditText rate, review;
    int status;

    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        recyclerViewrate = findViewById(R.id.vehicleDetailRateRecycler);
        recyclerViewrate.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewphoto = findViewById(R.id.vehicleDetailPhotoRecycler);
        recyclerViewphoto.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        bookBtn = findViewById(R.id.bookNow);
        Vtitle = findViewById(R.id.vehicleDetailsTitle);
        FavoriteBtn = findViewById(R.id.AddFavorite);
        Vtype = findViewById(R.id.detailsType);
        Vamount = findViewById(R.id.detailsAmount);
        VdriverNeed = findViewById(R.id.detailsDriver);
        Vstatus = findViewById(R.id.detailsStatus);
        Vdistrict = findViewById(R.id.detailsDistrict);
        Vcity = findViewById(R.id.detailsCity);
        Vward = findViewById(R.id.detailsWard);
        Vtole = findViewById(R.id.detailsTole);
        deleteBtn = findViewById(R.id.deleteVehicle);
        RUser = findViewById(R.id.RateUser);
        Rrate = findViewById(R.id.recentRate);
        Rreview = findViewById(R.id.recentReview);
        postBtn = findViewById(R.id.p);
        rate = findViewById(R.id.Vrate);
        review = findViewById(R.id.Vreview);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences("UserRole", MODE_PRIVATE);
        String roles = preferences.getString("roles", "CUSTOMER");

        if (roles.equals("OWNER")){
            FavoriteBtn.setVisibility(View.GONE);
            bookBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        if (roles.equals("CUSTOMER")){
            FavoriteBtn.setVisibility(View.VISIBLE);
            bookBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        int Vid = intent.getIntExtra("VID", 0);

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle/"+Vid;

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("name");
                            String type = response.getString("type");
                            amount = response.getString("amount");
                            String driver = response.getString("driverstatus");
                            status = response.getInt("status");

                            JSONObject location = response.getJSONObject("location");
                            String locationDistrict = location.getString("district");
                            String locationCity = location.getString("city");
                            String locationWard = location.getString("ward");
                            String locationTole = location.getString("tole");

                            Vtitle.setText(title);
                            Vtype.setText(type);
                            Vamount.setText(amount);
                            VdriverNeed.setText(driver);
                            Vdistrict.setText(locationDistrict);
                            Vcity.setText(locationCity);
                            Vward.setText(locationWard);
                            Vtole.setText(locationTole);
                            if (status == 0){
                                Vstatus.setText("Available");
                            }
                            if (status == 1){
                                Vstatus.setText("Booked");
                                bookBtn.setVisibility(View.GONE);
                            }

                            String ShowRurl = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle/"+Vid+"/rate";

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, ShowRurl,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                GsonBuilder gsonBuilder = new GsonBuilder();
                                                Gson gson = gsonBuilder.create();
                                                RateReview[] rateReviews = gson.fromJson(response, RateReview[].class);
                                                recyclerViewrate.setAdapter(new RateAdapter(rateReviews, getApplicationContext()));
                                            }catch (Exception e){

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(VehicleDetailActivity.this, "Failed to load rate and review. "+error, Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(stringRequest);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VehicleDetailActivity.this, "Something went wrong. "+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request1);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleDetailActivity.this, BookingActivity.class);
                intent.putExtra("idv", Vid);
                intent.putExtra("TotalAmount",amount);
                startActivity(intent);
            }
        });

        FavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
                int id = pref.getInt("userId", 0);

                String Furl = "http://192.168.43.231:8080/api/vehicleRentalServices/favorite";

                int finalVid = Vid;
                JSONObject object = new JSONObject();
                try {
                    JSONObject user = new JSONObject();
                    user.put("id", id);

                    JSONObject vehicle = new JSONObject();
                    vehicle.put("id", finalVid);

                    object.put("user", user);
                    object.put("vehicle", vehicle);

                }catch (Exception e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Furl, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(VehicleDetailActivity.this, "Add Favorite !!", Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VehicleDetailActivity.this, "Network error !!", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
                int id = pref.getInt("userId", 0);

                String RRrate = rate.getText().toString();
                String RRreview = review.getText().toString();

                rate.setText("");
                review.setText("");


                String Rurl = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+id+"/rate";

                int finalid = Vid;
                JSONObject rate = new JSONObject();
                try {
                    JSONObject vehicle = new JSONObject();
                    vehicle.put("id", finalid);

                    rate.put("rate", RRrate);
                    rate.put("review",RRreview);
                    rate.put("vehicle", vehicle);

                }catch (Exception e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Rurl, rate,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String Showrate = response.getString("rate");
                                    String Showreview = response.getString("review");

                                    JSONObject user = response.getJSONObject("user");
                                    String Uname = user.getString("name");

                                    RUser.setText(Uname);
                                    Rrate.setText(Showrate);
                                    Rreview.setText(Showreview);



                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VehicleDetailActivity.this, "Network error !! "+error, Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int finalVid = Vid;

                String Durl = "http://192.168.43.231:8080/api/vehicleRentalServices/vehicle/"+Vid;

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Durl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(VehicleDetailActivity.this, "Deleted !!", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VehicleDetailActivity.this, "Failed to delete "+error, Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });
    }
}