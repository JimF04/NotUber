package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mDriver, mEmployee;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.btnDriver);
        mEmployee = (Button) findViewById(R.id.btnEmployee);

        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}