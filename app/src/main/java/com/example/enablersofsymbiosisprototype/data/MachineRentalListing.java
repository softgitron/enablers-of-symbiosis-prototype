package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.enumToStringList;

import java.util.ArrayList;

public class MachineRentalListing extends RentalListing {
    public enum Department {
        Tractor,
        Harvester,
        LogMachine
    }

    public enum Gears {
        Manual,
        Automatic
    }

    // Technical details
    public int operatingHours;
    public int modelYear;
    public String engine;

    public Gears gears;
    public String drive;
    public int power;
    public int weight;

    public ArrayList<String> getDepartments() {
        return enumToStringList(Department.values());
    }
}
