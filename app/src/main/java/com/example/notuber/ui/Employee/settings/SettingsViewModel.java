package com.example.notuber.ui.Employee.settings;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel asociado al fragmento de configuración del usuario empleado.
 */
public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private Button mLogoutButton;

    /**
     * Constructor de la clase SettingsViewModel.
     * Inicializa el MutableLiveData y establece un valor predeterminado para el texto.
     */
    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

    }

    /**
     * Método para obtener el LiveData del texto asociado con el fragmento.
     *
     * @return LiveData del texto del fragmento.
     */
    public LiveData<String> getText() {
        return mText;
    }
}