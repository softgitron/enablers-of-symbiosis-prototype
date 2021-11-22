package com.example.enablersofsymbiosisprototype.ui.farmMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.enablersofsymbiosisprototype.MainActivity;
import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.databinding.FragmentFarmMapBinding;
import com.example.enablersofsymbiosisprototype.ui.marketplace.MarketplaceFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class FarmMapFragment extends Fragment {

    private FarmMapViewModel farmMapViewModel;
    private FragmentFarmMapBinding binding;
    private MyLocationNewOverlay locationOverlay;
    private Context currentContext;
    private Activity currentActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        currentActivity = getActivity();
        currentContext = getContext();

        farmMapViewModel =
                new ViewModelProvider(this).get(FarmMapViewModel.class);

        binding = FragmentFarmMapBinding.inflate(inflater, container, false);

        // Get a handle to the fragment and register the callback.
        MapView mapFragment = binding.filterMapView;
        mapFragment.setTileSource(TileSourceFactory.MAPNIK);
        mapFragment.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        mapFragment.setMultiTouchControls(true);
        mapFragment.setMinZoomLevel(3d);
        mapFragment.setMaxZoomLevel(18d);
        mapFragment.zoomToBoundingBox(new BoundingBox(), false);

        binding.filterMapView.setOnTouchListener((view, motionEvent) -> requestLocationPermissions());

        MainActivity.latestGrantResults.observe(getViewLifecycleOwner(), ints -> checkPermissionsAndAddOverlay());
        checkPermissionsAndAddOverlay();

        Marker marker = new Marker(mapFragment);
        marker.setPosition(new GeoPoint(37.419857d,-122.088830d));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_location));
        mapFragment.getOverlays().add(marker);


        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        OverlayItem overlayItem = new OverlayItem(null, null, new GeoPoint(37.419857d,-122.078827d));
        overlayItem.setMarker(getResources().getDrawable(R.drawable.ic_location));
        items.add(overlayItem); // Lat/Lon decimal degrees

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        showBottomSheetDialog();
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, currentContext);
        mOverlay.setFocusItemsOnTap(true);

        mapFragment.getOverlays().add(mOverlay);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(currentContext);
        bottomSheetDialog.setContentView(R.layout.farm_context_popup);

        RelativeLayout arrow = bottomSheetDialog.findViewById(R.id.arrow);
        RelativeLayout name = bottomSheetDialog.findViewById(R.id.farmname);

        ImageButton upArrow = bottomSheetDialog.findViewById(R.id.up_arrow);

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(FarmMapFragment.this);
                navController.navigate(R.id.action_nav_farmmap_your_farm);
                bottomSheetDialog.cancel();
            }
        });

        bottomSheetDialog.show();
    }

    private Boolean requestLocationPermissions() {
        if ((ActivityCompat.checkSelfPermission(
                (Activity) currentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && !MainActivity.ongoingPermissionRequest) {
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