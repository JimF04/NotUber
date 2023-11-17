package com.example.notuber.ui.Driver.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.ApiService;
import com.example.notuber.Model.NodeMarker;
import com.example.notuber.Model.NodesResponse;
import com.example.notuber.R;
import com.example.notuber.VariablesGlobales;
import com.example.notuber.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment_Driver extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;

    private MapView mapView;

    private List<Marker> markers = new ArrayList<>();
    private List<LatLng> journeyPath = new ArrayList<>();

    private Button mStart;
    private Polyline journeyPolyline;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel_Driver homeViewModel =
                new ViewModelProvider(this).get(MapViewModel_Driver.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = (MapView) root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        mStart = (Button) root.findViewById(R.id.btnStart);
//        mStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startJourney();
//            }
//        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ocultar nombres de lugares, carreteras, etc.
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Aplicar un estilo personalizado para ocultar otros elementos
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

        // Configurar posición inicial y zoom
        LatLng initialLocation = new LatLng(9.85339295368408, -83.90958197696368);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 14));

        getNodesFromApi();

    }

    private void getNodesFromApi() {
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

                    Bundle bundle = getArguments();
                    String driverLocation = bundle.getString("driverLocation", "default_value");
                    setDriverLocationMarker(driverLocation);

                    ArrayList lista_nodos = new ArrayList();

                    LatLng origen = new LatLng(9.838267, -83.902797);
                    LatLng destino = new LatLng(9.846535, -83.903945);
                    LatLng otro = new LatLng(9.854979,  -83.90887);


                    lista_nodos.add(origen);
                    lista_nodos.add(destino);
                    lista_nodos.add(otro);

                    drawLinesBetweenCoordinates(lista_nodos);

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

            Marker marker = mMap.addMarker(markerOptions);
            markers.add(marker);
        }
    }

    private void setDriverLocationMarker(String driverLocation) {
        for (Marker marker : markers) {
            if (driverLocation.equals(marker.getTitle())) {
                // Crear un nuevo marcador en la misma posición
                MarkerOptions newMarkerOptions = new MarkerOptions()
                        .position(marker.getPosition())
                        .title(marker.getTitle())
                        .zIndex(1.0f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon));

                // Agregar el nuevo marcador al mapa
                Marker newMarker = mMap.addMarker(newMarkerOptions);

                // Añadir el nuevo marcador a la lista de marcadores
                markers.add(newMarker);

                break; // Termina el bucle después de encontrar el marcador original
            }
        }
    }







    private void drawLinesBetweenCoordinates(List<LatLng> coordinates) {
        // Borra la polilínea anterior si existe
        if (journeyPolyline != null) {
            journeyPolyline.remove();
        }

        // Configura las opciones de la polilínea
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(coordinates)
                .width(5)  // Ancho de la línea en píxeles
                .color(getResources().getColor(android.R.color.holo_red_dark)); // Color rojo oscuro de Android

        // Dibuja la polilínea en el mapa
        journeyPolyline = mMap.addPolyline(polylineOptions);

        // Añade un marcador con el ícono solo en la última posición
        if (!coordinates.isEmpty()) {
            LatLng lastPosition = coordinates.get(coordinates.size() - 1);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(lastPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hombre_icon))
                    .anchor(0.5f, 0.5f); // Ajusta la posición del ancla del icono

            mMap.addMarker(markerOptions);
        }
    }





}
