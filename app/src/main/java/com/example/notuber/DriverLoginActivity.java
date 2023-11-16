package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notuber.Model.LogIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverLoginActivity extends AppCompatActivity {
    private Button mBackDriver, mLogInDriver;
    private EditText D_Email, D_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mBackDriver = (Button) findViewById(R.id.btnBackDriver);
        mLogInDriver = (Button) findViewById(R.id.btnLogInDriver);
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
        mLogInDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateDriver();
            }
        });
    }
    public void authenticateDriver() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }
        String email = D_Email.getText().toString();
        String password = D_Password.getText().toString();
        LogIn logIn = new LogIn(email, password);

        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.logInDriver(logIn);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Inicio de sesión exitoso, realiza las acciones necesarias.
                    Toast.makeText(DriverLoginActivity.this, "Driver logged in successfully", Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("role", "driver");

                    Intent intent = new Intent(DriverLoginActivity.this, ChooseLocationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                } else {
                    if (response.code() == 401) {
                        if (response.message().equals("User not found")) {
                            Toast.makeText(DriverLoginActivity.this, "User not found. Please register.", Toast.LENGTH_SHORT).show();
                        } else if (response.message().equals("Incorrect role for driver")) {
                            Toast.makeText(DriverLoginActivity.this, "You are not a driver", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DriverLoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error de inicio de sesión, muestra un mensaje de error.
                        Toast.makeText(DriverLoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejo de errores de red.
                Toast.makeText(DriverLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean validateEmail(){
        String emailInput = D_Email.getText().toString();
        if(emailInput.isEmpty()){
            D_Email.setError("Field can't be empty");
            return false;
        } else if (!emailInput.matches("[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,}")){
            D_Email.setError("Please enter a valid email address");
            return false;
        } else {
            D_Email.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String passwordInput = D_Password.getText().toString();
        if(passwordInput.isEmpty()){
            D_Password.setError("Field can't be empty");
            return false;
        }else {
            D_Password.setError(null);
            return true;
        }
    }
}