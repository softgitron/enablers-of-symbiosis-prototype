package com.example.enablersofsymbiosisprototype.ui.marketplace;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MarketplaceViewModel extends ViewModel {

    public MutableLiveData<Boolean> radioOrderNewest = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioOrderOldest = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioOrderPriceHighest = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioOrderPriceLowest = new MutableLiveData<>();

    public MarketplaceViewModel() {
        radioOrderNewest.setValue(true);
    }
}