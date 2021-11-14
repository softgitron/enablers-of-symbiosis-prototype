package com.example.enablersofsymbiosisprototype.ui.marketplace;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.databinding.FragmentMarketplaceBinding;
import com.example.enablersofsymbiosisprototype.ui.recyclers.StandardListAdapter;
import com.example.enablersofsymbiosisprototype.ui.recyclers.StandardListModel;

public class MarketplaceFragment extends Fragment {


    StandardListAdapter mAdapter;
    private FragmentMarketplaceBinding binding;
    private SearchController searchController;
    private StandardListModel[] marketplaceListItems;
    private Boolean filterMenuVisible = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMarketplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchController = SearchController.getInstance().updateBinding(binding);

        // Click handlers for radio buttons.
        binding.basicFilterOrder.radioOrderNewest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderOldest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderPriceHighest.setOnClickListener(searchController.radioButtonHandler);
        binding.basicFilterOrder.radioOrderPriceLowest.setOnClickListener(searchController.radioButtonHandler);

        // Click handlers for chips.
        binding.basicFilterCategories.filterFarmByproducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmDisposables.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmProducts.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFarmFertilizersAndChemicals.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterFieldRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterMachineRental.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterSeeds.setOnClickListener(searchController.chipHandler);
        binding.basicFilterCategories.filterWorkforceRental.setOnClickListener(searchController.chipHandler);

        // Text handlers for text field.
        binding.filterSearch.addTextChangedListener(searchController.searchListener);

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

        // Handle hiding / showing of the filters.
        binding.maximizeMinimizeFilters.setOnClickListener(view -> {
            filterMenuVisible = !filterMenuVisible;
            if (filterMenuVisible) {
                binding.maximizeMinimizeFilters.setImageResource(R.drawable.ic_arrow_up);
                binding.filterLayout.setTranslationY(
                        -binding.innerFilterLayout.getHeight() + binding.maximizeMinimizeFilters.getHeight());
                binding.innerFilterLayout.setVisibility(View.VISIBLE);
                binding.filterLayout.animate()
                        .translationY(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.innerFilterLayout.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                binding.maximizeMinimizeFilters.setImageResource(R.drawable.ic_arrow_down);
                binding.filterLayout.animate()
                        .translationY(-binding.innerFilterLayout.getHeight() + binding.maximizeMinimizeFilters.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.filterLayout.setTranslationY(0);
                                binding.innerFilterLayout.setVisibility(View.GONE);
                            }
                        });
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        searchController = SearchController.getInstance().updateBinding(binding);

        // Update chip statuses.
        searchController.updateChipStatuses();

        // Update order statuses.
        searchController.updateCurrentOrder();

        // Update search phrase.
        searchController.updateSearchText();

        // Update status field.
        searchController.updateStatusFields();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}