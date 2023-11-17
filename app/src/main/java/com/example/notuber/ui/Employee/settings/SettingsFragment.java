package com.example.notuber.ui.Employee.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.MainActivity;
import com.example.notuber.R;
import com.example.notuber.databinding.FragmentSettingsBinding;

/**
 * Fragmento para la configuración del usuario empleado.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    /**
     * Método llamado para crear y devolver la jerarquía de vistas asociada con el fragmento.
     *
     * @param inflater           El objeto LayoutInflater que se utiliza para inflar cualquier vista del fragmento.
     * @param container          Si no es nulo, este es el grupo de vistas al que se adjuntará el fragmento.
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido a partir de un estado guardado anteriormente.
     * @return La vista root del fragmento.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel galleryViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button mLogoutButton = (Button) root.findViewById(R.id.btn_logout);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;

    }

    /**
     * Método llamado cuando el fragmento ya no está en uso.
     * Esto se llama después de onDestroy().
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}