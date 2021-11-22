package com.example.enablersofsymbiosisprototype.ui.deals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.databinding.FragmentDealsBinding;

public class DealsFragment extends Fragment {

    private DealsViewModel dealsViewModel;
    private FragmentDealsBinding binding;
    private Context currentContext;
    private Activity currentActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        currentActivity = getActivity();
        currentContext = getContext();
        dealsViewModel =
                new ViewModelProvider(this).get(DealsViewModel.class);

        binding = FragmentDealsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        LinearLayout layout = root.findViewById(R.id.dealsLayout);

        CardView proposal = layout.findViewById(R.id.proposal);

        proposal.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        NavController navController = NavHostFragment.findNavController(DealsFragment.this);
                        navController.navigate(R.id.action_nav_deals_proposal);
                    }
                }
        );


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