package com.example.enablersofsymbiosisprototype.ui.marketplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.ui.recyclers.*;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceBinding;
import com.example.enablersofsymbiosisprototype.ui.recyclers.StandardListModel;
import com.example.enablersofsymbiosisprototype.ui.userMenu.UserMenuFragment;

public class MarketplaceFragment extends Fragment {


    private MarketplaceViewModel marketplaceViewModel;
    private FragmentMarketplaceBinding binding;

    private SearchController searchController;
    private StandardListModel[] marketplaceListItems;
    StandardListAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marketplaceViewModel =
                new ViewModelProvider(this).get(MarketplaceViewModel.class);

        binding = FragmentMarketplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        marketplaceViewModel.radioOrderNewest.observe(getViewLifecycleOwner(), binding.basicFilterOrder.radioOrderNewest::setChecked);
        marketplaceViewModel.radioOrderOldest.observe(getViewLifecycleOwner(), binding.basicFilterOrder.radioOrderOldest::setChecked);
        marketplaceViewModel.radioOrderPriceHighest.observe(getViewLifecycleOwner(), binding.basicFilterOrder.radioOrderPriceHighest::setChecked);
        marketplaceViewModel.radioOrderPriceLowest.observe(getViewLifecycleOwner(), binding.basicFilterOrder.radioOrderPriceLowest::setChecked);

        searchController = SearchController.getInstance(marketplaceViewModel);

        binding.basicFilterOrder.radioOrderNewest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderOldest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderPriceHighest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderPriceLowest.setOnClickListener(searchController.radioButtonHandler);

        binding.basicFilterCategories.filterFarmByproducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmDisposables.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmProducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmFertilizersAndChemicals.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFieldRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterMachineRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterSeeds.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterWorkforceRental.setOnClickListener(searchController.chipHandler);

        // Initial population for the recycler view.
        RecyclerView userList = binding.marketplaceItemsView;
        userList.setNestedScrollingEnabled(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        userList.setLayoutManager(layoutManager);

        // Set decoration to Recycler view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(userList.getContext(), LinearLayoutManager.VERTICAL);
        userList.addItemDecoration(dividerItemDecoration);

        // specify an adapter
        marketplaceListItems = searchController.getFilteredAndOrderedList();
        mAdapter = new StandardListAdapter(marketplaceListItems, null);
        userList.setAdapter(mAdapter);

        // Handle additional filters click.
        binding.moreSearchOptionsButton.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(MarketplaceFragment.this);
            navController.navigate(R.id.action_nav_marketplace_to_nav_marketplace_additional_filters);
        });

        // Handle search
        binding.applyFiltersButton.setOnClickListener(view -> {
            marketplaceListItems = searchController.getFilteredAndOrderedList();
            mAdapter.updateAdapterList(marketplaceListItems);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}