package org.lasque.tusdkdemohelper.tusdk.newUI.mainModule;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  14:27
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class FunctionMenuAdapter extends BaseAdapter<FunctionMenuAdapter.FunctionMenuViewHolder,FunctionMenuItem> {

    private int mParentWidth;

    protected FunctionMenuAdapter(Context context, List<FunctionMenuItem> itemList) {
        super(context, itemList);
    }

    @Override
    protected FunctionMenuViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tusdk_main_function_menu_item,parent,false);
        FunctionMenuViewHolder holder = new FunctionMenuViewHolder(view);
//        mParentWidth = parent.getMeasuredWidth();
//        mParentWidth = View.MeasureSpec.getSize(mParentWidth);
//        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
//        params.width = mParentWidth / mItemList.size();
        return holder;
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final FunctionMenuViewHolder holder, final int position, final FunctionMenuItem item) {
        holder.mTitle.setText(item.title);
        Glide.with(mContext).load(item.iconId).into(holder.mIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(position,holder,item);
                }
            }
        });
    }

    static public class FunctionMenuViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIcon;
        public TextView mTitle;
        public FunctionMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.lsq_function_menu_icon);
            mTitle = itemView.findViewById(R.id.lsq_function_menu_title);
        }
    }
}
