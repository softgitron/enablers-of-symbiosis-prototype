package com.example.enablersofsymbiosisprototype.ui.marketplace;

import android.util.Pair;
import android.view.View;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.data.Listing;
import com.example.enablersofsymbiosisprototype.data.MachineRentalListing;
import com.example.enablersofsymbiosisprototype.data.RentalListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.ui.recyclers.StandardListModel;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class SearchController {
    public enum SearchOrder {
        Newest,
        Oldest,
        PriceHighest,
        PriceLowest
    }

    private static SearchController instance = new SearchController();
    private static MarketplaceViewModel marketplaceViewModel;

    public OrderButtonClickListener radioButtonHandler;
    public ChipClickListener chipHandler;

    private HashSet<String> currentCategories = new HashSet<>();
    private SearchOrder currentOrder = SearchOrder.Newest;

    private SearchController() {
        radioButtonHandler = new OrderButtonClickListener();
        chipHandler = new ChipClickListener();
    }

    public static SearchController getInstance(MarketplaceViewModel newMarketplaceViewModel) {
        marketplaceViewModel = newMarketplaceViewModel;
        return instance;
    }

    public class OrderButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedView) {
            marketplaceViewModel.radioOrderNewest.setValue(false);
            marketplaceViewModel.radioOrderOldest.setValue(false);
            marketplaceViewModel.radioOrderPriceHighest.setValue(false);
            marketplaceViewModel.radioOrderPriceLowest.setValue(false);

            if (clickedView.getId() == R.id.radioOrderNewest) {
                currentOrder = SearchOrder.Newest;
                marketplaceViewModel.radioOrderNewest.setValue(true);
            } else if (clickedView.getId() == R.id.radioOrderOldest) {
                currentOrder = SearchOrder.Oldest;
                marketplaceViewModel.radioOrderOldest.setValue(true);
            } else if (clickedView.getId() == R.id.radioOrderPriceHighest) {
                currentOrder = SearchOrder.PriceHighest;
                marketplaceViewModel.radioOrderPriceHighest.setValue(true);
            } else if (clickedView.getId() == R.id.radioOrderPriceLowest) {
                currentOrder = SearchOrder.PriceLowest;
                marketplaceViewModel.radioOrderPriceLowest.setValue(true);
            }
        }
    }

    public class ChipClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedChip) {
            Chip chip = (Chip)clickedChip;
            if (chip.isChecked()) {
                currentCategories.add(chip.getText().toString());
            } else {
                currentCategories.remove(chip.getText().toString());
            }
        }
    }

    public StandardListModel[] getFilteredAndOrderedList() {
        ArrayList<Pair<User, Listing>> allListingsData = Users.getInstance().getAllListings();
        return adaptListingsToList(allListingsData);
    }

    private StandardListModel[] adaptListingsToList(ArrayList<Pair<User, Listing>> filteredListings) {
        StandardListModel[] adaptedListingsList = new StandardListModel[filteredListings.size()];

        int index = 0;
        for (Pair<User, Listing> userAndUser : filteredListings) {
            User user = userAndUser.first;
            Listing listing = userAndUser.second;
            adaptedListingsList[index] = new StandardListModel();

            adaptedListingsList[index].title = listing.name;

            if (listing.photoReferences.size() != 0) {
                adaptedListingsList[index].thumbnailReference = listing.photoReferences.get(0);
            }

            if (listing instanceof RentalListing) {
                RentalListing rListing = (RentalListing)listing;
                String upperDescription = String.format(Locale.US, "Price: %d / day", rListing.price / 100);
                String startTime = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(rListing.availability.getFirstDate());
                String endTime = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(rListing.availability.getLastDate());
                String lowerDescription = String.format(Locale.US, "Available: %s - %s", startTime, endTime);
                adaptedListingsList[index].description = String.format(Locale.US, "%s\n%s", upperDescription, lowerDescription);
            }

            adaptedListingsList[index].extras.put("listing", listing);
            adaptedListingsList[index].extras.put("user", user);

            index++;
        }

        return adaptedListingsList;
    }
}
