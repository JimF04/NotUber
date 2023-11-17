package com.example.notuber.ui.Employee.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel para el fragmento del mapa.
 */
public class MapViewModel extends ViewModel {

    /**
     * Texto mutable observable que se actualiza en el fragmento del mapa.
     */
    private final MutableLiveData<String> mText;

    /**
     * Constructor de la clase MapViewModel. Inicializa el LiveData y establece un valor predeterminado.
     */
    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is map fragment");
    }

    /**
     * Obtiene el LiveData que contiene el texto observable.
     *
     * @return LiveData que contiene el texto observable.
     */
    public LiveData<String> getText() {
        return mText;
    }
}