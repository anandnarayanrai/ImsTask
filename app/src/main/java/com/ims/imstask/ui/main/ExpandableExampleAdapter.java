/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.ims.imstask.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.ims.imstask.R;
import com.ims.imstask.retrofit.BookingSlotsChildItem;
import com.ims.imstask.retrofit.BookingSlotsParentItem;
import com.ims.imstask.widget.ExpandableItemIndicator;

import java.util.ArrayList;

class ExpandableExampleAdapter
        extends AbstractExpandableItemAdapter<ExpandableExampleAdapter.MyGroupViewHolder, ExpandableExampleAdapter.MyChildViewHolder> {
    private static final String TAG = "MyExpandableItemAdapter";

    private final ArrayList<BookingSlotsParentItem> mItemList = new ArrayList<>();

    public ExpandableExampleAdapter(ArrayList<BookingSlotsParentItem> mItemList) {
        this.mItemList.addAll(mItemList);
        setHasStableIds(true);
    }

    public static class MyGroupViewHolder extends MyBaseViewHolder {
        public ExpandableItemIndicator mIndicator;

        public MyGroupViewHolder(View v) {
            super(v);
            mIndicator = v.findViewById(R.id.indicator);
        }
    }

    public static class MyChildViewHolder extends MyBaseViewHolder {
        public MyChildViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public int getGroupCount() {
        return mItemList.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mItemList.get(groupPosition).getChildItem().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    @Override
    public void onBindGroupViewHolder(@NonNull MyGroupViewHolder holder, int groupPosition, int viewType) {
        final BookingSlotsParentItem item = this.mItemList.get(groupPosition);
        holder.tvHeader.setText(item.getHeader());
        holder.tvChildCount.setText(item.getSlotsAvailable() + " Slots available");

        holder.itemView.setClickable(true);

        final ExpandableItemState expandState = holder.getExpandState();

        if (expandState.isUpdated()) {
            int bgResId;
            boolean animateIndicator = expandState.hasExpandedStateChanged();

            bgResId = R.drawable.bg_item_normal_state;
            holder.mContainer.setBackgroundResource(bgResId);
            holder.mIndicator.setExpandedState(expandState.isExpanded(), animateIndicator);
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    @NonNull
    public MyGroupViewHolder onCreateGroupViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_group_item, parent, false);
        return new MyGroupViewHolder(v);
    }

    @Override
    @NonNull
    public MyChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item, parent, false);
        return new MyChildViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(@NonNull MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {

        final BookingSlotsChildItem item = this.mItemList.get(groupPosition).getChildItem().get(childPosition);

        holder.tvChild.setText(item.getTimeSlots());

        int bgResId;

        if (item.isBooked()) {
            bgResId = R.drawable.bg_slots_booked_state;
        } else {
            bgResId = R.drawable.bg_slots_available_state;
        }
        holder.mContainer.setBackgroundResource(bgResId);
    }

    public static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public LinearLayout mContainer;
        public TextView tvHeader;
        public TextView tvChildCount;
        public TextView tvChild;

        public MyBaseViewHolder(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            tvHeader = v.findViewById(R.id.tvHeader);
            tvChildCount = v.findViewById(R.id.tvChildCount);
            tvChild = v.findViewById(R.id.tvChild);
        }
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check is enabled
        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
            return false;
        }

        return true;
    }
}
