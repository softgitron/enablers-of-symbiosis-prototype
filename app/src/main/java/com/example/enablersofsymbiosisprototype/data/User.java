package com.example.enablersofsymbiosisprototype.data;

import java.util.ArrayList;

public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public long phoneNumber;
    public int profilePictureReference;

    public ArrayList<Listing> listings = new ArrayList<>();
    public Notifications notifications;
    public Farm farm;
    public Company company;

    public ArrayList<Message> messages = new ArrayList<>();

    private static int userAmount;

    public User() {
        id = userAmount;
        userAmount++;
    }

    public Listing getListingById(int listingId) {
        for (Listing listing : listings) {
            if (listing.id == listingId) {
                return listing;
            }
        }
        return null;
    }
}
