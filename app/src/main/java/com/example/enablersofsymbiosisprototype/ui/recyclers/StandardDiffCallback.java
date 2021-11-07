package com.example.enablersofsymbiosisprototype.ui.recyclers;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class StandardDiffCallback extends DiffUtil.Callback {
    StandardListModel[] oldItems;
    StandardListModel[] newItems;

    public StandardDiffCallback(StandardListModel[] newItems, StandardListModel[] oldItems) {
        this.newItems = newItems;
        this.oldItems = oldItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.length;
    }

    @Override
    public int getNewListSize() {
        return newItems.length;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems[oldItemPosition].thumbnailReference == newItems[newItemPosition].thumbnailReference;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems[oldItemPosition].equals(oldItems[newItemPosition]);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
