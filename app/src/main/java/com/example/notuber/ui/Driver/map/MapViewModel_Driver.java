package com.example.notuber.ui.Driver.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel_Driver extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is map fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}