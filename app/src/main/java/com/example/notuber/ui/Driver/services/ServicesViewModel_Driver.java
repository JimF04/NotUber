package com.example.notuber.ui.Driver.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServicesViewModel_Driver extends ViewModel {

    private final MutableLiveData<String> mText;

    public ServicesViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is services fragment Driver");
    }

    public LiveData<String> getText() {
        return mText;
    }
}