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

/**
 * Actividad que permite a los usuarios registrarse como conductores o empleados.
 */
public class RegisterActivity extends AppCompatActivity {

    private Button mBack, mRegister;
    private EditText mCompanyID, mName, mPassword, mEmail, mLocation;
    private RadioButton mDriver, mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtener referencias a los elementos de la interfaz de usuario
        mBack = findViewById(R.id.btnBackRegister);
        mRegister = findViewById(R.id.btnRegisterUser);
        mCompanyID = findViewById(R.id.register_companyID);
        mName = findViewById(R.id.register_name);
        mPassword = findViewById(R.id.register_password);
        mEmail = findViewById(R.id.register_email);
        mDriver = findViewById(R.id.register_driver);
        mEmployee = findViewById(R.id.register_employee);

        // Configurar el evento onClick para el botón de retroceso
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad principal
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        // Configurar el evento onClick para el botón de registro
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFormFields();
            }
        });
    }

    /**
     * Procesa los campos del formulario y realiza la acción correspondiente
     * (registro de conductor o redirección a la selección de ubicación para el empleado).
     */
    public void processFormFields(){
        if(!validadeCompanyID() || !validateName() || !validateEmail() || !validatePassword() || !validateRole()){
            return;
        }
        if (mDriver.isChecked()){
            // Si el usuario selecciona "conductor", registra al conductor
            Driver driver = new Driver();
            driver.setCompanyID(mCompanyID.getText().toString());
            driver.setName(mName.getText().toString());
            driver.setEmail(mEmail.getText().toString());
            driver.setPassword(mPassword.getText().toString());
            driver.setRating(0.0);
            driver.setRides(0);
            registerDriverOnServer(driver);

        } else {
            // Si el usuario selecciona "empleado", redirige a la selección de ubicación para el empleado
            Bundle bundle = new Bundle();
            bundle.putString("companyID", mCompanyID.getText().toString());
            bundle.putString("name", mName.getText().toString());
            bundle.putString("email", mEmail.getText().toString());
            bundle.putString("password", mPassword.getText().toString());
            bundle.putString("role", "employee");

            Intent intent = new Intent(RegisterActivity.this, ChooseLocationActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Valida el campo de identificación de la empresa.
     * @return true si el campo es válido, false si no lo es.
     */
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

    /**
     * Valida el campo de nombre.
     * @return true si el campo es válido, false si no lo es.
     */
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

    /**
     * Valida el campo de correo electrónico.
     * @return true si el campo es válido, false si no lo es.
     */
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

    /**
     * Valida el campo de contraseña.
     * @return true si el campo es válido, false si no lo es.
     */
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

    /**
     * Valida la selección de rol (conductor o empleado).
     * @return true si se ha seleccionado un rol, false si no se ha seleccionado ninguno.
     */
    public boolean validateRole(){
        return mDriver.isChecked() || mEmployee.isChecked();
    }

    /**
     * Registra al conductor en el servidor.
     * @param driver Objeto Driver que contiene la información del conductor.
     */
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
                    // Registro exitoso, muestra un mensaje de éxito
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Redirige a la actividad principal
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    // Manejo de errores durante el registro
                    if (response.code() == 400) {
                        Toast.makeText(RegisterActivity.this, "User with the same email or CompanyID already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejo de errores de red
                Toast.makeText(RegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                String error = t.getMessage();
                Log.e("Error", error);
                System.out.println(error);
            }
        });
    }
}
