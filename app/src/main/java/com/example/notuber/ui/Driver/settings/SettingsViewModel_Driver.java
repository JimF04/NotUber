package com.example.notuber.ui.Driver.settings;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel_Driver extends ViewModel {

    private final MutableLiveData<String> mText;
    private Button mLogoutButton;

    public SettingsViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}