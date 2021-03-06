package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.lipstick;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.tusdkdemohelper.R;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticTypes;

import java.util.List;

/**
 * TuSDK
 * panel.lipstick
 * droid-sdk-video-refresh
 *
 * @author H.ys
 * @Date 2020/10/20  15:09
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
class LipstickAdapter extends BaseAdapter<LipstickAdapter.LipstickViewHolder,CosmeticTypes.LipstickType> {

    protected LipstickAdapter(List<CosmeticTypes.LipstickType> itemList, Context context) {
        super(context,itemList);
    }

    @Override
    protected LipstickViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lipstrick_layout, parent, false);
        return new LipstickViewHolder(itemView);
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final LipstickViewHolder holder, final int position, final CosmeticTypes.LipstickType item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(position,holder,item);
                }
            }
        });
        Glide.with(mContext).load(item.mIconId).apply(RequestOptions.circleCropTransform()).into(holder.mIcon);
        holder.mTitle.setText(item.mTitleId);
        if (mCurrentPos == position){
            holder.mSelect.setVisibility(View.VISIBLE);
            holder.mIcon.setColorFilter(Color.parseColor("#ffcc00"));
        } else {
            holder.mSelect.setVisibility(View.GONE);
            holder.mIcon.clearColorFilter();
        }
    }

    public static class LipstickViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIcon;
        public TextView mTitle;
        public ImageView mSelect;

        public LipstickViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.lsq_cosmetic_item_icon);
            mTitle = itemView.findViewById(R.id.lsq_cosmetic_item_title);
            mSelect = itemView.findViewById(R.id.lsq_cosmetic_item_sel);
        }
    }
}
