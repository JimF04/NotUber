package com.example.notuber.ui.Driver.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendsViewModel_Driver extends ViewModel {

    private final MutableLiveData<String> mText;

    public FriendsViewModel_Driver() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friends fragment Driver");
    }

    public LiveData<String> getText() {
        return mText;
    }
}