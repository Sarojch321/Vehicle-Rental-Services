package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NewPasswordActivity extends AppCompatActivity {

    ImageView closeEyeForPass, closeEyeForConformPass;
    boolean passVisible = false;
    EditText pass, conformPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        closeEyeForPass = findViewById(R.id.closeEyeForPass);
        closeEyeForConformPass = findViewById(R.id.closeEyeForOnformPass);
        pass = findViewById(R.id.Pass);
        conformPass = findViewById(R.id.conformPass);

        closeEyeForPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passVisible = !passVisible;
                if(passVisible){
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEyeForPass.setImageResource(R.drawable.baseline_visibility_24);
                }else{
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEyeForPass.setImageResource(R.drawable.baseline_visibility_off_24);
                }

            }
        });

        closeEyeForConformPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passVisible = !passVisible;
                if(passVisible){
                    conformPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEyeForConformPass.setImageResource(R.drawable.baseline_visibility_24);
                }else{
                    conformPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEyeForConformPass.setImageResource(R.drawable.baseline_visibility_off_24);
                }

            }
        });
    }
}