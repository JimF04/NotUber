package com.example.notuber.ui.Driver.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.ApiService;
import com.example.notuber.DriverLoginActivity;
import com.example.notuber.MainActivity;
import com.example.notuber.Model.Node;
import com.example.notuber.Model.NodeMarker;
import com.example.notuber.Model.NodesResponse;
import com.example.notuber.R;
import com.example.notuber.RatingActivity;
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

/**
 * Fragmento que representa la sección del mapa en la interfaz de usuario del conductor.
 */
public class MapFragment_Driver extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;

    private MapView mapView;

    private List<Marker> markers = new ArrayList<>();
    private List<LatLng> journeyPath = new ArrayList<>();

    private Button mStart;
    private Polyline journeyPolyline;

    private Handler handler = new Handler();

    private Marker carMarker;





    /**
     * Se llama para crear la vista del fragmento.
     *
     * @param inflater           El LayoutInflater utilizado para inflar la vista.
     * @param container          El contenedor en el que se debe insertar la vista.
     * @param savedInstanceState La instancia previamente guardada del fragmento.
     * @return La vista del fragmento.
     */
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

    /**
     * Se llama cuando la vista del fragmento está a punto de ser destruida.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Se llama cuando el mapa está listo para ser utilizado.
     *
     * @param googleMap El objeto GoogleMap que representa el mapa.
     */
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

    /**
     * Realiza una solicitud a la API para obtener información sobre los nodos y realiza
     * acciones en consecuencia, como agregar marcadores y dibujar rutas en el mapa.
     */
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

                    // Create a list of destinations
                    List<String> destinations = new ArrayList<>();
                    destinations.add(driverLocation);
                    destinations.add("Destino 1");

                    getShortestPath(destinations);

//                    ArrayList lista_nodos = new ArrayList();
//
//                    LatLng origen = new LatLng(9.838267, -83.902797);
//                    LatLng destino = new LatLng(9.846535, -83.903945);
//                    LatLng otro = new LatLng(9.854979,  -83.90887);
//
//
//                    lista_nodos.add(origen);
//                    lista_nodos.add(destino);
//                    lista_nodos.add(otro);
//
//                    drawLinesBetweenCoordinates(lista_nodos);

                }
            }

            @Override
            public void onFailure(Call<NodesResponse> call, Throwable t) {
                // Manejar el fallo
                Log.e("Node", "Error al obtener nodos desde la API", t);
            }
        });
    }

    /**
     * Agrega marcadores al mapa en función de la información proporcionada por la API.
     *
     * @param nodeMarkers Lista de marcadores de nodos obtenidos de la API.
     */
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

    /**
     * Establece un marcador que representa la ubicación actual del conductor en el mapa.
     *
     * @param driverLocation La ubicación actual del conductor.
     */
    private void setDriverLocationMarker(String driverLocation) {
        if (carMarker != null) {
            // Update the position of the existing car marker
            for (Marker marker : markers) {
                if ("CarMarker".equals(marker.getTitle())) {
                    marker.setPosition(marker.getPosition());
                    break;
                }
            }
        } else {
            // Create a new car marker if it doesn't exist
            for (Marker marker : markers) {
                if (driverLocation.equals(marker.getTitle())) {
                    // Create a new car marker in the same position
                    MarkerOptions newMarkerOptions = new MarkerOptions()
                            .position(marker.getPosition())
                            .title("CarMarker")
                            .zIndex(1.0f)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon));

                    // Add the new car marker to the map
                    carMarker = mMap.addMarker(newMarkerOptions);

                    // Add the new car marker to the list of markers
                    markers.add(carMarker);

                    break; // End the loop after finding the original marker
                }
            }
        }
    }



    /**
     * Dibuja una polilínea entre las coordenadas especificadas en el mapa y agrega marcadores
     * en las posiciones correspondientes.
     *
     * @param coordinates Lista de coordenadas para dibujar la polilínea.
     */
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
            LatLng position = new LatLng(9.858343, -83.915458);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hombre_icon))
                    .anchor(0.5f, 0.5f); // Ajusta la posición del ancla del icono

            mMap.addMarker(markerOptions);
        }
    }


    private void getShortestPath(List<String> destinationNames) {
        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // Use the new method to get the shortest path
        Call<List<Node>> call = apiService.getShortestPathToCompany(destinationNames);
        call.enqueue(new Callback<List<Node>>() {
            @Override
            public void onResponse(Call<List<Node>> call, Response<List<Node>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Node> shortestPath = response.body();

                    // Log the result
                    for (Node node : shortestPath) {
                        Log.d("Shortest Path Node", "Name: " + node.getName() +
                                ", Latitude: " + node.getLatitude() +
                                ", Longitude: " + node.getLongitude());
                    }

                    // Create a list of coordinates from the shortest path
                    List<LatLng> coordinates = new ArrayList<>();
                    for (Node node : shortestPath) {
                        coordinates.add(new LatLng(node.getLatitude(), node.getLongitude()));
                    }

                    // Draw the lines between the coordinates
                    drawLinesBetweenCoordinates(coordinates);

                    List<String> coordinates2 = new ArrayList<>();
                    for (Node node : shortestPath) {
                        coordinates2.add(node.getName());
                    }

                    // Pass the coordinates and total distance to animateCarAlongPath
                    getTimepProm(coordinates2, coordinates);

                } else {
                    // Handle error
                    Log.e("API Error", "Error getting shortest path");
                }
            }

            @Override
            public void onFailure(Call<List<Node>> call, Throwable t) {
                // Handle failure
                Log.e("API Error", "Failed to get shortest path", t);
            }
        });
    }


    private void animateCarAlongPath(final List<LatLng> path, double totalDistance) {
        // Reset previous animation
        handler.removeCallbacksAndMessages(null);

        int distanciaInt = (int) Math.round(totalDistance);

        Log.e("Time", String.valueOf(distanciaInt));
        // Calculate the total time for the animation (adjust speedFactor as needed)
        int totalAnimationTime = (distanciaInt * 10000) / path.size(); // 1000 milliseconds per second

        Log.e("Time", String.valueOf(totalAnimationTime));
        // Index to keep track of the current position in the path
        final int[] currentIndex = {0};

        // Calculate the delay between each position update
        final int delay = totalAnimationTime / path.size();

        // Runnable to update the car's position at regular intervals
        Runnable updateCarPosition = new Runnable() {
            @Override
            public void run() {
                if (currentIndex[0] < path.size()) {
                    LatLng nextPosition = path.get(currentIndex[0]);
                    moveCarMarker(nextPosition);

                    // Check if it's the last position in the path
                    if (currentIndex[0] == path.size() - 1) {
                        // Perform the additional action for the last position
                        performAdditionalAction();
                    }

                    currentIndex[0]++;
                    // Repeat the animation after a delay
                    handler.postDelayed(this, delay);
                }
            }
        };

        // Start the animation
        handler.post(updateCarPosition);
    }

    private void moveCarMarker(LatLng newPosition) {
        // Find the car marker in the list
        for (Marker marker : markers) {
            if (marker.getTitle().equals("CarMarker")) {
                marker.setPosition(newPosition);
                break;
            }
        }
    }

    private void performAdditionalAction() {
        Intent intent = new Intent(getActivity(), RatingActivity.class);
        startActivity(intent);
        Log.e("Animation", "Reached the last position. Performing additional action.");
    }


    private void getTimepProm(List<String> destinationNames, List<LatLng> coordinates) {
        String ipAddress = VariablesGlobales.localip;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // Use the new method to get the total distance
        Call<Double> call = apiService.getTotalDistance(destinationNames);
        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double totalDistance = response.body();
                    Log.d("Total Distance", "Total Distance: " + totalDistance);

                    // Now you can use the total distance as needed
                    animateCarAlongPath(coordinates, totalDistance);
                } else {
                    // Handle error
                    Log.e("API Error", "Error getting total distance");
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                // Handle failure
                Log.e("API Error", "Failed to get total distance", t);
            }
        });
    }





}
