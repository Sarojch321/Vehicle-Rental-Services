package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SupportHelpActivity extends AppCompatActivity {

    TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_help);
        topic = findViewById(R.id.txtTopic);
        topic.setText("Support and Help");
    }
}