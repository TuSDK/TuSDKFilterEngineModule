package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  14:33
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public abstract class BaseAdapter<H extends RecyclerView.ViewHolder,Y> extends RecyclerView.Adapter<H> {
    protected OnItemClickListener<H,Y> mOnClickListener;
    protected List<Y> mItemList;
    protected Context mContext;
    protected int mCurrentPos = -1;

    protected BaseAdapter(Context context, List<Y> itemList){
        this.mItemList = itemList;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener<H,Y> onItemClickListener){
        this.mOnClickListener = onItemClickListener;
    }

    public int getCurrentPos(){
        return mCurrentPos;
    }

    public void setCurrentPos(int pos){
        int lastPos = mCurrentPos;
        notifyItemChanged(lastPos);
        mCurrentPos = pos;
        if (mCurrentPos != -1){
            notifyItemChanged(mCurrentPos);
        }
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onChildCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        onChildBindViewHolder(holder, position,mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    protected abstract H onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType);
    protected abstract void onChildBindViewHolder(@NonNull H holder, int position, Y item);


}
