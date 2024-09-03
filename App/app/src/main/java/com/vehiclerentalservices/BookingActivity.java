package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vehiclerentalservices.Jwt.AuthenticatedRequest;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class BookingActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    TextView etPickDate, etPickTime, etDropDate, etDropTime;
    EditText pdistrict, pcity, pward, ptole, ddistrict, dcity, dward, dtole;
    CheckBox check;
    RadioGroup group;
    RadioButton Rbtn;
    TextView amount;
    TextView tvPickDate, tvPickTime, tvDropDate, tvDropTime;
    Button bookingBtn;
    DatePickerDialog.OnDateSetListener setListener;
    String Amount;
    int Vid;
    String Pdistrict, Pcity, Pward, Ptole, Ddistrict, Dcity, Dward, Dtole, PDate, PTime, DDate, DTime, driver, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        etPickDate = findViewById(R.id.txtPickDate);
        etPickTime = findViewById(R.id.txtPickTime);
        etDropDate = findViewById(R.id.txtDropDate);
        etDropTime = findViewById(R.id.txtDropTime);
        tvPickDate = findViewById(R.id.selectPickDate);
        tvPickTime = findViewById(R.id.selectPickTime);
        tvDropDate = findViewById(R.id.selectDropDate);
        tvDropTime = findViewById(R.id.selectDropTime);

        requestQueue = Volley.newRequestQueue(this);

        pdistrict = findViewById(R.id.txtpDistrict);
        pcity = findViewById(R.id.txtpCity);
        pward = findViewById(R.id.txtpWardNo);
        ptole = findViewById(R.id.txtpTole);
        ddistrict = findViewById(R.id.txtdDistrict);
        dcity = findViewById(R.id.txtdCity);
        dward = findViewById(R.id.txtdWardNo);
        dtole = findViewById(R.id.txtdTole);

        check = findViewById(R.id.checkBoxForSameLocation);

        group = findViewById(R.id.groupRadio);

        amount = findViewById(R.id.txtTotalAmount);

        bookingBtn = findViewById(R.id.placeBooking);

        Intent intent = getIntent();
        Vid = intent.getIntExtra("idv", -1);
        Log.e("ddd","id"+Vid);
        Amount = intent.getStringExtra("TotalAmount");

        check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                Pdistrict = pdistrict.getText().toString();
                Pcity = pcity.getText().toString();
                Pward = pward.getText().toString();
                Ptole = ptole.getText().toString();

                ddistrict.setText(Pdistrict);
                dcity.setText(Pcity);
                dward.setText(Pward);
                dtole.setText(Ptole);
            }else {
                ddistrict.setText("");
                dcity.setText("");
                dward.setText("");
                dtole.setText("");
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Rbtn = findViewById(checkedId);
            }
        });

        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);
        final int hours = calender.get(Calendar.HOUR_OF_DAY);
        final int min = calender.get(Calendar.MINUTE);

        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        etPickDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        tvPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat formate = new SimpleDateFormat("k:mm a");
                        String time = formate.format(c.getTime());
                        etPickTime.setText(time);
                    }
                }, hours, min, false);
                timePickerDialog.show();
            }
        });

        tvDropDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        etDropDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        tvDropTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat formate = new SimpleDateFormat("k:mm a");
                        String time = formate.format(c.getTime());
                        etDropTime.setText(time);
                    }
                }, hours, min, false);
                timePickerDialog.show();
            }
        });

        TextWatcher dateWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calculateDateDifference();
            }
        };

        etPickDate.addTextChangedListener(dateWatcher);
        etDropDate.addTextChangedListener(dateWatcher);


        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pdistrict = pdistrict.getText().toString();
                Pcity = pcity.getText().toString();
                Pward = pward.getText().toString();
                Ptole = ptole.getText().toString();

                Ddistrict = ddistrict.getText().toString();
                Dcity = dcity.getText().toString();
                Dward = dward.getText().toString();
                Dtole = dtole.getText().toString();

                PDate = etPickDate.getText().toString();
                PTime = etPickTime.getText().toString();

                DDate = etDropDate.getText().toString();
                DTime = etDropTime.getText().toString();
                driver = Rbtn.getText().toString();
                total =  amount.getText().toString();


                processData(Pdistrict, Pcity, Pward, Ptole, Ddistrict, Dcity, Dward, Dtole, PDate, PTime, DDate, DTime, driver, total, Vid);
            }
        });
    }

    private void calculateDateDifference() {
        String date1Str = etPickDate.getText().toString();
        String date2Str = etDropDate.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date1 = dateFormat.parse(date1Str);
            Date date2 = dateFormat.parse(date2Str);

            if (date1 != null && date2 != null) {
                long differenceInMillis = Math.abs(date2.getTime() - date1.getTime());
                long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);

                long TAmount = Long.parseLong(String.valueOf(amount));
                Long TotalAmount = differenceInDays*TAmount;
                amount.setText((int) differenceInDays);
            } else {
                amount.setText("unable to calculate amount");
            }
        } catch (ParseException e) {

        }
    }

    private void processData(String pdistrict, String pcity, String pward, String ptole, String ddistrict, String dcity, String dward, String dtole, String pDate, String pTime, String dDate, String dTime, String driver, String total, int vid) {
        SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
        int Uid = pref.getInt("userId", 0);
        String books = "http://192.168.43.231:8080/api/vehicleRentalServices/user/"+Uid+"/vehicle/"+vid+"/booking";
        String loc1 = "http://192.168.43.231:8080/api/vehicleRentalServices/handingloc";

        JSONObject locs1 = new JSONObject();
        try {
            locs1.put("district",pdistrict);
            locs1.put("city",pcity);
            locs1.put("ward",pward);
            locs1.put("tole",ptole);
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loc1, locs1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int id1 = 0;
                        try {
                            id1 = response.getInt("id");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        JSONObject locs2 = new JSONObject();
                        try {
                            locs2.put("district",ddistrict);
                            locs2.put("city",dcity);
                            locs2.put("ward",dward);
                            locs2.put("tole",dtole);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        int finalId = id1;
                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, loc1, locs2,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        int id2 = 0;
                                        try {
                                            id2 = response.getInt("id");
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        JSONObject book = new JSONObject();
                                        try {
                                            JSONObject locss1 = new JSONObject();
                                            locss1.put("id", finalId);

                                            JSONObject locss2 = new JSONObject();
                                            locss2.put("id", id2);

                                            book.put("dateFrom", PDate);
                                            book.put("dateTo", DDate);
                                            book.put("totalAmount", total);
                                            book.put("driverNeed", driver);
                                            book.put("flag", 0);
                                            book.put("pickupLocation",locss1);
                                            book.put("dropoffLocation",locss2);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, books, book,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Toast.makeText(BookingActivity.this, "Booking request Successful !!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(BookingActivity.this, "Booking Fail" +error, Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                Map<String, String> headers = new HashMap<>();
                                                headers.put("Content-Type", "application/json");
                                                return headers;
                                            }
                                        };
                                        requestQueue.add(jsonObjectRequest2);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(BookingActivity.this, "failed to add DropOff location." +error, Toast.LENGTH_SHORT).show();
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this, "failed to add pickUp location." +error, Toast.LENGTH_SHORT).show();

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
}