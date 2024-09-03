package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private RequestQueue requestQueue;

    Button loginbtn;
    TextView txtForget;
    Boolean passVisible = false;
    EditText uname, pass;
    TextView txtSignUp;

    ImageView closeEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = (Button) findViewById(R.id.lgnbtn);
        txtForget = (TextView) findViewById(R.id.txtForget);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        uname = findViewById(R.id.uName);
        pass = findViewById(R.id.Pass);
        closeEye = findViewById(R.id.closeEyeForPass);
        requestQueue = Volley.newRequestQueue(this);


        closeEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passVisible = !passVisible;
                if (passVisible) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEye.setImageResource(R.drawable.baseline_visibility_24);
                } else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEye.setImageResource(R.drawable.baseline_visibility_off_24);
                }

            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = uname.getText().toString();
                String password = pass.getText().toString();
                if (username.isEmpty()) {
                    if (password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Insert Username and Password !!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Insert Username !!", Toast.LENGTH_SHORT).show();
                    }
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Insert password !!", Toast.LENGTH_SHORT).show();

                } else {
                    loginData(username, password);
                }
            }
        });

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void loginData(String username, String password) {
        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/login";

        JSONObject login = new JSONObject();
        try {
            login.put("username", username);
            login.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, login,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SharedPreferences pref = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putBoolean("Logs", true);
                            edit.apply();
                            String token = response.getString("jwtToken");
                            long expirationTime = System.currentTimeMillis() + (5 * 24 * 60 * 60 * 1000L); // 5 days in milliseconds
                            SharedPreferencesUtil.saveToken(LoginActivity.this, token, expirationTime);
                            Toast.makeText(LoginActivity.this, "Login Successful !!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Toast.makeText(LoginActivity.this, "Login failed !!", Toast.LENGTH_SHORT).show();
                        } else {
                            String TAG = null;
                            Log.d(TAG, "onErrorResponse: " +error);
                            Toast.makeText(LoginActivity.this, "failed!!" +error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }
}