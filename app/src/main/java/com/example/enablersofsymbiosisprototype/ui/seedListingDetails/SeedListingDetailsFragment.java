package com.example.enablersofsymbiosisprototype.ui.seedListingDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.data.MachineRentalListing;
import com.example.enablersofsymbiosisprototype.data.SeedListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMachineRentalListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.databinding.FragmentSeedListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.ui.StringUtils;
import com.example.enablersofsymbiosisprototype.ui.farmMap.FarmMapViewModel;
import com.example.enablersofsymbiosisprototype.ui.machineListingDetails.MachineListingDetailsFragmentArgs;

public class SeedListingDetailsFragment extends Fragment {

    private FragmentSeedListItemDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSeedListItemDetailsBinding.inflate(inflater, container, false);

        // Get parameters from input bundle.
        MachineListingDetailsFragmentArgs fragmentArgs = MachineListingDetailsFragmentArgs.fromBundle(getArguments());
        int userId = fragmentArgs.getUserId();
        int listingId = fragmentArgs.getListingId();

        User user = Users.getInstance().list.get(userId);
        SeedListing listing = (SeedListing) user.getListingById(listingId);

        // Populate fields based on the arguments.
        binding.seedOrigin.setText(StringUtils.boldTitle("Origin", listing.origin));
        binding.seedGrowingTime.setText(StringUtils.boldTitle("Growing time", listing.growingTime));
        binding.seedMill.setText(StringUtils.boldTitle("Industry", listing.industry));
        binding.seedDiseaseResistance.setText(StringUtils.boldTitle("Disease resistance", listing.getDiseaseResistance()));
        binding.seedCultivationInstructions.setText(StringUtils.boldTitle("Cultivation instructions", listing.cultivationInstructions));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}