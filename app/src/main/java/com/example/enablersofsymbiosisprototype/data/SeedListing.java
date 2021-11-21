package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.camelToHumanCase;
import static com.example.enablersofsymbiosisprototype.data.Utils.enumToStringList;

import java.util.ArrayList;

public class SeedListing extends Listing {
    public enum Application {
        NormalAndOrganicFarming,
    }

    public enum DiseaseResistance {
        Bad,
        Medium,
        Good,
        Excellent
    }

    public String weight;
    public Application application;

    // Product information.
    public String origin;
    public String growingTime;
    public String industry;

    // Technical information.
    public DiseaseResistance diseaseResistance;
    public String cultivationInstructions;

    public ArrayList<String> getApplications() {
        return enumToStringList(Application.values());
    }

    public String getDiseaseResistance() {
        return camelToHumanCase(diseaseResistance.name());
    }
}
