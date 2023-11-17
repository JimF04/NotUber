package com.example.notuber.ui.Employee.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.MainActivity;
import com.example.notuber.R;
import com.example.notuber.databinding.FragmentSettingsBinding;
import com.example.notuber.ui.Driver.settings.SettingsViewModel_Driver;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel_Driver galleryViewModel =
                new ViewModelProvider(this).get(SettingsViewModel_Driver.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button mLogoutButton = root.findViewById(R.id.btn_logout);

        // Crear el primer EditText y establecer parámetros
        EditText mEditableText1 = new EditText(requireContext());
        mEditableText1.setText("Nombre: Jimmy ");
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params1.bottomMargin = dpToPx(16); // Ajusta el valor según tus necesidades
        mEditableText1.setLayoutParams(params1);

        // Crear el segundo EditText y establecer parámetro

        // Crear el tercer EditText y establecer parámetros
        EditText mEditableText3 = new EditText(requireContext());
        mEditableText3.setText("Rating: 3.9");
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params3.bottomMargin = dpToPx(32); // Ajusta el valor según tus necesidades
        mEditableText3.setLayoutParams(params3);

        // Agregar los EditText al LinearLayout
        LinearLayout linearLayout = new LinearLayout(requireContext());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(mEditableText1);
        linearLayout.addView(mEditableText3);

        // Agregar el LinearLayout al ConstraintLayout
        ((ViewGroup) root).addView(linearLayout);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción del botón de cierre de sesión
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}