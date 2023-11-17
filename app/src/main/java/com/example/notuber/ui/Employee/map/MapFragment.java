package com.example.notuber.ui.Employee.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.ApiService;
import com.example.notuber.Model.ApiResponse;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento que muestra un mapa con marcadores de nodos y la ubicación del empleado.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;

    private MapView mapView;
    private List<Marker> markers = new ArrayList<>();


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
        MapViewModel homeViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = (MapView) root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

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
     * @param googleMap La instancia de GoogleMap.
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
     * Obtiene los nodos desde la API y muestra marcadores en el mapa.
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
                    // Acceder a los datos del Bundle según sea necesario
                    String email = bundle.getString("email", "default_value");
                    // Realizar acciones con el email si es necesario
                    Log.d("MapFragment", "Email received: " + email);

                    // Llamar al servidor para obtener la ubicación del empleado
                    getEmployeeLocation(email);
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

    /**
     * Obtiene la ubicación del empleado desde la API y actualiza el color del marcador en el mapa.
     *
     * @param email El correo electrónico del empleado.
     */
    private void getEmployeeLocation(String email) {
        Log.d("Node", "getEmployeeLocation called with email: " + email);

        String ipAddress = VariablesGlobales.localip;

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Llamar al nuevo endpoint para obtener la ubicación
        // Llamar al nuevo endpoint para obtener la ubicación
        Call<ApiResponse> call = apiService.getEmployeeLocation(email);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String location = apiResponse.getLocation();
                    // Buscar el marcador con el mismo nombre de ubicación
                    Marker employeeMarker = findMarkerByLocationName(location);

                    if (employeeMarker != null) {
                        // Cambiar el color del marcador
                        employeeMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else {
                        Log.e("MapFragment", "Marker not found for location: " + location);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Manejar el fallo
                Log.e("Node", "Error al obtener la ubicación desde la API", t);
            }
        });
    }

    /**
     * Busca un marcador por el nombre de ubicación y actualiza su color.
     *
     * @param locationName El nombre de la ubicación del marcador.
     * @return El marcador encontrado o nulo si no se encuentra.
     */
    private Marker findMarkerByLocationName(String locationName) {
        for (Marker marker : markers) {
            if (locationName.equals(marker.getTitle())) {
                // Cambiar el color del marcador aquí
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                return marker;
            }
        }
        return null;
    }
}