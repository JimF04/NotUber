package com.example.notuber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.notuber.Model.ApiResponse;
import com.example.notuber.Model.FriendRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RatingActivity extends Activity {

    private RatingBar ratingBar;
    private Button submitRatingButton;
    private Button continueButton;
    private int ratingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.ratingBar);
        submitRatingButton = findViewById(R.id.submitRatingButton);
        continueButton = findViewById(R.id.continueButton);

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Método para autenticar al conductor
                addrating();
            }
        });

        continueButton.setOnClickListener(v -> {
            // regresa a menu principal de empleado
            Intent intent = new Intent(RatingActivity.this, EmployeeActivity.class);
            startActivity(intent);
            finish();
        });
    }
        public void addrating(){
            ratingValue = Math.round(ratingBar.getRating());
            Toast.makeText(getApplicationContext(), "Rating: " + ratingValue, Toast.LENGTH_SHORT).show();

            String ipAddress = VariablesGlobales.localip;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + ipAddress + ":8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            Call<ApiResponse> call = apiService.addRating(ratingValue);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Adici�n de amigo exitosa
                        Toast.makeText(getActivity(), "Friend added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}