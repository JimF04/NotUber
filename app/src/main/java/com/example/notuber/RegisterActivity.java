package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.notuber.Model.Driver;
import com.example.notuber.Model.Employee;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (mDriver.isChecked()){
            Driver driver = new Driver();
            driver.setCompanyID(mCompanyID.getText().toString());
            driver.setName(mName.getText().toString());
            driver.setEmail(mEmail.getText().toString());
            driver.setPassword(mPassword.getText().toString());
            driver.setRating(0.0);
            driver.setRides(0);
            registerDriverOnServer(driver);

        } else {

            Intent intent = new Intent(RegisterActivity.this, ChooseLocationActivity.class);
            startActivity(intent);
            finish();

            //Employee employee = new Employee();
            //employee.setCompanyID(mCompanyID.getText().toString());
            //employee.setName(mName.getText().toString());
            //employee.setEmail(mEmail.getText().toString());
            //employee.setPassword(mPassword.getText().toString());
            //employee.setLocation("Location1");
            //employee.setRating(0.0);
            //registerEmployeeOnServer(employee);
        }
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

    private void registerDriverOnServer(Driver driver) {
        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);


        Call<Void> call = apiService.registerDriver(driver);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(RegisterActivity.this, "User with the same email or CompanyID already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                String error = t.getMessage();
                Log.e("Error", error);
                System.out.println(error);
            }
        });

    }

    private void registerEmployeeOnServer(Employee employee) {
        String ipAddress = VariablesGlobales.localip;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);


        Call<Void> call = apiService.registerEmployee(employee);

        call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        if (response.code() == 400) {
                            Toast.makeText(RegisterActivity.this, "User with the same email or CompanyID already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                String error = t.getMessage();
                Log.e("Error", error);
                System.out.println(error);
            }
        });

    }
}