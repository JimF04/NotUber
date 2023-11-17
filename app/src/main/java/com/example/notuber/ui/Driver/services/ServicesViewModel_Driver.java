package com.example.notuber.ui.Driver.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel asociado al fragmento de servicios en la interfaz de usuario del conductor.
 */
public class ServicesViewModel_Driver extends ViewModel {

    /**
     * LiveData que contiene el texto asociado al fragmento de servicios.
     */
    private final MutableLiveData<String> mText;

    /**
     * Constructor que inicializa el ViewModel y establece un valor predeterminado para el texto.
     */
    public ServicesViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is services fragment Driver");
    }

    /**
     * Obtiene el LiveData que contiene el texto asociado al fragmento de servicios.
     *
     * @return LiveData que contiene el texto del fragmento de servicios.
     */
    public LiveData<String> getText() {
        return mText;
    }
}