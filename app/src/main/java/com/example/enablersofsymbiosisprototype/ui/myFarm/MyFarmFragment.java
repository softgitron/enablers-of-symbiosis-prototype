package com.example.enablersofsymbiosisprototype.ui.myFarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.enablersofsymbiosisprototype.databinding.FragmentMyFarmBinding;

public class MyFarmFragment extends Fragment {

    private MyFarmViewModel myFarmViewModel;
    private FragmentMyFarmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFarmViewModel =
                new ViewModelProvider(this).get(MyFarmViewModel.class);

        binding = FragmentMyFarmBinding.inflate(inflater, container, false);

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