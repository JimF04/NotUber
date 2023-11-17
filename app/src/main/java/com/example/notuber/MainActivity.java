package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Actividad principal que permite a los usuarios seleccionar su rol (conductor o empleado)
 * o registrarse en la aplicación.
 */
public class MainActivity extends AppCompatActivity {
    private Button mDriver, mEmployee;
    private TextView registerLabel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los elementos de la interfaz de usuario
        mDriver = findViewById(R.id.btnDriver);
        mEmployee = findViewById(R.id.btnEmployee);
        registerLabel = findViewById(R.id.registerLabel);

        // Configurar el evento onClick para el botón del conductor
        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio de sesión del conductor
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        // Configurar el evento onClick para el botón del empleado
        mEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio de sesión del empleado
                Intent intent = new Intent(MainActivity.this, EmployeeLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        // Configurar el evento onClick para la etiqueta de registro
        registerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de registro
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
