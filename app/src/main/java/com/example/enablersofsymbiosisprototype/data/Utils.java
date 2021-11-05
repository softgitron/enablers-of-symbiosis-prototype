package com.example.enablersofsymbiosisprototype.data;

import java.util.ArrayList;

public class Utils {
    public static String camelToHumanCase(String cameCaseString) {
        StringBuilder humanReadableString = new StringBuilder(cameCaseString.substring(0, 1));
        for (char camelChar : cameCaseString.substring(1).toCharArray()) {
            if (Character.isUpperCase(camelChar)) {
                humanReadableString.append(" ").append(Character.toLowerCase(camelChar));
            } else {
                humanReadableString.append(camelChar);
            }
        }

        return humanReadableString.toString();
    }

    @SuppressWarnings("rawtypes")
    public static ArrayList<String> enumToStringList(Enum[] enumerator) {
        ArrayList<String> enumItems = new ArrayList<>();
        for (Enum instance : enumerator) {
            String humanizedDepartmentName = camelToHumanCase(instance.name());
            enumItems.add(humanizedDepartmentName + "s");
        }

        return enumItems;
    }
}
