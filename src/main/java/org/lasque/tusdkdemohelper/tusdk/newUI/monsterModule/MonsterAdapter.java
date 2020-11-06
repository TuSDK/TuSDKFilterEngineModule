package org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule;

import android.content.Context;
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
 * org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  17:19
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
class MonsterAdapter extends BaseAdapter<MonsterAdapter.MonsterViewHolder, MonsterFunction> {

    // 当前选中
    private int mCurrentPosition = -1;

    protected MonsterAdapter(Context context, List<MonsterFunction> itemList) {
        super(context, itemList);
    }

    @Override
    protected MonsterViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonsterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_monster_module_item, parent,false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final MonsterViewHolder holder, final int position, final MonsterFunction item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(position, holder, item);
                }
            }
        });
        holder.mMonsterTitle.setText(item.title);
        Glide.with(mContext).load(item.iconId).into(holder.mMonsterIcon);
        if (mCurrentPosition == position){
            holder.mMonsterIcon.setBackgroundResource(R.drawable.bg_yellow_border);
        } else {
            holder.mMonsterIcon.setBackgroundResource(0);
        }
    }

    public static class MonsterViewHolder extends RecyclerView.ViewHolder {

        public TextView mMonsterTitle;
        public ImageView mMonsterIcon;

        public MonsterViewHolder(@NonNull View itemView) {
            super(itemView);
            mMonsterTitle = itemView.findViewById(R.id.lsq_monster_title);
            mMonsterIcon = itemView.findViewById(R.id.lsq_monster_icon);
        }
    }
}
