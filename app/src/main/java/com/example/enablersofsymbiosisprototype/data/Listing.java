package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.camelToHumanCase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class Listing {
    public enum ListingType {
        Buy,
        Sell,
        Rent
    }

    public static final ArrayList<String> categories = new ArrayList<String>() {
        {
            add("Farm byproducts");
            add("Farm disposables");
            add("Farm products");
            add("Fertilizers and chemicals");
            add("Field rental");
            add("Machine rental");
            add("Seeds");
            add("Workforce rental");
        }
    };

    public ListingType type;
    public ArrayList<Integer> photoReferences = new ArrayList<>();
    public String name;
    public String description;
    public Long price;
    public Date date;
    public Location location;

    @NonNull
    @Override
    public String toString() {
        return camelToHumanCase(this.getClass().getName());
    }
}
