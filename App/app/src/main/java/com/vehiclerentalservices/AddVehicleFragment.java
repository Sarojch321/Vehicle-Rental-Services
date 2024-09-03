package com.vehiclerentalservices;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.vehiclerentalservices.Jwt.AuthenticatedRequest;
import com.vehiclerentalservices.Jwt.JwtUtils;
import com.vehiclerentalservices.adapter.BrowseVPhoto;
import com.vehiclerentalservices.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddVehicleFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST1 = 2;
    private static final int PICK_IMAGE_REQUEST2 = 3;
    private static final int REQUEST_CODE_PICK_IMAGES = 1;
    String[] item = {"Bike", "Car"};
    AutoCompleteTextView type;
    ArrayAdapter<String> arrayAdapter;
    EditText title, amount, district, city, ward, tole;
    int uid;
    private RequestQueue requestQueue;
    RadioGroup group;
    RadioButton select;
    ImageView bluebook, insurance;
    TextView bluebookBtn, insuranceBtn, vehicleBtn;
    RecyclerView recyclerView;
    Button save;

    public AddVehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        recyclerView = view.findViewById(R.id.imageRecycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        type = view.findViewById(R.id.txtType);
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.type_list, item);

        type.setAdapter(arrayAdapter);

        requestQueue = Volley.newRequestQueue(getContext());

        title = view.findViewById(R.id.txtTitle);
        amount = view.findViewById(R.id.txtAmount);
        district = view.findViewById(R.id.txtpDistrict);
        city = view.findViewById(R.id.txtpCity);
        ward = view.findViewById(R.id.txtpWardNo);
        tole = view.findViewById(R.id.txtpTole);
        group = view.findViewById(R.id.groupRadio);
        save = view.findViewById(R.id.saveVehicle);

        bluebookBtn = view.findViewById(R.id.chooseBlueBookPhoto);
        insuranceBtn = view.findViewById(R.id.chooseInsurancePhoto);

        bluebook = view.findViewById(R.id.blueBookPhoto);
        insurance = view.findViewById(R.id.insurancePhoto);
        vehicleBtn = view.findViewById(R.id.chooseVehiclePhoto);

        String jwtToken = SharedPreferencesUtil.getToken(getContext());

        String username = JwtUtils.extractFromToken(jwtToken);

        String urls = "http://192.168.43.231:8080/api/vehicleRentalServices/user/email/" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        User[] users = gson.fromJson(response, User[].class);
                        for (User user : users) {
                            uid = user.getId();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Error !!", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);


        bluebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST1);
            }
        });

        insuranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST2);
            }
        });

        vehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), REQUEST_CODE_PICK_IMAGES);
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                select = view.findViewById(checkedId);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Vtitle, Vtype, Vdistrict, Vcity, Vward, Vtole, dyes, Vamount;
                Vtitle = title.getText().toString();
                Vtype = type.getText().toString();
                Vdistrict = district.getText().toString();
                Vcity = city.getText().toString();
                Vward = ward.getText().toString();
                Vtole = tole.getText().toString();
                dyes = select.getText().toString();
                Vamount = amount.getText().toString();

                processData(Vtitle, Vtype, Vdistrict, Vcity, Vward, Vtole, dyes, Vamount, uid);

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Display the selected image in an ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                if (requestCode == PICK_IMAGE_REQUEST1) {
                    bluebook.setImageBitmap(bitmap);
                } else if (requestCode == PICK_IMAGE_REQUEST2) {
                    insurance.setImageBitmap(bitmap);
                } else if (requestCode == REQUEST_CODE_PICK_IMAGES) {
                    ArrayList<Uri> imageUris = new ArrayList<>();
                    if (data.getClipData() != null) {
                        // Handle multiple images
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);
                        }
                    } else if (data.getData() != null) {
                        // Handle single image
                        Uri imageUri = data.getData();
                        imageUris.add(imageUri);
                    }
                    displaySelectedImages(imageUris);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displaySelectedImages(ArrayList<Uri> imageUris) {
        List<Bitmap> imageBitmaps = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            try (InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageBitmaps.add(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new BrowseVPhoto(imageBitmaps));
        }
    }

    private void processData(String vtitle, String vtype, String vdistrict, String vcity, String vward, String vtole, String dyes, String vamount, int userid) {
        String loc = "http://192.168.43.231:8080/api/vehicleRentalServices/handingloc";
        String url = "http://192.168.43.231:8080/api/vehicleRentalServices/create/vehicle";
        String Iphoto = "insurance.jpg";
        String Bphoto = "bluebook.jpg";

        JSONObject locs = new JSONObject();
        try {
            locs.put("district", vdistrict);
            locs.put("city", vcity);
            locs.put("ward", vward);
            locs.put("tole", vtole);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, loc, locs,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int id;
                        try {
                            id = response.getInt("id");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        JSONObject vehicle = new JSONObject();
                        try {
                            JSONObject user = new JSONObject();
                            user.put("id", userid);

                            JSONObject locss = new JSONObject();
                            locss.put("id", id);

                            vehicle.put("name", vtitle);
                            vehicle.put("type", vtype);
                            vehicle.put("amount", vamount);
                            vehicle.put("status", 0);
                            vehicle.put("bluebookphoto", Bphoto);
                            vehicle.put("insurancephoto", Iphoto);
                            vehicle.put("driverstatus", dyes);
                            vehicle.put("user", user);
                            vehicle.put("location", locss);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, vehicle,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(getContext(), "Added !!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Failed "+error, Toast.LENGTH_SHORT).show();
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail to add pickup address " + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(request1);
    }
}