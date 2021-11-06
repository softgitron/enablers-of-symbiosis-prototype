package com.example.enablersofsymbiosisprototype.ui.farmMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.databinding.FragmentFarmMapBinding;

public class FarmMapFragment extends Fragment {

    private FarmMapViewModel farmMapViewModel;
    private FragmentFarmMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        farmMapViewModel =
                new ViewModelProvider(this).get(FarmMapViewModel.class);

        binding = FragmentFarmMapBinding.inflate(inflater, container, false);

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