package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DriverLoginActivity extends AppCompatActivity {
    private Button mBackDriver;
    private EditText D_Email, D_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mBackDriver = (Button) findViewById(R.id.btnBackDriver);
        D_Email = (EditText) findViewById(R.id.emailDriver);
        D_Password = (EditText) findViewById(R.id.passwordDriver);

        mBackDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}