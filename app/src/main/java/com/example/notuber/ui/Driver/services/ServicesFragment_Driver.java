package com.example.notuber.ui.Driver.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.databinding.FragmentServicesBinding;

/**
 * Fragmento que representa la sección de servicios en la interfaz de usuario del conductor.
 */
public class ServicesFragment_Driver extends Fragment {
    /**
     * El enlace de datos para el fragmento de servicios.
     */
    private FragmentServicesBinding binding;

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
        ServicesViewModel_Driver galleryViewModel =
                new ViewModelProvider(this).get(ServicesViewModel_Driver.class);

        binding = FragmentServicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textServices;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
}