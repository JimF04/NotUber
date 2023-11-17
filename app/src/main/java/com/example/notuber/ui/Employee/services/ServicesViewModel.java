package com.example.notuber.ui.Employee.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel para el fragmento de servicios del usuario empleado.
 */
public class ServicesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Constructor que inicializa el ViewModel.
     */
    public ServicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is services fragment");
    }

    /**
     * Método para obtener el LiveData que contiene el texto a mostrar en la interfaz de usuario.
     *
     * @return LiveData que contiene el texto del fragmento de servicios.
     */
    public LiveData<String> getText() {
        return mText;
    }
}