package org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule;

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
import com.example.tusdkdemohelper.R;

import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  14:08
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class PlasticAdapter extends BaseAdapter<PlasticAdapter.PlasticViewHolder, PlasticFunction> {

    // 当前选中
    private int mCurrentPosition = -1;

    protected PlasticAdapter(Context context, List<PlasticFunction> itemList) {
        super(context, itemList);
    }

    @Override
    protected PlasticViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlasticViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_plastic_module_item, parent,false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final PlasticViewHolder holder, final int position, final PlasticFunction item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(position, holder, item);
                }
            }
        });
        holder.mPlasticTitle.setText(item.title);
        if (mCurrentPosition == position) {
            holder.mPlasticTitle.setTextColor(Color.parseColor("#FFFFB602"));
            Glide.with(mContext).load(item.selIconId).into(holder.mPlasticIcon);
        } else {
            holder.mPlasticTitle.setTextColor(Color.parseColor("#FF999999"));
            Glide.with(mContext).load(item.defIconId).into(holder.mPlasticIcon);
        }
    }

    @Override
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    @Override
    public void setCurrentPosition(int position) {
        int lastPosition = mCurrentPosition;
        this.mCurrentPosition = position;
        notifyItemChanged(lastPosition);
        if (mCurrentPosition != -1)
            notifyItemChanged(mCurrentPosition);
    }

    public static class PlasticViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPlasticIcon;
        public TextView mPlasticTitle;

        public PlasticViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlasticIcon = itemView.findViewById(R.id.lsq_plastic_item_icon);
            mPlasticTitle = itemView.findViewById(R.id.lsq_plastic_item_title);
        }
    }
}
