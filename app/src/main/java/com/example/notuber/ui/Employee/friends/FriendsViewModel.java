package com.example.notuber.ui.Employee.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> friends = new MutableLiveData<String>();

    public FriendsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friends fragment Driver");
    }

    public void setFriends(String list) {
        friends.setValue(list);
    }

    public LiveData<String> getFriends() {
        return friends;
    }
    public LiveData<String> getText() {
        return mText;
    }
}