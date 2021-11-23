package com.example.enablersofsymbiosisprototype.ui.listingDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.data.FertilizerListing;
import com.example.enablersofsymbiosisprototype.data.Listing;
import com.example.enablersofsymbiosisprototype.data.MachineRentalListing;
import com.example.enablersofsymbiosisprototype.data.SeedListing;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentGeneralListItemDetailsBinding;
import com.example.enablersofsymbiosisprototype.ui.StringUtils;
import com.example.enablersofsymbiosisprototype.ui.fertilizerListingDetails.FertilizerListingDetailsFragment;
import com.example.enablersofsymbiosisprototype.ui.machineListingDetails.MachineListingDetailsFragment;
import com.example.enablersofsymbiosisprototype.ui.seedListingDetails.SeedListingDetailsFragment;

import java.util.Objects;

public class ListingDetailsFragment extends Fragment {

    private ListingDetailsViewModel listingDetailsViewModel;
    private FragmentGeneralListItemDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listingDetailsViewModel =
                new ViewModelProvider(this).get(ListingDetailsViewModel.class);

        binding = FragmentGeneralListItemDetailsBinding.inflate(inflater, container, false);

        //final TextView textView = binding.textSlideshow;
        //dealsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Get parameters from input bundle.
        ListingDetailsFragmentArgs fragmentArgs = ListingDetailsFragmentArgs.fromBundle(getArguments());
        int userId = fragmentArgs.getUserId();
        int listingId = fragmentArgs.getListingId();

        User user = Users.getInstance().list.get(userId);
        Listing listing = user.getListingById(listingId);

        // Populate basic fields
        binding.foregroundImage.setImageResource(listing.photoReferences.get(0));
        binding.listingTitle.setText(listing.name);
        binding.listingType.setText(StringUtils.boldTitle("Listing type", listing.type.name()));
        binding.listingDepartment.setText(StringUtils.boldTitle("Department", listing.department.name()));
        binding.listingPrice.setText(StringUtils.boldTitle("Price", listing.price / 100f, "€"));
        binding.listingDate.setText(StringUtils.boldTitle("Listing date", StringUtils.formatDate(listing.date)));
        binding.listingSellerName.setText(StringUtils.boldTitle("Seller name", user.firstName + " " + user.lastName));
        binding.listingSellerEmail.setText(StringUtils.boldTitle("Email", user.email));
        binding.listingSellerPhoneNumber.setText(StringUtils.boldTitle("Phone number", StringUtils.formatPhoneNumber(user.phoneNumber)));
        binding.listingDescription.setText(StringUtils.boldTitle("Description", listing.description));

        // Populate special fields based on the listing type.
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", user.id);
        bundle.putInt("listingId", listing.id);
        if (listing instanceof MachineRentalListing) {
            MachineListingDetailsFragment machineFragment = new MachineListingDetailsFragment();
            machineFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.listingSpecificDetails, machineFragment)
                    .commit();
        } else if (listing instanceof SeedListing) {
            SeedListingDetailsFragment seedFragment = new SeedListingDetailsFragment();
            seedFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.listingSpecificDetails, seedFragment)
                    .commit();
        } else if (listing instanceof FertilizerListing) {
            FertilizerListingDetailsFragment fertilizerFragment = new FertilizerListingDetailsFragment();
            fertilizerFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.listingSpecificDetails, fertilizerFragment)
                    .commit();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}