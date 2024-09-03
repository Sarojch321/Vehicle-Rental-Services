package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vehiclerentalservices.Jwt.JwtUtils;
import com.vehiclerentalservices.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextView topic, editBtn;
    TextView name, email, address, mobile, dob, status, role;
    ImageView photo, lisence;
    private RequestQueue requestQueue;
    int statusR;
    String nameR, addR, mobileR, emailR, photoR, dobR, lisenceR, passR, roleR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Profile");

        name = findViewById(R.id.editTxtName);
        email = findViewById(R.id.editTxtEmail);
        address = findViewById(R.id.editTxtAddress);
        mobile = findViewById(R.id.editTxtPhone);
        dob = findViewById(R.id.editTxtDob);
        status = findViewById(R.id.editTxtStatus);
        role = findViewById(R.id.editTxtRole);

        photo = findViewById(R.id.userPhoto);
        lisence = findViewById(R.id.licensePhoto);

        editBtn = findViewById(R.id.editProfileBtn);
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
        int id = pref.getInt("userId", 0);

        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nameR = response.getString("name");
                            addR = response.getString("address");
                            mobileR = response.getString("mobile");
                            emailR = response.getString("email");
                            dobR = response.getString("dob");
                            statusR = response.getInt("status");
                            roleR = response.getString("role");

                            //lisence and photo baki ba

                            name.setText(nameR);
                            address.setText(addR);
                            mobile.setText(mobileR);
                            email.setText(emailR);
                            dob.setText(dobR);
                            role.setText(roleR);
                            if (statusR == 0){
                                status.setText("Unverified");
                            }
                            if (statusR == 1){
                                status.setText("Unverified");
                            }
                            if (statusR == 2){
                                status.setText("verified");
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Connection error !!" + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("name", nameR);
                intent.putExtra("address", addR);
                intent.putExtra("mobile", mobileR);
                intent.putExtra("email", emailR);
                intent.putExtra("dob", dobR);
                intent.putExtra("role", roleR);
                intent.putExtra("status", statusR);
                startActivity(intent);
            }
        });


    }
}