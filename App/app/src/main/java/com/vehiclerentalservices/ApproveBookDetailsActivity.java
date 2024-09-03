package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApproveBookDetailsActivity extends AppCompatActivity {

    TextView Vname, UserName, UserPhone, pickDate, dropDate, amount, pickDistrict, pickCity, pickWard, pickTole;
    TextView dropDistrict, dropCity, dropWard, dropTole, driverNeed, bookingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_book_details);

        Vname = findViewById(R.id.appVehicle);
        UserName = findViewById(R.id.appCName);
        UserPhone = findViewById(R.id.appCPhone);
        pickDate = findViewById(R.id.appPickDate);
        dropDate = findViewById(R.id.appDropDate);
        amount = findViewById(R.id.appAmount);
        pickDistrict = findViewById(R.id.txtpDistrict);
        pickCity = findViewById(R.id.txtpCity);
        pickWard = findViewById(R.id.txtpWardNo);
        pickTole = findViewById(R.id.txtpTole);
        dropDistrict = findViewById(R.id.txtdDistrict);
        dropCity = findViewById(R.id.txtdCity);
        dropWard = findViewById(R.id.txtdWardNo);
        dropTole = findViewById(R.id.txtdTole);
        driverNeed = findViewById(R.id.driverNeed);
        bookingBtn = findViewById(R.id.placeBooking);
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        Intent intent = getIntent();
        int Bid = intent.getIntExtra("id", 0);

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/booking/"+Bid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Pickdate = response.getString("dateFrom");
                            String dropdate = response.getString("dateTo");
                            String Tamount = response.getString("totalAmount");
                            String driver = response.getString("driverNeed");

                            JSONObject vehicle = response.getJSONObject("vehicle");
                            String VehicleName = vehicle.getString("name");

                            JSONObject user = response.getJSONObject("user");
                            String Uname = user.getString("name");
                            String Uphone = user.getString("mobile");

                            JSONObject pick = response.getJSONObject("pickupLocation");
                            String pDistrict = pick.getString("district");
                            String pCity = pick.getString("city");
                            String pWard = pick.getString("ward");
                            String pTole = pick.getString("tole");

                            JSONObject drop = response.getJSONObject("pickupLocation");
                            String dDistrict = drop.getString("district");
                            String dCity = drop.getString("city");
                            String dWard = drop.getString("ward");
                            String dTole = drop.getString("tole");

                            Vname.setText(VehicleName);
                            UserName.setText(Uname);
                            UserPhone.setText(Uphone);
                            pickDate.setText(Pickdate);
                            dropDate.setText(dropdate);
                            amount.setText(Tamount);
                            driverNeed.setText(driver);
                            pickDistrict.setText(pDistrict);
                            pickCity.setText(pCity);
                            pickWard.setText(pWard);
                            pickTole.setText(pTole);
                            dropDistrict.setText(dDistrict);
                            dropCity.setText(dCity);
                            dropWard.setText(dWard);
                            dropTole.setText(dTole);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApproveBookDetailsActivity.this, "Failed to load booking details" + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Furl = "http://192.168.43.231:8080/api/vehicleRentalServices/booking/"+Bid;

                JSONObject object = new JSONObject();
                try {
                    object.put("flag", 1);

                }catch (Exception e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.PUT, Furl, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(ApproveBookDetailsActivity.this, "Booking approved !!", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(ApproveBookDetailsActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ApproveBookDetailsActivity.this, "Network error."+error, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest1);
            }
        });
    }
}