package com.example.notuber.ui.Driver.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel asociado al fragmento de mapa en la interfaz de usuario del conductor.
 */
public class MapViewModel_Driver extends ViewModel {

    /**
     * LiveData que contiene el texto asociado al fragmento de mapa.
     */
    private final MutableLiveData<String> mText;


    /**
     * Constructor que inicializa el ViewModel y establece un valor predeterminado para el texto.
     */
    public MapViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is map fragment");
    }

    /**
     * Obtiene el LiveData que contiene el texto asociado al fragmento de mapa.
     *
     * @return LiveData que contiene el texto del fragmento de mapa.
     */
    public LiveData<String> getText() {
        return mText;
    }
}