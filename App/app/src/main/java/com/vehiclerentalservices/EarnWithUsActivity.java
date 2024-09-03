package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EarnWithUsActivity extends AppCompatActivity {

    Button yesBtn;
    TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_with_us);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Become Owner");
        yesBtn = findViewById(R.id.yesBtn);

        SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
        int id = pref.getInt("userId", 0);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/role/"+id;

                JSONObject obj = new JSONObject();
                try {
                    obj.put("role", "OWNER");
                }catch (Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences preferences = getSharedPreferences("doubleRole", MODE_PRIVATE);
                                SharedPreferences.Editor edit = preferences.edit();
                                edit.putBoolean("IsDouble", true);
                                edit.apply();
                                SharedPreferences pref = getSharedPreferences("UserRole", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("roles","OWNER");
                                editor.apply();
                                Toast.makeText(EarnWithUsActivity.this, "Successful !!", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(EarnWithUsActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EarnWithUsActivity.this, "Failed "+error, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }
}