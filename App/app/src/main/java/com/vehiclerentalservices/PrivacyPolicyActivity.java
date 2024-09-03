package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Privacy Policy");
    }
}