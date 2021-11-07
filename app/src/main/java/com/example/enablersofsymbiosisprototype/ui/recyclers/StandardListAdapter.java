package com.example.enablersofsymbiosisprototype.ui.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enablersofsymbiosisprototype.R;

import java.util.ArrayList;

public class StandardListAdapter extends RecyclerView.Adapter<StandardListAdapter.ViewHolder> {
    private StandardListModel[] localDataSet;
    private View.OnClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descriptionView;
        private final ImageView thumbnailView;

        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(clickListener);

            titleView = (TextView) view.findViewById(R.id.marketplaceNotificationTitle);
            descriptionView = (TextView) view.findViewById(R.id.marketplaceNotificationDescription);
            thumbnailView = (ImageView) view.findViewById(R.id.listItemThumbnail);
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescriptionView() {
            return descriptionView;
        }

        public ImageView getThumbnailView() {
            return thumbnailView;
        }
    }

    public StandardListAdapter(StandardListModel[] dataSet, View.OnClickListener clickListener) {
        localDataSet = dataSet;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item.
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_standard_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitleView().setText(localDataSet[position].title);
        viewHolder.getDescriptionView().setText(localDataSet[position].description);
        viewHolder.getThumbnailView().setImageResource(localDataSet[position].thumbnailReference);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public void updateAdapterList(StandardListModel[] dataSet) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new StandardDiffCallback(dataSet, localDataSet));
        diffResult.dispatchUpdatesTo(this);
    }
}
