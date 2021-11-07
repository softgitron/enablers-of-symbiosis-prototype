package com.example.enablersofsymbiosisprototype.ui.userMenu;

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
import com.example.enablersofsymbiosisprototype.data.User;
import com.example.enablersofsymbiosisprototype.data.Users;
import com.example.enablersofsymbiosisprototype.ui.recyclers.*;
import com.example.enablersofsymbiosisprototype.databinding.FragmentUserMenuBinding;

public class UserMenuFragment extends Fragment {

    private UserMenuViewModel userMenuViewModel;
    private FragmentUserMenuBinding binding;

    private StandardListModel[] userListItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userMenuViewModel =
                new ViewModelProvider(this).get(UserMenuViewModel.class);

        binding = FragmentUserMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textSlideshow;
        //dealsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Recycler view related stuff.

        RecyclerView userList = binding.userList;
        userList.setNestedScrollingEnabled(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        userList.setLayoutManager(layoutManager);

        // Set decoration to Recycler view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(userList.getContext(), LinearLayoutManager.VERTICAL);
        userList.addItemDecoration(dividerItemDecoration);

        // specify an adapter
        userListItems = adaptUsersToList();
        StandardListAdapter mAdapter = new StandardListAdapter(userListItems, new UserListClickListener());
        userList.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private StandardListModel[] adaptUsersToList() {
        Users users = Users.getInstance();
        StandardListModel[] adaptedUserList = new StandardListModel[users.list.size()];

        int index = 0;
        for (User user : users.list) {
            adaptedUserList[index] = new StandardListModel();

            adaptedUserList[index].title = String.format("%s %s", user.firstName, user.lastName);

            String phoneNumber = String.valueOf(user.phoneNumber)
                    .replaceFirst("(\\d{3})(\\d{2})(\\d{3})(\\d+)", "+$1 $2 $3 $4");
            adaptedUserList[index].description = String.format("%s\n%s", phoneNumber, user.email);

            adaptedUserList[index].thumbnailReference = user.profilePictureReference;

            adaptedUserList[index].extras.put("user", user);

            index++;
        }

        return adaptedUserList;
    }

    private class UserListClickListener implements View.OnClickListener {
        @Override
        public void onClick(View clickedView) {
            int itemPosition = binding.userList.getChildLayoutPosition(clickedView);
            Users.getInstance().currentUser = (User)userListItems[itemPosition].extras.get("user");

            NavController navController = NavHostFragment.findNavController(UserMenuFragment.this);
            navController.navigate(R.id.action_global_nav_home);
        }
    }
}