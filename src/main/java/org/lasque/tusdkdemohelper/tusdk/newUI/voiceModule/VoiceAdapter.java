package org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule;

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
 * org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/9/1  9:44
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class VoiceAdapter extends BaseAdapter<VoiceAdapter.VoiceViewHolder,VoiceFunction> {

    private int mCurrentPosition = -1;

    protected VoiceAdapter(Context context, List<VoiceFunction> itemList) {
        super(context, itemList);
    }

    @Override
    protected VoiceViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoiceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_voice_module_item,parent,false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final VoiceViewHolder holder, final int position, final VoiceFunction item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(position, holder, item);
                }
            }
        });
        holder.mVoiceTitle.setText(item.title);
        if (mCurrentPosition == position) {
            holder.mVoiceTitle.setTextColor(Color.parseColor("#FFFFB602"));
            Glide.with(mContext).load(item.selIconId).into(holder.mVoiceIcon);
        } else {
            holder.mVoiceTitle.setTextColor(Color.parseColor("#FF999999"));
            Glide.with(mContext).load(item.defIconId).into(holder.mVoiceIcon);
        }
    }

    public static class VoiceViewHolder extends RecyclerView.ViewHolder{

        public ImageView mVoiceIcon;
        public TextView mVoiceTitle;

        public VoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            mVoiceIcon = itemView.findViewById(R.id.lsq_voice_item_icon);
            mVoiceTitle = itemView.findViewById(R.id.lsq_voice_item_title);
        }
    }
}
