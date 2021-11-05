package com.example.enablersofsymbiosisprototype.data;

import static com.example.enablersofsymbiosisprototype.data.Utils.enumToStringList;

import java.util.ArrayList;

public class MachineRentalListing extends Listing {
    public enum Department {
        Tractor,
        Harvester,
        LogMachine
    }

    public enum Gears {
        Manual,
        Automatic
    }

    public DateSpan availability;

    // Technical details
    public int operatingHours;
    public int modelYear;
    public String engine;
    public Department department;

    public Gears gears;
    public String drive;
    public int power;
    public int weight;

    public ArrayList<String> getDepartments() {
        return enumToStringList(Department.values());
    }
}
