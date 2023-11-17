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

/**
 * Esta clase representa la actividad de inicio de sesión para los empleados.
 */
public class EmployeeLoginActivity extends AppCompatActivity {

    private Button mBackEmployee, mLogInEmployee;
    private EditText E_Email, E_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        // Inicialización de elementos de la interfaz de usuario
        mBackEmployee = (Button) findViewById(R.id.btnBackEmployee);
        mLogInEmployee = (Button) findViewById(R.id.btnLogInEmployee);
        E_Email = (EditText) findViewById(R.id.emailEmployee);
        E_Password = (EditText) findViewById(R.id.passwordEmployee);

        // Configuración del botón de retroceso
        mBackEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar de vuelta a la actividad principal al hacer clic en el botón de retroceso
                Intent intent = new Intent(EmployeeLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Configuración del botón de inicio de sesión
        mLogInEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Método para autenticar al empleado
                authenticateEmployee();
            }
        });
    }

    /**
     * Método para autenticar al empleado en el servidor.
     */
    public void authenticateEmployee() {
        // Validar la entrada del correo electrónico y la contraseña
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        // Obtener el correo electrónico y la contraseña ingresados
        String email = E_Email.getText().toString();
        String password = E_Password.getText().toString();

        // Crear un objeto LogIn con las credenciales
        LogIn logIn = new LogIn(email, password);

        // Obtener la dirección IP local desde las variables globales
        String ipAddress = VariablesGlobales.localip;

        // Configurar Retrofit para realizar la llamada a la API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Realizar la llamada para iniciar sesión del empleado
        Call<Void> call = apiService.logInEmployee(logIn);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Inicio de sesión exitoso, realizar las acciones necesarias
                    Toast.makeText(EmployeeLoginActivity.this, "Employee logged in successfully", Toast.LENGTH_SHORT).show();

                    // Establecer el usuario actual en las variables globales
                    VariablesGlobales.currentuser = email;

                    // Crear un paquete de datos para enviar a la siguiente actividad
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);

                    // Iniciar la actividad EmployeeActivity y pasar los datos
                    Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                } else {
                    if (response.code() == 401) {
                        // Manejar diferentes errores de inicio de sesión
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
                // Manejo de errores de red
                Toast.makeText(EmployeeLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para validar el formato del correo electrónico.
     *
     * @return true si el formato es válido, false de lo contrario.
     */
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

    /**
     * Método para validar la no vacuidad de la contraseña.
     *
     * @return true si la contraseña es válida, false de lo contrario.
     */
    public boolean validatePassword(){
        String passwordInput = E_Password.getText().toString();
        if(passwordInput.isEmpty()){
            E_Password.setError("Field can't be empty");
            return false;
        } else {
            E_Password.setError(null);
            return true;
        }
    }
}
