package com.example.enablersofsymbiosisprototype.ui.deals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DealsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}