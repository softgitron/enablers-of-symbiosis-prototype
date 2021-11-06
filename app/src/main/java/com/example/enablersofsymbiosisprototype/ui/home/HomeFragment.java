package com.example.enablersofsymbiosisprototype.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.enablersofsymbiosisprototype.R;
import com.example.enablersofsymbiosisprototype.data.Notifications;
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = NavHostFragment.findNavController(this);

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Define buttons
        binding.marketplaceButtonLayout.setOnClickListener(view ->
                navController.navigate(R.id.action_nav_home_to_nav_marketplace));
        binding.farmMapButtonLayout.setOnClickListener(view ->
                navController.navigate(R.id.action_nav_home_to_nav_farm_map));
        binding.dealsButtonLayout.setOnClickListener(view ->
                navController.navigate(R.id.action_nav_home_to_nav_deals));
        binding.myFarmButtonLayout.setOnClickListener(view ->
                navController.navigate(R.id.action_nav_home_to_nav_my_farm));

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Update notifications based on user's notification status.
        Notifications notifications = Users.getInstance().currentUser.notifications;
        if (notifications == null) {
            binding.marketplaceNotificationLayout.setVisibility(View.INVISIBLE);
            binding.farmMapNotificationLayout.setVisibility(View.INVISIBLE);
            binding.dealsNotificationLayout.setVisibility(View.INVISIBLE);
            binding.myFarmNotificationLayout.setVisibility(View.INVISIBLE);
            binding.marketplaceNotificationIndicator.setVisibility(View.INVISIBLE);
            binding.farmMapNotificationIndicator.setVisibility(View.INVISIBLE);
            binding.dealsNotificationIndicator.setVisibility(View.INVISIBLE);
            binding.myFarmNotificationIndicator.setVisibility(View.INVISIBLE);
        } else {
            updateNotification(
                    notifications.marketplaceTitle,
                    notifications.marketplaceDescription,
                    binding.marketplaceNotificationLayout,
                    binding.marketplaceNotificationTitle,
                    binding.marketplaceNotificationDescription,
                    binding.marketplaceNotificationIndicator);
            updateNotification(
                    notifications.farmMapTitle,
                    notifications.farmMapDescription,
                    binding.farmMapNotificationLayout,
                    binding.farmMapNotificationTitle,
                    binding.farmMapNotificationDescription,
                    binding.farmMapNotificationIndicator);
            updateNotification(
                    notifications.dealsTitle,
                    notifications.dealsDescription,
                    binding.dealsNotificationLayout,
                    binding.dealsNotificationTitle,
                    binding.dealsNotificationDescription,
                    binding.dealsNotificationIndicator);
            updateNotification(
                    notifications.myFarmTitle,
                    notifications.myFarmDescription,
                    binding.myFarmNotificationLayout,
                    binding.myFarmNotificationTitle,
                    binding.myFarmNotificationDescription,
                    binding.myFarmNotificationIndicator);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateNotification(
            String title,
            String description,
            LinearLayout layout,
            TextView titleView,
            TextView descriptionView,
            View notificationIndicator)
    {
        if ((title == null || title.isEmpty()) && (description == null || description.isEmpty())) {
            layout.setVisibility(View.INVISIBLE);
            notificationIndicator.setVisibility(View.INVISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
            notificationIndicator.setVisibility(View.VISIBLE);
            titleView.setText(title);
            descriptionView.setText(description);
        }
    }
}