package com.example.enablersofsymbiosisprototype.ui.listingDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListingDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}