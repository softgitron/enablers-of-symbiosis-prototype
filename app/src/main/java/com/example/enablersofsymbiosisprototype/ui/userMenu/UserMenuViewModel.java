package com.example.enablersofsymbiosisprototype.ui.userMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserMenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserMenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}