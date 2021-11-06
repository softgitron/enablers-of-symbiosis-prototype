package com.example.enablersofsymbiosisprototype.ui.farmMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FarmMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FarmMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}