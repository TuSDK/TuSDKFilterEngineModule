package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseAdapter;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.filterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  17:21
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class FilterGroupAdapter2 extends BaseAdapter<FilterGroupAdapter2.FilterViewHolder, FilterGroup> {

    private Map<Long, FilterItemAdapter> mFilterAdapterMap;
    private Map<Integer,FilterViewHolder> mFilterViews;
    private List<String> mColorList;

    private int mCurrentPosition = -1;

    private String mDefaultFilterCode;

    private OnItemClickListener<FilterItemAdapter.FilterItemViewHolder, FilterOption> mFilterItemClickListener;

    protected FilterGroupAdapter2(Context context, List<FilterGroup> itemList, List<String> colorList) {
        super(context, itemList);
        mFilterAdapterMap = new HashMap<>();
        mColorList = colorList;
        mFilterViews = new HashMap<>();
    }

    public void setFilterItemClickListener(OnItemClickListener<FilterItemAdapter.FilterItemViewHolder, FilterOption> filterItemClickListener) {
        this.mFilterItemClickListener = filterItemClickListener;
    }

    @Override
    protected FilterViewHolder onChildCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_filter_group_item_2, parent, false));
    }

    @Override
    protected void onChildBindViewHolder(@NonNull final FilterViewHolder holder, final int position, final FilterGroup item) {
        String filterCode = item.getDefaultFilter().code;
        String imageCode = filterCode.toLowerCase().replaceAll("_", "");
        String filterImageName = getThumbPrefix() + imageCode;
        FilterItemAdapter adapter = mFilterAdapterMap.get(item.groupId);
        if (adapter == null) {
            adapter = new FilterItemAdapter(mContext, mColorList.get(position), item.filters,position);
            adapter.setOnItemClickListener(mFilterItemClickListener);
            mFilterAdapterMap.put(item.groupId, adapter);
            mFilterViews.put(position,holder);
        }
        adapter.setDefaultFilterCode(mDefaultFilterCode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mFilterList.setNestedScrollingEnabled(false);
        holder.mFilterList.setLayoutManager(linearLayoutManager);
        holder.mFilterList.setAdapter(adapter);
        if (mCurrentPosition != position){
            adapter.setCurrentPos(-1);
        }

        if (position == mItemList.size() -1){
            holder.mLineView.setVisibility(View.GONE);
        } else {
            holder.mLineView.setVisibility(View.VISIBLE);
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

    public FilterViewHolder getItemViewHolder(int pos){
        return mFilterViews.get(pos);
    }

    public void setDefaultFilterCode(String filterCode){
        mDefaultFilterCode = filterCode;
    }

    static public class FilterViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView mFilterList;

        public View mLineView;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilterList = itemView.findViewById(R.id.lsq_filter_item_list);
            mLineView = itemView.findViewById(R.id.lsq_filter_group_line);
        }
    }
}
