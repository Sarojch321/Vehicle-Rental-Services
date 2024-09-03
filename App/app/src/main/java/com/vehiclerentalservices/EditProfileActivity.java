package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ImageView Uphoto, LicensePhoto;
    TextView topic, choosePhotoBtn, Udob, Ustatus, Urole;
    EditText Uname, Uemail, Uaddress, Uphone;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Edit Profile");

        Uphoto = findViewById(R.id.userPhoto);
        choosePhotoBtn = findViewById(R.id.choosePhoto);
        Uname = findViewById(R.id.editTxtName);
        Uemail = findViewById(R.id.editTxtEmail);
        Uaddress= findViewById(R.id.editTxtAddress);
        Uphone = findViewById(R.id.editTxtPhone);
        Udob = findViewById(R.id.editTxtDob);
        Ustatus = findViewById(R.id.editTxtStatus);
        Urole = findViewById(R.id.editTxtRole);
        LicensePhoto = findViewById(R.id.licensePhoto);
        saveBtn = findViewById(R.id.saveEditBtn);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String mobile = intent.getStringExtra("mobile");
        String  email = intent.getStringExtra("email");
        String dob = intent.getStringExtra("dob");
        String role = intent.getStringExtra("role");
        int status = intent.getIntExtra("status", 0);

        Uname.setText(name);
        Uemail.setText(email);
        Uaddress.setText(address);
        Uphone.setText(mobile);
        Udob.setText(dob);
        Urole.setText(role);
        if (status == 0){
            Ustatus.setText("Unverified");
        }
        if (status == 1){
            Ustatus.setText("KYC Pending");
        }
        if (status == 2){
            Ustatus.setText("Verified");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String nameU = Uname.getText().toString();
               String addressU = Uaddress.getText().toString();
               String emailU = Uemail.getText().toString();
               String mobileU = Uphone.getText().toString();
               String dobU = Udob.getText().toString();
               String statusU = Ustatus.getText().toString();
               String roleU = Urole.getText().toString();
               int statusofUser = 0;
               if (statusU.equals("Unverified")){
                   statusofUser = 0;
               }
               if (statusU.equals("KYC Pending")){
                   statusofUser = 1;
               }
                if (statusU.equals("Verified")){
                    statusofUser = 2;
                }
                String photo = "photo.jpg";
                String lisence = "license.jpg";

                SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
                int Userid = pref.getInt("userId", 0);

                String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+Userid;

                JSONObject user = new JSONObject();
                try {
                    user.put("name", nameU);
                    user.put("address", addressU);
                    user.put("email", emailU);
                    user.put("mobile", mobileU);
                    user.put("dob", dobU);
                    user.put("status", statusofUser);
                    user.put("photo", photo);
                    user.put("licensephoto", lisence);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.PUT, url, user,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(EditProfileActivity.this, "save successfully !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfileActivity.this, "failed to update your Profile. "+error, Toast.LENGTH_SHORT).show();
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