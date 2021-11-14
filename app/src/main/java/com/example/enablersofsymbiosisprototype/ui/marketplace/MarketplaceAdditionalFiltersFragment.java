package com.example.enablersofsymbiosisprototype.ui.marketplace;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.enablersofsymbiosisprototype.MainActivity;
import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceAdditionalFiltersBinding;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MarketplaceAdditionalFiltersFragment extends Fragment {
    private FragmentMarketplaceAdditionalFiltersBinding binding;
    private Context currentContext;
    private Activity currentActivity;

    private SearchController searchController;
    private MyLocationNewOverlay locationOverlay;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currentActivity = getActivity();
        currentContext = getContext();

        binding = FragmentMarketplaceAdditionalFiltersBinding.inflate(inflater, container, false);

        searchController = SearchController.getInstance().updateBinding(binding).clone();

        // Click handlers for radio buttons.
        binding.filterOrder.radioOrderNewest.setOnClickListener(searchController.radioButtonHandler);
        binding.filterOrder.radioOrderOldest.setOnClickListener(searchController.radioButtonHandler);
        binding.filterOrder.radioOrderPriceHighest.setOnClickListener(searchController.radioButtonHandler);
        binding.filterOrder.radioOrderPriceLowest.setOnClickListener(searchController.radioButtonHandler);

        // Click handlers for chips.
        binding.basicFilterCategories.filterFarmByproducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmDisposables.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmProducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmFertilizersAndChemicals.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFieldRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterMachineRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterSeeds.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterWorkforceRental.setOnClickListener(searchController.chipHandler);

        // Click handlers for check boxes.
        binding.listingTypes.checkBoxRentalListings.setOnClickListener(searchController.checkBoxHandler);
        binding.listingTypes.checkBoxSellListings.setOnClickListener(searchController.checkBoxHandler);
        binding.listingTypes.checkBoxPurchaseListings.setOnClickListener(searchController.checkBoxHandler);

        // Text handlers for text fields.
        binding.filterSearch.addTextChangedListener(searchController.searchListener);
        binding.filterMinPrice.addTextChangedListener(searchController.minPriceListener);
        binding.filterMaxPrice.addTextChangedListener(searchController.maxPriceListener);
        binding.filterMaxDistance.addTextChangedListener(searchController.maxDistanceListener);

        // Update chip statuses.
        searchController.updateChipStatuses();

        // Update order statuses.
        searchController.updateCurrentOrder();

        // Update listing types.
        searchController.updateCheckBoxStatuses();

        // Update search phrase.
        searchController.updateSearchText();

        // Update min price.
        searchController.updateMinPriceText();

        // Update max price.
        searchController.updateMaxPriceText();

        // Update max distance.
        searchController.updateMaxDistanceText();

        // Handle apply filters button press.
        binding.applyFiltersButton.setOnClickListener(view -> {
            searchController.updateInstance(searchController);
            NavController navController = NavHostFragment.findNavController(MarketplaceAdditionalFiltersFragment.this);
            navController.navigateUp();
        });

        // Handle clear filters button press.
        binding.clearFiltersButton.setOnClickListener(view -> {
            SearchController.resetInstance();
            NavController navController = NavHostFragment.findNavController(MarketplaceAdditionalFiltersFragment.this);
            navController.navigateUp();
        });

        // Get a handle to the fragment and register the callback.
        MapView mapFragment = binding.filterMapView;
        mapFragment.setTileSource(TileSourceFactory.MAPNIK);
        mapFragment.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        mapFragment.setMultiTouchControls(true);
        mapFragment.setMinZoomLevel(3d);
        mapFragment.setMaxZoomLevel(18d);
        mapFragment.zoomToBoundingBox(new BoundingBox(), false);

        binding.filterMaxDistance.setOnTouchListener((view, motionEvent) -> requestLocationPermissions());

        binding.filterMaxDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                requestLocationPermissions();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.filterMapView.setOnTouchListener((view, motionEvent) -> requestLocationPermissions());

        // Add location layer, when permissions granted.
        MainActivity.latestGrantResults.observe(getViewLifecycleOwner(), ints -> checkPermissionsAndAddOverlay());
        checkPermissionsAndAddOverlay();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Boolean requestLocationPermissions() {
        if ((ActivityCompat.checkSelfPermission(
                currentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && !MainActivity.ongoingPermissionRequest) {
            MainActivity.ongoingPermissionRequest = true;
            ActivityCompat.requestPermissions(currentActivity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return true;
        }
        return false;
    }

    private void checkPermissionsAndAddOverlay() {
        if ((ActivityCompat.checkSelfPermission(
                currentActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && locationOverlay == null) {
            // Add current location.
            MapView mapFragment = binding.filterMapView;
            locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(currentContext),mapFragment);
            locationOverlay.enableMyLocation();
            locationOverlay.enableFollowLocation();
            locationOverlay.setDrawAccuracyEnabled(true);
            mapFragment.getOverlays().add(locationOverlay);
        }
    }
}
