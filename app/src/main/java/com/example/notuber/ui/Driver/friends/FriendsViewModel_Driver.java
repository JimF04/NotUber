package com.example.notuber.ui.Driver.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel para el fragmento de amigos del conductor.
 */
public class FriendsViewModel_Driver extends ViewModel {

    /**
     * Texto mutable observable que se actualiza en el fragmento de amigos del conductor.
     */
    private final MutableLiveData<String> mText;

    /**
     * Lista mutable observable de amigos.
     */
    private final MutableLiveData<String> friends = new MutableLiveData<String>();

    /**
     * Constructor de la clase FriendsViewModel_Driver. Inicializa el LiveData y establece un valor predeterminado.
     */
    public FriendsViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friends fragment Driver");
    }

    /**
     * Establece la lista de amigos.
     *
     * @param list Lista de amigos en formato de cadena.
     */
    public void setFriends(String list) {
        friends.setValue(list);
    }

    /**
     * Obtiene la lista de amigos observable.
     *
     * @return LiveData que contiene la lista de amigos.
     */
    public LiveData<String> getFriends() {
        return friends;
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