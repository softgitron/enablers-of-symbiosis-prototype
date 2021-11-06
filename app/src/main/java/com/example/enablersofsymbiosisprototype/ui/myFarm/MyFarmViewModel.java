package com.example.enablersofsymbiosisprototype.ui.myFarm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyFarmViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyFarmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}