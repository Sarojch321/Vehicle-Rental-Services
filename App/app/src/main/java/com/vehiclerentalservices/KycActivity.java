package com.vehiclerentalservices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KycActivity extends AppCompatActivity {
    TextView topic, tvDob, etDob, chooseUPhoto, chooseLisencePhoto;
    ImageView uPhoto, LisencePhoto;
    EditText Uname, Uaddress, Uphone;
    Button kycBtn;
    String DateOfBirth, UserName, UserAddress, UserMobile, email, photo, lisence, encodeStringForPhoto, encodeStringForLicense;
    int status;
    Bitmap bitmap;
    private static final int PICK_IMAGE_REQUEST1 = 1;
    private static final int PICK_IMAGE_REQUEST2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Fill KYC");

        etDob = findViewById(R.id.Dob);
        tvDob= findViewById(R.id.selectDob);
        uPhoto = findViewById(R.id.userPhoto);
        chooseUPhoto = findViewById(R.id.choosePhoto);
        Uname = findViewById(R.id.editTxtName);
        Uaddress = findViewById(R.id.editTxtAddress);
        Uphone = findViewById(R.id.editTxtPhone);
        LisencePhoto = findViewById(R.id.licensePhoto);
        chooseLisencePhoto = findViewById(R.id.chooseLicensePhoto);
        kycBtn = findViewById(R.id.SubmitKycBtn);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);


        chooseUPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(KycActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent =new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Browse Image"),PICK_IMAGE_REQUEST1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
            }
        });

        chooseLisencePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(KycActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent =new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Browse Image"),PICK_IMAGE_REQUEST2);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
            }
        });

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        KycActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        etDob.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
        int Userid = pref.getInt("userId", 0);

        String Uurl = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+Userid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Uurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            DateOfBirth = response.getString("dob");
                            UserName = response.getString("name");
                            UserAddress = response.getString("address");
                            UserMobile = response.getString("mobile");
                            email = response.getString("email");

                            Uname.setText(UserName);
                            Uaddress.setText(UserAddress);
                            Uphone.setText(UserMobile);
                            etDob.setText(DateOfBirth);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        kycBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+Userid;
                status = 1;
                String name = Uname.getText().toString();
                String address = Uaddress.getText().toString();
                String dateOfB = etDob.getText().toString();
                String phone = Uphone.getText().toString();
                photo = "photo.jpg";
                lisence = "license.jpg";
                JSONObject user = new JSONObject();
                try {
                    user.put("name", name);
                    user.put("address", address);
                    user.put("email", email);
                    user.put("mobile", phone);
                    user.put("dob", dateOfB);
                    user.put("status", status);
                    user.put("photo", photo);
                    user.put("licensephoto", lisence);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.PUT, url, user,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(KycActivity.this, "KYC request successful !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KycActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KycActivity.this, "failed For KYC request. "+error, Toast.LENGTH_SHORT).show();
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
               /* String urlForUPhoto = "http://192.168.43.231:8080/api/vehicleRentalServices/user/userimage/"+Userid;

                JSONObject userPhoto = new JSONObject();
                try {
                    userPhoto.put("image", encodeStringForPhoto);
                }catch (Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, urlForUPhoto, userPhoto,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest2);
                String urlForLPhoto = "http://192.168.43.231:8080/api/vehicleRentalServices/user/lisenceimage/"+Userid;

                JSONObject userLPhoto = new JSONObject();
                try {
                    userPhoto.put("image", encodeStringForLicense);
                }catch (Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, urlForLPhoto, userLPhoto,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest3);*/
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                if (requestCode == PICK_IMAGE_REQUEST1){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    uPhoto.setImageBitmap(bitmap);
                    encodeBitmaImageForUphoto(bitmap);
                }else if (requestCode == PICK_IMAGE_REQUEST2) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    LisencePhoto.setImageBitmap(bitmap);
                    encodeBitmaImageForLicense(bitmap);
                }
            }catch (Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmaImageForLicense(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofImage = byteArrayOutputStream.toByteArray();
        encodeStringForLicense = android.util.Base64.encodeToString(bytesofImage, Base64.DEFAULT);
    }

    private void encodeBitmaImageForUphoto(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofImage = byteArrayOutputStream.toByteArray();
        encodeStringForPhoto = android.util.Base64.encodeToString(bytesofImage, Base64.DEFAULT);
    }

}