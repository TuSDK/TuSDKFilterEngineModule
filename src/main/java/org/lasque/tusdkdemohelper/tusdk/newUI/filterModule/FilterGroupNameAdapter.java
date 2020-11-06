package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.filterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/9/9  14:47
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
class FilterGroupNameAdapter extends BaseAdapter<FilterGroupNameAdapter.FilterGroupNameViewHolder, String> {

    public int getCurrentPos() {
        return mCurrentPosition;
    }

    private int mCurrentPosition = -1;

    private int mParentWidth;

    protected FilterGroupNameAdapter(Context context, List<String> itemList) {
        super(context, itemList);
    }

    @Override
    protected FilterGroupNameViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_filter_name_item, parent, false);
        FilterGroupNameViewHolder viewHolder = new FilterGroupNameViewHolder(view);
        mParentWidth = parent.getMeasuredWidth();
        mParentWidth = View.MeasureSpec.getSize(mParentWidth);
        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
        params.width = mParentWidth / mItemList.size();
        return viewHolder;
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final FilterGroupNameViewHolder holder, final int position, final String item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(position,holder,item);
                }
            }
        });
        holder.mFilterGroupName.setText(item);
        if (mCurrentPosition == position){
            holder.mFilterGroupName.setTextColor(Color.WHITE);
        } else {
            holder.mFilterGroupName.setTextColor(Color.parseColor("#99FFFFFF"));
        }
    }

    public void setCurrentPos(int position) {
        int lastPosition = mCurrentPosition;
        this.mCurrentPosition = position;
        notifyItemChanged(lastPosition);
        if (mCurrentPosition != -1) {
            notifyItemChanged(mCurrentPosition);
        }
    }

    static public class FilterGroupNameViewHolder extends RecyclerView.ViewHolder {

        public TextView mFilterGroupName;

        public FilterGroupNameViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilterGroupName = itemView.findViewById(R.id.lsq_filter_group_name);
        }
    }
}
