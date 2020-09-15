package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.filterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  17:41
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
class FilterItemAdapter extends BaseAdapter<FilterItemAdapter.FilterItemViewHolder, FilterOption> {

    private int mSelectColor;

    private int mTitleColor;

    // 当前选中
    private int mCurrentPosition = -1;

    private String mDefaultFilterCode;

    // 是否显示调节图
    private boolean isShowParameter = false;

    private int mPosition = -1;

    protected FilterItemAdapter(Context context, String color,List<FilterOption> itemList,int position) {
        super(context, itemList);
        mSelectColor = Color.parseColor("#66" + color);
        mTitleColor = Color.parseColor("#FF" + color);
        mPosition = position;
    }

    public int getPosition(){
        return mPosition;
    }

    @Override
    protected FilterItemViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_filter_item,parent,false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final FilterItemViewHolder holder, final int position, final FilterOption item) {
        String filterCode = item.code;
        String imageCode = filterCode.toLowerCase().replaceAll("_","");
        String filterImageName = getThumbPrefix() + imageCode;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!= null) {
                    mOnClickListener.onItemClick(position,holder,item);
                }
            }
        });
        if (!TextUtils.isEmpty(mDefaultFilterCode) && mCurrentPosition == -1){
            if (item.code.equals(mDefaultFilterCode)){
                mCurrentPosition = position;
            }
        }

        if (position == mCurrentPosition){
            holder.mFilterIcon.setColorFilter(mSelectColor);
            holder.mConfigIcon.setVisibility(isShowParameter ? View.VISIBLE : View.GONE);
        } else {
            holder.mFilterIcon.clearColorFilter();
            holder.mConfigIcon.setVisibility(View.GONE);
        }

        Bitmap filterImage = TuSdkContext.getRawBitmap(filterImageName);
        if (filterImage != null)
        {
            holder.mFilterIcon.setImageBitmap(filterImage);
        }
        holder.mFilterTitle.setText(item.getName());
        holder.mFilterTitle.setBackgroundColor(mTitleColor);
    }

    /**
     * 缩略图前缀
     *
     * @return
     */
    protected String getThumbPrefix()
    {
        return "lsq_filter_thumb_";
    }

    public void changeShowParameterState(){
        isShowParameter = !isShowParameter;
    }

    public boolean isShowParameter(){
        return isShowParameter;
    }

    @Override
    public void setCurrentPosition(int position){
        int lastPosition = mCurrentPosition;
        this.mCurrentPosition = position;
        notifyItemChanged(lastPosition);
        if (mCurrentPosition != -1)
            notifyItemChanged(mCurrentPosition);
    }

    public void setDefaultFilterCode(String filterCode){
        mDefaultFilterCode = filterCode;
    }

    @Override
    public int getCurrentPosition(){
        return mCurrentPosition;
    }


    public static class FilterItemViewHolder extends RecyclerView.ViewHolder{

        public TextView mFilterTitle;
        public ImageView mFilterIcon;
        public ImageView mConfigIcon;

        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilterTitle = itemView.findViewById(R.id.lsq_filter_title);
            mFilterIcon = itemView.findViewById(R.id.lsq_filter_icon);
            mConfigIcon = itemView.findViewById(R.id.lsq_config_icon);
        }
    }
}
