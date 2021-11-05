package com.example.enablersofsymbiosisprototype.data;

import java.util.ArrayList;

public class User {
    public String firstName;
    public String lastName;
    public String email;
    public long phoneNumber;
    public String pictureReference;

    public Farm farm;
    public Company company;

    public ArrayList<Message> messages = new ArrayList<>();
}
