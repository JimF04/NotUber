package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.notuber.Model.Driver;
import com.example.notuber.Model.Employee;
import com.example.notuber.Model.NodeMarker;
import com.example.notuber.Model.NodesResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton mZoomIn, mZoomOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapChooseLocation);
        mapFragment.getMapAsync(this);

        mZoomIn = (ImageButton) findViewById(R.id.btnZoomIn);
        mZoomOut = (ImageButton) findViewById(R.id.btnZoomOut);

        mZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        mZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ocultar nombres de lugares, carreteras, etc.
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Aplicar un estilo personalizado para ocultar otros elementos
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        // Configurar posición inicial y zoom
        LatLng initialLocation = new LatLng(9.85339295368408, -83.90958197696368);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 14));

        getNodesFromApi();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker){

                if (marker.getTitle().equals("Empresa")){
                    Toast.makeText(ChooseLocationActivity.this, "You can't choose this location", Toast.LENGTH_SHORT).show();
                    return true;
                } else {

                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String companyID = bundle.getString("companyID");
                        String name = bundle.getString("name");
                        String email = bundle.getString("email");
                        String password = bundle.getString("password");
                        String nodeName = marker.getTitle();
                        String role = bundle.getString("role");

                        if (Objects.equals(role, "employee")) {
                            Employee employee = new Employee();
                            employee.setCompanyID(companyID);
                            employee.setName(name);
                            employee.setEmail(email);
                            employee.setPassword(password);
                            employee.setLocation(nodeName);
                            employee.setRating(0.0);

                            registerEmployeeOnServer(employee);
                        } else if (Objects.equals(role, "driver")) {
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("driverLocation", nodeName);

                            Intent intent2 = new Intent(ChooseLocationActivity.this, DriverActivity.class);
                            intent2.putExtras(bundle2);
                            startActivity(intent2);
                            finish();
                            return true;

                        }
                    }
                }
                return true;
            }
        });
    }

    public void getNodesFromApi() {
        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<NodesResponse> call = apiService.getNodes();
        call.enqueue(new Callback<NodesResponse>() {
            @Override
            public void onResponse(Call<NodesResponse> call, Response<NodesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Imprimir datos para verificar
                    for (NodeMarker nodeMarker : response.body().getNodes()) {
                        Log.d("Node", "Name: " + nodeMarker.getName() + ", Position: " + nodeMarker.getPosition());
                    }

                    // Agregar marcadores al mapa
                    addMarkersToMap(response.body().getNodes());
                }
            }

            @Override
            public void onFailure(Call<NodesResponse> call, Throwable t) {
                // Manejar el fallo
                Log.e("Node", "Error al obtener nodos desde la API", t);
            }
        });
    }

    private void addMarkersToMap(List<NodeMarker> nodeMarkers) {
        for (NodeMarker nodeMarker : nodeMarkers) {
            MarkerOptions markerOptions;
            LatLng position = new LatLng(nodeMarker.getLatitude(), nodeMarker.getLongitude());
            // Verificar si el nodo es "Empresa" y asignar un color diferente
            if ("Empresa".equals(nodeMarker.getName())) {
                markerOptions = new MarkerOptions()
                        .position(position)
                        .title(nodeMarker.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            } else {
                markerOptions = new MarkerOptions()
                        .position(position)
                        .title(nodeMarker.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            mMap.addMarker(markerOptions);
        }
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
                    Toast.makeText(ChooseLocationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChooseLocationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(ChooseLocationActivity.this, "User with the same email or CompanyID already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChooseLocationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ChooseLocationActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                String error = t.getMessage();
                Log.e("Error", error);
                System.out.println(error);
            }
        });

    }

}