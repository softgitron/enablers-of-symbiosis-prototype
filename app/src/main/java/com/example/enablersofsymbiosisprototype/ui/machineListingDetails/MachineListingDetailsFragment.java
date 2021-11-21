package com.example.enablersofsymbiosisprototype.ui.machineListingDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.data.Listing;
import com.example.enablersofsymbiosisprototype.data.MachineRentalListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentFarmMapBinding;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMachineRentalListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.ui.StringUtils;
import com.example.enablersofsymbiosisprototype.ui.farmMap.FarmMapViewModel;
import com.example.enablersofsymbiosisprototype.ui.listingDetails.ListingDetailsFragmentArgs;

public class MachineListingDetailsFragment extends Fragment {

    private FragmentMachineRentalListItemDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMachineRentalListItemDetailsBinding.inflate(inflater, container, false);

        // Get parameters from input bundle.
        MachineListingDetailsFragmentArgs fragmentArgs = MachineListingDetailsFragmentArgs.fromBundle(getArguments());
        int userId = fragmentArgs.getUserId();
        int listingId = fragmentArgs.getListingId();

        User user = Users.getInstance().list.get(userId);
        MachineRentalListing listing = (MachineRentalListing) user.getListingById(listingId);

        // Populate fields based on the arguments.
        binding.equipmentInformationMotor.setText(StringUtils.boldTitle("Engine", listing.engine));
        binding.equipmentInformationGearbox.setText(StringUtils.boldTitle("Gearbox", listing.gears.name()));
        binding.equipmentInformationDrive.setText(StringUtils.boldTitle("Drive", listing.drive));
        binding.technicalInformationPower.setText(StringUtils.boldTitle("Power", listing.power, "kW"));
        binding.technicalInformationWeight.setText(StringUtils.boldTitle("Weight", listing.weight, "kg"));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}