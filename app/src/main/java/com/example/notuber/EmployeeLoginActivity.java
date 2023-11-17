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

public class EmployeeLoginActivity extends AppCompatActivity {

    private Button mBackEmployee, mLogInEmployee;
    private EditText E_Email, E_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        mBackEmployee = (Button) findViewById(R.id.btnBackEmployee);
        mLogInEmployee = (Button) findViewById(R.id.btnLogInEmployee);
        E_Email = (EditText) findViewById(R.id.emailEmployee);
        E_Password = (EditText) findViewById(R.id.passwordEmployee);

        mBackEmployee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mLogInEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateEmployee();
            }
        });

    }
    public void authenticateEmployee() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }
        String email = E_Email.getText().toString();
        String password = E_Password.getText().toString();
        LogIn logIn = new LogIn(email, password);

        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.logInEmployee(logIn);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Inicio de sesión exitoso, realiza las acciones necesarias.
                    Toast.makeText(EmployeeLoginActivity.this, "Employee logged in successfully", Toast.LENGTH_SHORT).show();

                    VariablesGlobales.currentuser = email;

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);

                    Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                } else {
                    if (response.code() == 401) {
                        if (response.message().equals("User not found")) {
                            Toast.makeText(EmployeeLoginActivity.this, "User not found. Please register.", Toast.LENGTH_SHORT).show();
                        } else if (response.message().equals("Incorrect role for employee")) {
                            Toast.makeText(EmployeeLoginActivity.this, "You are not an employee", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmployeeLoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error de inicio de sesión, muestra un mensaje de error.
                        Toast.makeText(EmployeeLoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejo de errores de red.
                Toast.makeText(EmployeeLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean validateEmail(){
        String emailInput = E_Email.getText().toString();
        if(emailInput.isEmpty()){
            E_Email.setError("Field can't be empty");
            return false;
        } else if (!emailInput.matches("[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,}")){
            E_Email.setError("Please enter a valid email address");
            return false;
        } else {
            E_Email.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String passwordInput = E_Password.getText().toString();
        if(passwordInput.isEmpty()){
            E_Password.setError("Field can't be empty");
            return false;
        }else {
            E_Password.setError(null);
            return true;
        }
    }
}
