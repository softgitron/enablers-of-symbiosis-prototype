package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.enumToStringList;

import java.util.ArrayList;

public class FertilizerListing extends Listing {
    public enum Application {
        OrganicProducts,
    }

    public enum PresentationForms {
        Solid,
        Liquid,
        SolidAndLiquid
    }

    public String weight;
    public Application application;

    public PresentationForms presentationForms;
    public String productInformation;

    public String origin;

    public float phosphorus;
    public float potassium;
    public float calcium;
    public float magnesium;

    public ArrayList<String> getApplications() {
        return enumToStringList(Application.values());
    }

    public ArrayList<String> getPresentationForms() {
        return enumToStringList(PresentationForms.values());
    }
}
