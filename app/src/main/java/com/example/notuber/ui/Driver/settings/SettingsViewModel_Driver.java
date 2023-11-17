package com.example.notuber.ui.Driver.settings;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel asociado al fragmento de configuración en la interfaz de usuario del conductor.
 */
public class SettingsViewModel_Driver extends ViewModel {

    /**
     * LiveData que contiene el texto asociado al fragmento de configuración.
     */
    private final MutableLiveData<String> mText;
    /**
     * Botón asociado al fragmento de configuración para realizar acciones específicas.
     */
    private Button mLogoutButton;

    /**
     * Constructor que inicializa el ViewModel y establece un valor predeterminado para el texto.
     */
    public SettingsViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

    }
    /**
     * Obtiene el LiveData que contiene el texto asociado al fragmento de configuración.
     *
     * @return LiveData que contiene el texto del fragmento de configuración.
     */
    public LiveData<String> getText() {
        return mText;
    }
}