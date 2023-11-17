package com.example.notuber.ui.Employee.services;

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
 * Fragmento para mostrar la información de servicios para el usuario empleado.
 */
public class ServicesFragment extends Fragment {

    private FragmentServicesBinding binding;

    /**
     * Se llama para crear y devolver la jerarquía de vistas asociada con el fragmento.
     *
     * @param inflater           El objeto LayoutInflater que puede inflar las vistas en el fragmento.
     * @param container          Si no es nulo, este es el grupo de vistas al que se adjuntará
     *                           la jerarquía de vistas resultante.
     * @param savedInstanceState Si no es nulo, este fragmento se está reanudando a partir de un estado guardado anteriormente.
     * @return La vista raíz del fragmento.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ServicesViewModel galleryViewModel =
                new ViewModelProvider(this).get(ServicesViewModel.class);

        binding = FragmentServicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textServices;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    /**
     * Se llama cuando la vista y los recursos asociados con el fragmento están siendo destruidos.
     * Este método también puede llamarse después de que el fragmento ya no esté asociado con su actividad,
     * pero esto dependerá de cómo se maneje la transacción.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}