package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button mBack, mRegister;
    private EditText mCompanyID, mName, mPassword, mEmail, mLocation;
    private RadioButton mDriver, mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBack = (Button) findViewById(R.id.btnBackRegister);
        mRegister = (Button) findViewById(R.id.btnRegisterUser);

        mCompanyID = (EditText) findViewById(R.id.register_companyID);
        mName = (EditText) findViewById(R.id.register_name);
        mPassword = (EditText) findViewById(R.id.register_password);
        mEmail = (EditText) findViewById(R.id.register_email);

        mDriver = (RadioButton) findViewById(R.id.register_driver);
        mEmployee = (RadioButton) findViewById(R.id.register_employee);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFormFields();
            }
        });
    }

    public void processFormFields(){
        if(!validadeCompanyID() || !validateName() || !validateEmail() || !validatePassword() || !validateRole()){
            return;
        }
        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
    }
    public boolean validadeCompanyID(){
        String companyIDInput = mCompanyID.getText().toString();
        if(companyIDInput.isEmpty()){
            mCompanyID.setError("Field can't be empty");
            return false;
        } else {
            mCompanyID.setError(null);
            return true;
        }
    }
    public boolean validateName(){
        String nameInput = mName.getText().toString();
        if(nameInput.isEmpty()){
            mName.setError("Field can't be empty");
            return false;
        } else {
            mName.setError(null);
            return true;
        }
    }
    public boolean validateEmail(){
        String emailInput = mEmail.getText().toString();
        if(emailInput.isEmpty()){
            mEmail.setError("Field can't be empty");
            return false;
        } else if (!emailInput.matches("[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,}")){
            mEmail.setError("Please enter a valid email address");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String passwordInput = mPassword.getText().toString();
        if(passwordInput.isEmpty()){
            mPassword.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() < 8){
            mPassword.setError("Password must be at least 8 characters long");
            return false;
        }else {
            mPassword.setError(null);
            return true;
        }
    }
    public boolean validateRole(){
        if(mDriver.isChecked() || mEmployee.isChecked()){
            return true;
        } else {
            return false;
        }
    }
}