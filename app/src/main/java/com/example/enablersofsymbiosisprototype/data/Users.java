package com.example.enablersofsymbiosisprototype.data;

import android.util.Pair;

import com.example.enablersofsymbiosisprototype.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        DateSpan dateSpan1 = createDateSpan("1.1.2022", "1.2.2022");
        MachineRentalListing listing1 = new MachineRentalListing();
        listing1.name = "Good tractor for rental";
        listing1.price = 20000L;
        listing1.type = Listing.ListingType.Rent;
        listing1.date = createDate("10.12.2021");
        listing1.photoReferences.add(R.drawable.green_tractor);
        listing1.availability = dateSpan1;
        user1.listings.add(listing1);

        DateSpan dateSpan2 = createDateSpan("2.4.2022", "3.4.2022");
        MachineRentalListing listing2 = new MachineRentalListing();
        listing2.name = "Cheap tractor for a weekend";
        listing2.price = 5000L;
        listing2.type = Listing.ListingType.Rent;
        listing2.date = createDate("11.12.2021");
        listing2.photoReferences.add(R.drawable.red_tractor);
        listing2.availability = dateSpan2;
        user1.listings.add(listing2);

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

        SeedListing listing3 = new SeedListing();
        listing3.name = "Oat seeds";
        listing3.price = 16L;
        listing3.type = Listing.ListingType.Sell;
        listing3.date = createDate("13.12.2021");
        listing3.cultivationInstructions = "Recommended for Southern Finland";
        listing3.photoReferences.add(R.drawable.oat_seeds);
        user3.listings.add(listing3);

        FertilizerListing listing4 = new FertilizerListing();
        listing4.name = "Fertilizer for Barley";
        listing4.price = 32L;
        listing4.type = Listing.ListingType.Sell;
        listing4.date = createDate("15.12.2021");
        listing4.presentationForms = FertilizerListing.PresentationForms.Solid;
        listing4.photoReferences.add(R.drawable.chemical_fertilizer);
        user3.listings.add(listing4);

        list.add(user3);

        currentUser = user1;
    }

    private static DateSpan createDateSpan(String startDate, String endData) {
        DateSpan dateSpan = new DateSpan();
        dateSpan.initDatesBetween(createDate(startDate), createDate(endData));
        return dateSpan;
    }

    private static Date createDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
