package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tusdkdemohelper.R;

import org.lasque.tusdkpulse.core.TuSdkContext;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterGroup;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterOption;
import org.lasque.tusdkpulse.core.utils.ThreadHelper;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.filterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  17:21
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class FilterGroupAdapter extends BaseAdapter<FilterGroupAdapter.FilterViewHolder, FilterGroup> {

    private Map<Long, FilterItemAdapter> mFilterAdapterMap;
    private List<String> mColorList;

    private int mCurrentPosition = -1;

    private String mDefaultFilterCode;

    private OnItemClickListener<FilterItemAdapter.FilterItemViewHolder, FilterOption> mFilterItemClickListener;

    protected FilterGroupAdapter(Context context, List<FilterGroup> itemList, List<String> colorList) {
        super(context, itemList);
        mFilterAdapterMap = new HashMap<>();
        mColorList = colorList;
    }

    public void setFilterItemClickListener(OnItemClickListener<FilterItemAdapter.FilterItemViewHolder, FilterOption> filterItemClickListener) {
        this.mFilterItemClickListener = filterItemClickListener;
    }

    @Override
    protected FilterViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_filter_group_item, parent, false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final FilterViewHolder holder, final int position, final FilterGroup item) {
        String filterCode = item.getDefaultFilter().code;
        String imageCode = filterCode.toLowerCase().replaceAll("_", "");
        String filterImageName = getThumbPrefix() + imageCode;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    for (FilterItemAdapter adapter : mFilterAdapterMap.values()) {
                        adapter.setCurrentPos(-1);
                    }
                    mOnClickListener.onItemClick(position, holder, item);
                }
            }
        });
        holder.mFilterGroupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentPos(-1);
            }
        });
        FilterItemAdapter adapter = mFilterAdapterMap.get(item.groupId);
        if (adapter == null) {
            adapter = new FilterItemAdapter(mContext, mColorList.get(position), item.filters,position);
            adapter.setOnItemClickListener(mFilterItemClickListener);
            mFilterAdapterMap.put(item.groupId, adapter);
        }
        adapter.setDefaultFilterCode(mDefaultFilterCode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mFilterList.setLayoutManager(linearLayoutManager);
        holder.mFilterList.setAdapter(adapter);

        holder.mFilterGroupTitle.setText(item.getName());
        if (position == mCurrentPosition) {
            holder.mFilterGroupIcon.setColorFilter(Color.parseColor(mColorList.get(position)));
            Glide.with(mContext)
                    .asBitmap()
                    .load(TuSdkContext.getRawBitmap(filterImageName))
                    .transform(new BlurTransformation(50))
                    .into(holder.mFilterGroupIcon);
            holder.mFilterGroupClose.setVisibility(View.VISIBLE);
            holder.mLineView.setVisibility(View.VISIBLE);
            holder.mFilterList.setVisibility(View.VISIBLE);
            holder.mFilterGroupTitle.setBackgroundColor(Color.TRANSPARENT);
        } else {
            Glide.with(mContext).load(TuSdkContext.getRawBitmap(filterImageName)).into(holder.mFilterGroupIcon);
            holder.mFilterGroupIcon.clearColorFilter();
            holder.mFilterGroupClose.setVisibility(View.GONE);
            holder.mLineView.setVisibility(View.GONE);
            holder.mFilterList.setVisibility(View.GONE);
            holder.mFilterGroupTitle.setBackgroundColor(Color.parseColor(mColorList.get(position)));
        }

    }

    /**
     * 缩略图前缀
     *
     * @return
     */
    protected String getThumbPrefix() {
        return "lsq_filter_thumb_";
    }

    public FilterItemAdapter getItemAdapter(long pos) {
        return mFilterAdapterMap.get(pos);
    }

    public void setDefaultFilterCode(String filterCode){
        mDefaultFilterCode = filterCode;
    }

    static public class FilterViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView mFilterList;

        public View mLineView;

        public ImageView mFilterGroupIcon;

        public TextView mFilterGroupTitle;

        public ImageView mFilterGroupClose;


        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilterList = itemView.findViewById(R.id.lsq_filter_item_list);
            mLineView = itemView.findViewById(R.id.lsq_filter_group_line);
            mFilterGroupIcon = itemView.findViewById(R.id.lsq_filter_group_icon);
            mFilterGroupTitle = itemView.findViewById(R.id.lsq_filter_group_title);
            mFilterGroupClose = itemView.findViewById(R.id.lsq_filter_group_close);
        }
    }
}
