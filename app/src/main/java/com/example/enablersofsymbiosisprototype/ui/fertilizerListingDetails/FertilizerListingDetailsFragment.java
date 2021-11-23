package com.example.enablersofsymbiosisprototype.ui.fertilizerListingDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.enablersofsymbiosisprototype.data.FertilizerListing;
import com.example.enablersofsymbiosisprototype.data.SeedListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentFertilizerListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.databinding.FragmentSeedListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.ui.StringUtils;
import com.example.enablersofsymbiosisprototype.ui.machineListingDetails.MachineListingDetailsFragmentArgs;

public class FertilizerListingDetailsFragment extends Fragment {

    private FragmentFertilizerListItemDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFertilizerListItemDetailsBinding.inflate(inflater, container, false);

        // Get parameters from input bundle.
        MachineListingDetailsFragmentArgs fragmentArgs = MachineListingDetailsFragmentArgs.fromBundle(getArguments());
        int userId = fragmentArgs.getUserId();
        int listingId = fragmentArgs.getListingId();

        User user = Users.getInstance().list.get(userId);
        FertilizerListing listing = (FertilizerListing) user.getListingById(listingId);

        // Populate fields based on the arguments.
        binding.fertilizerOrigin.setText(StringUtils.boldTitle("Origin", listing.origin));
        binding.fertilizerTotalPhosphorus.setText(StringUtils.boldTitle("Total phosphorus (P)", listing.phosphorus, "%"));
        binding.fertilizerPotassium.setText(StringUtils.boldTitle("Water-soluble potassium (K)", listing.potassium, "%"));
        binding.fertilizerCalcium.setText(StringUtils.boldTitle("Calcium (Ca)", listing.calcium, "%"));
        binding.fertilizerMagnesium.setText(StringUtils.boldTitle("Magnesium (Mg)", listing.magnesium, "%"));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}