package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    private RequestQueue requestQueue;

    ImageView closeEyeForPass, closeEyeForConformPass;
    boolean passVisible = false;
    boolean checkBox = false;
    TextView terms, policy, directToLogin;
    EditText pass, conformPass, uname, names, add, phn;
    Button signUpBtn;
    CheckBox acceptTerm;
    String licensephoto = null, photo = null, dob = null, role = "CUSTOMER";
    int status = 0, activestatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        closeEyeForPass = findViewById(R.id.closeEyeForPass);
        closeEyeForConformPass = findViewById(R.id.closeEyeForOnformPass);
        pass = findViewById(R.id.Pass);
        conformPass = findViewById(R.id.conformPass);
        uname = findViewById(R.id.uName);
        terms = findViewById(R.id.txt2);
        policy = findViewById(R.id.txt3);
        signUpBtn = findViewById(R.id.signUpbtn);
        directToLogin = findViewById(R.id.plzLogin);
        acceptTerm = findViewById(R.id.checkBox);
        names = findViewById(R.id.Name);
        add = findViewById(R.id.Address);
        phn = findViewById(R.id.PhoneNo);
        requestQueue = Volley.newRequestQueue(this);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = uname.getText().toString();
                String password = pass.getText().toString();
                String confirmPassword = conformPass.getText().toString();
                String name = names.getText().toString();
                String address = add.getText().toString();
                String mobile = phn.getText().toString();
                if (acceptTerm.isChecked()) {
                    checkBox = true;
                }
                if (Username.isEmpty() && password.isEmpty() && confirmPassword.isEmpty() && !checkBox) {
                    Toast.makeText(SignUpActivity.this, "Insert all field !!", Toast.LENGTH_SHORT).show();
                } else {
                    if (Username.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Insert your email !!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.isEmpty()) {
                            Toast.makeText(SignUpActivity.this, "Insert password !!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (confirmPassword.isEmpty()) {
                                Toast.makeText(SignUpActivity.this, "Insert confirm password !!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!checkBox) {
                                    Toast.makeText(SignUpActivity.this, "Please accept our terms and privacy policy !!", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (password.equals(confirmPassword)) {
                                        processData(name, address, mobile, Username, password, licensephoto, photo, dob, status, activestatus, role);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Insert same password and conform password !!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        closeEyeForPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passVisible = !passVisible;
                if (passVisible) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEyeForPass.setImageResource(R.drawable.baseline_visibility_24);
                } else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEyeForPass.setImageResource(R.drawable.baseline_visibility_off_24);
                }

            }
        });

        closeEyeForConformPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passVisible = !passVisible;
                if (passVisible) {
                    conformPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEyeForConformPass.setImageResource(R.drawable.baseline_visibility_24);
                } else {
                    conformPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEyeForConformPass.setImageResource(R.drawable.baseline_visibility_off_24);
                }

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        directToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processData(String name, String address, String mobile, String username, String password, String licensephoto, String photo, String dob, int status, int activestatus, String role) {
        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/signup";

        JSONObject sign = new JSONObject();

        try {
            sign.put("name", name);
            sign.put("address", address);
            sign.put("mobile", mobile);
            sign.put("email", username);
            sign.put("password", password);
            sign.put("licensephoto", licensephoto);
            sign.put("photo", photo);
            sign.put("status", status);
            sign.put("activestatus", activestatus);
            sign.put("role", role);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, sign,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if (message.equals("Exist")){
                                Toast.makeText(SignUpActivity.this, "Email is already exit !!", Toast.LENGTH_SHORT).show();
                            }
                            if (message.equals("Done")){
                                Toast.makeText(SignUpActivity.this, "Sign Up Successful !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Toast.makeText(SignUpActivity.this, "failed !!"+ error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "2failed!!"+ error, Toast.LENGTH_SHORT).show();
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