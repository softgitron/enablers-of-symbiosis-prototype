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

import com.example.enablersofsymbiosisprototype.MainActivity;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceAdditionalFiltersBinding;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MarketplaceAdditionalFiltersFragment extends Fragment {
    private MarketplaceViewModel marketplaceViewModel;
    private FragmentMarketplaceAdditionalFiltersBinding binding;
    private Context currentContext;
    private Activity currentActivity;

    private MyLocationNewOverlay locationOverlay;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currentActivity = getActivity();
        currentContext = getContext();

        marketplaceViewModel =
                new ViewModelProvider(this).get(MarketplaceViewModel.class);

        binding = FragmentMarketplaceAdditionalFiltersBinding.inflate(inflater, container, false);

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
