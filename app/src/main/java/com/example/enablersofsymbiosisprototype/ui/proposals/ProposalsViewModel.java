package com.example.enablersofsymbiosisprototype.ui.proposals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProposalsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProposalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
