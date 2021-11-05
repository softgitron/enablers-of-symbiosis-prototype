package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.enumToStringList;

import java.util.ArrayList;

public class FertilizerListing {
    public enum Application {
        OrganicProducts,
    }

    public enum Department {
        Fertilizers,
        OrganicFertilizers
    }

    public enum PresentationForms {
        Solid,
        Liquid,
        SolidAndLiquid
    }

    public String weight;
    public Application application;
    public Department department;

    public PresentationForms presentationForms;
    public String productInformation;

    public float phosphorus;
    public float potassium;
    public float calcium;
    public float magnesium;

    public ArrayList<String> getApplications() {
        return enumToStringList(Application.values());
    }

    public ArrayList<String> getDepartments() {
        return enumToStringList(Department.values());
    }

    public ArrayList<String> getPresentationForms() {
        return enumToStringList(PresentationForms.values());
    }
}
