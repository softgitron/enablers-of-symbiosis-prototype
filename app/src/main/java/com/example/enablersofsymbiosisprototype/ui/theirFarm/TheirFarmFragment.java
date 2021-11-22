package com.example.enablersofsymbiosisprototype.ui.theirFarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.databinding.FragmentDealsBinding;
import com.example.enablersofsymbiosisprototype.databinding.FragmentTheirFarmBinding;
import com.example.enablersofsymbiosisprototype.ui.deals.DealsViewModel;

public class TheirFarmFragment extends Fragment {

    private TheirFarmViewModel theirFarmViewModel;
    private FragmentTheirFarmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        theirFarmViewModel =
                new ViewModelProvider(this).get(TheirFarmViewModel.class);

        binding = FragmentTheirFarmBinding.inflate(inflater, container, false);

        //final TextView textView = binding.textSlideshow;
        //dealsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
