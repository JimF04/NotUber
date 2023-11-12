package com.example.notuber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

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
    }
}