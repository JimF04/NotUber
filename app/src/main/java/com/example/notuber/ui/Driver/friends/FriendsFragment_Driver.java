package com.example.notuber.ui.Driver.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notuber.databinding.FragmentFriendsDriverBinding;
import java.util.List;

public class FriendsFragment_Driver extends Fragment {

    private FragmentFriendsDriverBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel_Driver slideshowViewModel =
                new ViewModelProvider(this).get(FriendsViewModel_Driver.class);

        binding = FragmentFriendsDriverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextInputEditText correoamigo = binding.correoamigo;
        Button buttonAddFriend = binding.button;
        LinearLayout linearLayout = binding.linearLayout;

        buttonAddFriend.setOnClickListener(v -> {
            String friendName = correoamigo.getText().toString();
            // request de API REST que agregue amigo va aqui
            /* agarrar lista de amigos de servidor con otro request
                procesar y meterla en variable lista
             */
            List<Integer> amigos = List.of(1, 2, 3, 4, 5);
            linearLayout.removeAllViews(); // Clear existing views

            for (Integer number : amigos) {
                TextView textView = new TextView(getContext());
                textView.setText("Section " + number);
                textView.setPadding(10, 10, 10, 10);
                linearLayout.addView(textView);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
