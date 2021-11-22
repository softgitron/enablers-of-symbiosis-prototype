package com.example.enablersofsymbiosisprototype.ui.theirFarm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TheirFarmViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TheirFarmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
