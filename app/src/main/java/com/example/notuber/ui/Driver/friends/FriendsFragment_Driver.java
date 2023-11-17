package com.example.notuber.ui.Driver.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.lifecycle.Observer;
import com.example.notuber.ApiService;
import com.example.notuber.DriverActivity;
import com.example.notuber.DriverLoginActivity;
import com.example.notuber.Model.FriendRequest;
import com.example.notuber.VariablesGlobales;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.databinding.FragmentFriendsDriverBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;
import java.util.List;

public class FriendsFragment_Driver extends Fragment {

    private FragmentFriendsDriverBinding binding;
    private FriendsViewModel_Driver viewmodel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FriendsViewModel_Driver viewmodel = new ViewModelProvider(requireActivity()).get(FriendsViewModel_Driver.class);

        binding = FragmentFriendsDriverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextInputEditText correoamigo = binding.correoamigo;
        Button buttonAddFriend = binding.button;
        LinearLayout linearLayout = binding.linearLayout;

        buttonAddFriend.setOnClickListener(v -> {

            String ipAddress = VariablesGlobales.localip;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + ipAddress + ":8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            String friendName = correoamigo.getText().toString();
            String username = VariablesGlobales.currentuser;

            FriendRequest friendRequest = new FriendRequest(username, friendName);

            Call<Void> call = apiService.addFriend(friendRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Adici�n de amigo exitosa
                        Toast.makeText(getActivity(), "Friend added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Manejo de errores de red.
                    Toast.makeText(getActivity(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // request get de amigos
            ApiService apiService2 = retrofit.create(ApiService.class);
            Call<String> callget = apiService2.getFriends(username);

            callget.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String amikos = (String) response.body();
                        viewmodel.setFriends(amikos);
                    } else {
                        Toast.makeText(getActivity(), "No se pudo obtener lista de amigos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            viewmodel.getFriends().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String friendstring) {
                    // This code will run whenever the friends list in the ViewModel is updated
                    if (friendstring != null && !friendstring.isEmpty()) {
                        String[] amigos = friendstring.split("\\s*,\\s*");
                        linearLayout.removeAllViews(); // Clear existing views

                        for (String amigo : amigos) {
                            TextView textView = new TextView(getContext());
                            textView.setText(amigo);
                            textView.setPadding(10, 10, 10, 10);
                            linearLayout.addView(textView);
                        }
                    }
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
