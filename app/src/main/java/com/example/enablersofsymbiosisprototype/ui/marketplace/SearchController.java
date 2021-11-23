package com.example.enablersofsymbiosisprototype.ui.marketplace;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.data.FertilizerListing;
import com.example.enablersofsymbiosisprototype.data.Listing;
import com.example.enablersofsymbiosisprototype.data.MachineRentalListing;
import com.example.enablersofsymbiosisprototype.data.RentalListing;
import com.example.enablersofsymbiosisprototype.data.SeedListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceAdditionalFiltersBinding;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceBinding;
import com.example.enablersofsymbiosisprototype.ui.recyclers.StandardListModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchController implements Cloneable {
    public enum SearchOrder {
        Newest,
        Oldest,
        PriceHighest,
        PriceLowest
    }

    private static SearchController instance = new SearchController();

    public OrderButtonClickListener radioButtonHandler;
    public ChipClickListener chipHandler;
    public ListingTypeClickListener checkBoxHandler;
    public SearchListener searchListener;
    public MinPriceListener minPriceListener;
    public MaxPriceListener maxPriceListener;
    public MaxDistanceListener maxDistanceListener;

    private FragmentMarketplaceBinding marketplaceBinding;
    private FragmentMarketplaceAdditionalFiltersBinding additionalFiltersBinding;

    private HashSet<Integer> currentCategories = new HashSet<>();
    private SearchOrder currentOrder = SearchOrder.Newest;
    private Boolean checkedRentalListings = true;
    private Boolean checkedSellListings = true;
    private Boolean checkedPurchaseListings = true;
    private String searchPhrase = "";
    private String minPrice = "";
    private String maxPrice = "";
    private String maxDistance = "";

    private SearchController() {
        initializeObject();
    }

    @NonNull
    public SearchController clone() {
        SearchController newSearchController = new SearchController();
        try {
            newSearchController = (SearchController) super.clone();
            newSearchController.initializeObject();
            newSearchController.currentCategories = (HashSet<Integer>) currentCategories.clone();
            return newSearchController;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newSearchController;
    }

    private void initializeObject() {
        radioButtonHandler = new OrderButtonClickListener();
        chipHandler = new ChipClickListener();
        checkBoxHandler = new ListingTypeClickListener();
        searchListener = new SearchListener();
        minPriceListener = new MinPriceListener();
        maxPriceListener = new MaxPriceListener();
        maxDistanceListener = new MaxDistanceListener();
    }

    public static SearchController getInstance() {
        return instance;
    }

    public SearchController updateBinding(Object currentBinding) {
        if (currentBinding instanceof FragmentMarketplaceBinding) {
            additionalFiltersBinding = null;
            marketplaceBinding = (FragmentMarketplaceBinding) currentBinding;
        } else if (currentBinding instanceof FragmentMarketplaceAdditionalFiltersBinding) {
            marketplaceBinding = null;
            additionalFiltersBinding = (FragmentMarketplaceAdditionalFiltersBinding) currentBinding;
        } else {
            System.out.println("Coding error, search controller accepts only either " +
                    "marketplace fragment or additional filters fragment.");
            System.exit(124);
        }
        return instance;
    }

    public void updateInstance(SearchController instance) {
        SearchController.instance = instance;
    }

    public static void resetInstance() {
        instance = new SearchController();
    }

    public class OrderButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedView) {
            int viewId = clickedView.getId();
            if (viewId == R.id.radioOrderNewest) {
                currentOrder = SearchOrder.Newest;
            } else if (viewId == R.id.radioOrderOldest) {
                currentOrder = SearchOrder.Oldest;
            } else if (viewId == R.id.radioOrderPriceHighest) {
                currentOrder = SearchOrder.PriceHighest;
            } else if (viewId == R.id.radioOrderPriceLowest) {
                currentOrder = SearchOrder.PriceLowest;
            }
            updateCurrentOrder();
        }
    }

    public void updateCurrentOrder() {
        // Get handle for the radio buttons.
        RadioButton radioOrderNewest;
        RadioButton radioOrderOldest;
        RadioButton radioOrderPriceHighest;
        RadioButton radioOrderPriceLowest;
        if (marketplaceBinding != null) {
            radioOrderNewest = marketplaceBinding.basicFilterOrder.radioOrderNewest;
            radioOrderOldest = marketplaceBinding.basicFilterOrder.radioOrderOldest;
            radioOrderPriceHighest = marketplaceBinding.basicFilterOrder.radioOrderPriceHighest;
            radioOrderPriceLowest = marketplaceBinding.basicFilterOrder.radioOrderPriceLowest;
        } else {
            radioOrderNewest = additionalFiltersBinding.filterOrder.radioOrderNewest;
            radioOrderOldest = additionalFiltersBinding.filterOrder.radioOrderOldest;
            radioOrderPriceHighest = additionalFiltersBinding.filterOrder.radioOrderPriceHighest;
            radioOrderPriceLowest = additionalFiltersBinding.filterOrder.radioOrderPriceLowest;
        }

        radioOrderNewest.setChecked(false);
        radioOrderOldest.setChecked(false);
        radioOrderPriceHighest.setChecked(false);
        radioOrderPriceLowest.setChecked(false);

        switch (currentOrder) {
            case Newest:
                radioOrderNewest.setChecked(true);
                break;
            case Oldest:
                radioOrderOldest.setChecked(true);
                break;
            case PriceHighest:
                radioOrderPriceHighest.setChecked(true);
                break;
            case PriceLowest:
                radioOrderPriceLowest.setChecked(true);
                break;
        }
    }

    public class ChipClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedChip) {
            Chip chip = (Chip)clickedChip;
            if (chip.isChecked()) {
                currentCategories.add(chip.getId());
            } else {
                currentCategories.remove(chip.getId());
            }
            updateHitsField();
        }
    }

    public void updateChipStatuses() {
        // Get handle for the filter categories chip group.
        ChipGroup chipGroup;
        if (marketplaceBinding != null) {
            chipGroup = marketplaceBinding.basicFilterCategories.filterCategories;
        } else {
            chipGroup = additionalFiltersBinding.basicFilterCategories.filterCategories;
        }

        // Uncheck all chip categories.
        chipGroup.clearCheck();

        // Set all chips checked that should be currently active.
        for (Integer currentCategory : currentCategories) {
            chipGroup.check(currentCategory);
        }
    }

    public class ListingTypeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedCheckBox) {
            CheckBox checkBox = (CheckBox) clickedCheckBox;
            int checkBoxId = checkBox.getId();
            if (checkBoxId == R.id.checkBoxRentalListings) {
                checkedRentalListings = checkBox.isChecked();
            } else if (checkBoxId == R.id.checkBoxSellListings) {
                checkedSellListings = checkBox.isChecked();
            } else if (checkBoxId == R.id.checkBoxPurchaseListings) {
                checkedPurchaseListings = checkBox.isChecked();
            }
            updateHitsField();
        }
    }

    public class SearchListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            searchPhrase = charSequence.toString();
            updateHitsField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void updateSearchText() {
        if (marketplaceBinding != null) {
            marketplaceBinding.filterSearch.setText(searchPhrase);
        } else {
            additionalFiltersBinding.filterSearch.setText(searchPhrase);
        }
    }

    public class MinPriceListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            minPrice = charSequence.toString();
            updateHitsField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void updateMinPriceText() {
        additionalFiltersBinding.filterMinPrice.setText(minPrice);
    }

    public class MaxPriceListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            maxPrice = charSequence.toString();
            updateHitsField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void updateMaxPriceText() {
        additionalFiltersBinding.filterMaxPrice.setText(maxPrice);
    }

    public class MaxDistanceListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            maxDistance = charSequence.toString();
            updateHitsField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void updateMaxDistanceText() {
        additionalFiltersBinding.filterMaxDistance.setText(maxDistance);
    }

    public void updateCheckBoxStatuses() {
        additionalFiltersBinding.listingTypes.checkBoxRentalListings.setChecked(checkedRentalListings);
        additionalFiltersBinding.listingTypes.checkBoxSellListings.setChecked(checkedSellListings);
        additionalFiltersBinding.listingTypes.checkBoxPurchaseListings.setChecked(checkedPurchaseListings);
    }

    public void updateStatusFields() {
        // Update filters text, if on marketplace page.
        // Otherwise update current hits on additional filters page.
        String titleSection = "<font color=#000000><b>Additional filters: </b></font>";
        String contentPrefix = "<font color=#666666></font>";
        String contentPostfix = "</font>";
        String listingTypes = null;
        if (!checkedRentalListings | !checkedSellListings | !checkedPurchaseListings) {
            listingTypes = "Listing types: " + Stream.of(
                    (checkedRentalListings ? null : "No rental listings"),
                    (checkedSellListings ? null : "No sell listings"),
                    (checkedPurchaseListings ? null : "No purchase listings")
            ).filter(Objects::nonNull)
                    .collect(Collectors.joining(" , ")) + contentPostfix;
        }

        String priceInfo = null;
        if (!minPrice.isEmpty() | !maxPrice.isEmpty()) {
            priceInfo = String.format("Price between %s-%s €", minPrice, maxPrice);
        }

        String distanceInfo = null;
        if (!maxDistance.isEmpty()) {
            distanceInfo = String.format("Maximum distance %s km", maxDistance);
        }

        // Form final string.
        String statusString = titleSection + contentPrefix + Stream.of(listingTypes,
                priceInfo, distanceInfo).filter(Objects::nonNull)
                .collect(Collectors.joining(" / ")) + contentPostfix;
        marketplaceBinding.currentFilters.setText(Html.fromHtml(statusString, Html.FROM_HTML_MODE_COMPACT));
    }

    public void updateHitsField() {
        if (additionalFiltersBinding == null) {
            return;
        }
        StandardListModel[] standardListModel = getFilteredAndOrderedList();
        String titleSection = "<font color=#000000><b>Current hits: </b></font>";
        String contentPrefix = "<font color=#666666></font>";
        String contentPostfix = "</font>";
        String currentHitsText = String.format(Locale.US, "%s %s %d hits with current parameters %s",
                titleSection, contentPrefix, standardListModel.length, contentPostfix);
        additionalFiltersBinding.currentHits.setText(Html.fromHtml(currentHitsText, Html.FROM_HTML_MODE_COMPACT));
    }

    public StandardListModel[] getFilteredAndOrderedList() {
        ArrayList<Pair<User, Listing>> allListingsData = Users.getInstance().getAllListings();

        // Prepare filtering.
        ChipGroup chipGroup;
        if (marketplaceBinding != null) {
            chipGroup = marketplaceBinding.basicFilterCategories.filterCategories;
        } else {
            chipGroup = additionalFiltersBinding.basicFilterCategories.filterCategories;
        }
        HashSet<String> currentCategoryStrings = new HashSet<>();
        for (Integer currentCategory : currentCategories) {
            Chip chip = chipGroup.findViewById(currentCategory);
            String chipName = chip.getText().toString();
            currentCategoryStrings.add(chipName);
        }

        float minPriceNumber = minPrice.isEmpty() ? 0 : Float.parseFloat(minPrice);
        float maxPriceNumber = maxPrice.isEmpty() ? 0 : Float.parseFloat(maxPrice);

        // Go through every element and filter accordingly.
        ArrayList<Pair<User, Listing>> filteredDataList = new ArrayList<>();
        for (Pair<User, Listing> dataInstance : allListingsData) {
            Listing listing = dataInstance.second;

            // Filter categories.
            if (!currentCategoryStrings.isEmpty()) {
                String categoryName = "";
                if (listing instanceof MachineRentalListing) {
                    categoryName = "Machine rental";
                } else if (listing instanceof SeedListing) {
                    categoryName = "Seeds";
                } else if (listing instanceof FertilizerListing) {
                    categoryName = "Fertilizers and chemicals";
                } else {
                    System.out.println("Category not implemented in the filtering.");
                    System.exit(548);
                }

                // Skip if not in the selected filters.
                if (!currentCategoryStrings.contains(categoryName)) {
                    continue;
                }
            }

            // Filter min price.
            if (minPriceNumber != 0 && (float)(listing.price / 100) < minPriceNumber) {
                continue;
            }

            // Filter max price.
            if (maxPriceNumber != 0 && (float)(listing.price / 100) > maxPriceNumber) {
                continue;
            }

            // Filter search phrase.
            if (!searchPhrase.isEmpty() && !listing.name.startsWith(searchPhrase)) {
                continue;
            }

            // Filter listing types.
            switch (listing.type) {
                case Rent:
                    if (!checkedRentalListings) {
                        continue;
                    }
                    break;
                case Sell:
                    if (!checkedSellListings) {
                        continue;
                    }
                    break;
                case Buy:
                    if (!checkedPurchaseListings) {
                        continue;
                    }
                    break;
            }

            filteredDataList.add(dataInstance);
        }

        switch (currentOrder) {
            case Newest:
            case Oldest:
                filteredDataList.sort(Comparator.comparing(userListingPair -> userListingPair.second.date));
                // Flip ordering, if oldest.
                if (currentOrder == SearchOrder.Newest) {
                    Collections.reverse(filteredDataList);
                }
                break;
            case PriceHighest:
            case PriceLowest:
                filteredDataList.sort(Comparator.comparing(userListingPair -> userListingPair.second.price));
                // Flip ordering, if lowest.
                if (currentOrder == SearchOrder.PriceHighest) {
                    Collections.reverse(filteredDataList);
                }
                break;
        }

        return adaptListingsToList(filteredDataList);
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
                RentalListing rListing = (RentalListing) listing;
                String upperDescription = String.format(Locale.US, "Price: %.2f € / day", (float)rListing.price / 100);
                String startTime = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(rListing.availability.getFirstDate());
                String endTime = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(rListing.availability.getLastDate());
                String lowerDescription = String.format(Locale.US, "Available: %s - %s", startTime, endTime);
                String renterDescription = String.format(Locale.US, "Renter: %s %s", user.firstName, user.lastName);
                adaptedListingsList[index].description = String.format(Locale.US, "%s\n%s\n%s",
                        upperDescription, lowerDescription, renterDescription);
            } else if (listing instanceof SeedListing) {
                SeedListing sListing = (SeedListing) listing;
                String upperDescription = String.format(Locale.US, "Price: %.2f € / kg", (float)sListing.price / 100);
                String lowerDescription = String.format(Locale.US, "Cultivation instructions: %s", sListing.cultivationInstructions);
                String sellerDescription = String.format(Locale.US, "Seller: %s %s", user.firstName, user.lastName);
                adaptedListingsList[index].description = String.format(Locale.US, "%s\n%s\n%s",
                        upperDescription, lowerDescription, sellerDescription);
            } else if (listing instanceof FertilizerListing) {
                FertilizerListing fListing = (FertilizerListing) listing;
                String upperDescription = String.format(Locale.US, "Price: %.2f € / kg", (float)fListing.price / 100);
                String lowerDescription = String.format(Locale.US, "Presentation: %s", fListing.presentationForms);
                String sellerDescription = String.format(Locale.US, "Seller: %s %s", user.firstName, user.lastName);
                adaptedListingsList[index].description = String.format(Locale.US, "%s\n%s\n%s",
                        upperDescription, lowerDescription, sellerDescription);
            } else {
                System.out.println("Not implemented class");
                System.exit(546);
            }

            adaptedListingsList[index].extras.put("listing", listing);
            adaptedListingsList[index].extras.put("user", user);

            index++;
        }

        return adaptedListingsList;
    }
}
