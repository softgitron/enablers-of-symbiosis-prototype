package com.example.enablersofsymbiosisprototype.data;

import android.util.Pair;

import com.example.enablersofsymbiosisprototype.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Users {
    private static Users instance = new Users();

    public User currentUser;
    public ArrayList<User> list = new ArrayList<>();

    private Users() {
        // User #1
        User user1 = new User();
        user1.firstName = "Lasse";
        user1.lastName = "Jokinen";
        user1.profilePictureReference = R.drawable.lasse_jokinen;
        user1.email = "lasse.jokinen@example.com";
        user1.phoneNumber = 358552145864L;

        DateSpan dateSpan1 = new DateSpan();
        try {
            dateSpan1.initDatesBetween(
                    new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse("1.1.2022"),
                    new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse("1.2.2022"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MachineRentalListing listing1 = new MachineRentalListing();
        listing1.name = "Good tractor for rental";
        listing1.price = 20000;
        listing1.photoReferences.add(R.drawable.green_tractor);
        listing1.availability = dateSpan1;
        user1.listings.add(listing1);

        list.add(user1);

        // User #2
        User user2 = new User();
        user2.firstName = "Merja";
        user2.lastName = "Rantanen";
        user2.profilePictureReference = R.drawable.merja_rantanen;
        user2.email = "merja.rantanen@example.com";
        user2.phoneNumber = 358789562214L;

        Notifications notifications1 = new Notifications();
        notifications1.marketplaceTitle = "New offerings near you!";
        notifications1.marketplaceDescription = "- Ferguson X1 tractor rental\n- Manure on sale!";
        notifications1.farmMapTitle = "Neighbours joined platform!";
        notifications1.farmMapDescription = "- Smith's Farm\n- Johanson's Farm";
        notifications1.dealsTitle = "New deals available!";
        notifications1.dealsDescription = "- Jones wants to buy milk from you\nWilliams accepted your milk";
        notifications1.myFarmTitle = "Reminders";
        notifications1.myFarmDescription = "- Remember to update your monthly stats\n- Farm photo is 2 years old, time to update?";
        user2.notifications = notifications1;

        list.add(user2);

        // User #3
        User user3 = new User();
        user3.firstName = "Heli";
        user3.lastName = "Järvinen";
        user3.profilePictureReference = R.drawable.heli_j_rvinen;
        user3.email = "heli.jarvinen@example.com";
        user3.phoneNumber = 358587632478L;

        Notifications notifications2 = new Notifications();
        notifications2.marketplaceTitle = "New offerings near you!";
        notifications2.marketplaceDescription = "- Super seeds available";
        user3.notifications = notifications2;

        list.add(user3);

        currentUser = user1;
    }

    public static Users getInstance() {
        return instance;
    }

    public ArrayList<Pair<User, Listing>> getAllListings() {
        ArrayList<Pair<User, Listing>> allListingsData = new ArrayList<>();
        for (User user : list) {
            for (Listing listing : user.listings) {
                allListingsData.add(new Pair<>(user, listing));
            }
        }

        return allListingsData;
    }
}
