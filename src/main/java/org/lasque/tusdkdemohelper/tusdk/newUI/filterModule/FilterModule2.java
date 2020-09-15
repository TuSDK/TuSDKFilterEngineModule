package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;
import org.lasque.tusdkdemohelper.tusdk.TabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.newUI.CustomUi.RecyclerViewTabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.filterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  17:19
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class FilterModule2 extends BaseModule {

    private ImageView mBackView;
    private ImageView mFilterRemoveView;
    private RecyclerView mFilterGroupList;

    private RecyclerViewTabPagerIndicator mFilterGroupNameTabs;

//    private RecyclerView mFilterGroupNameList;

    private FilterGroupAdapter2 mFilterGroupAdapter;

//    private FilterGroupNameAdapter mFilterGroupNameAdapter;

    private List<FilterGroup> mFilterGroups;

    private List<String> mColorList;

    private List<String> mFilterGroupNames;

    private int mDefaultFilterGroupIndex = -1;
    private String mDefaultFilterCode;

    private RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {
        @Override protected int getVerticalSnapPreference() {
            return LinearSmoothScroller.SNAP_TO_START;
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return LinearSmoothScroller.SNAP_TO_START;
        }
    };


    public FilterModule2(ModuleController controller, Context context, List<FilterGroup> filterGroups, List<String> colorList) {
        super(controller, FunctionsType.FILTER, context);
        this.mFilterGroups = filterGroups;
        this.mColorList = colorList;
        mFilterGroupNames = new ArrayList<>();
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_2_layout, null);
    }

    private int targetPos = -1;


    @Override
    protected void findViews() {
        mBackView = mCurrentView.findViewById(R.id.lsq_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
        mFilterRemoveView = mCurrentView.findViewById(R.id.lsq_filter_remove);
        mFilterRemoveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterGroupAdapter.setCurrentPosition(-1);
                mFilterGroupAdapter.setDefaultFilterCode(null);
                mFilterEngine.controller().changeFilter(null, null);
                showToast("滤镜重置");
            }
        });
        mFilterGroupList = mCurrentView.findViewById(R.id.lsq_filter_group_list);
        mFilterGroupAdapter = new FilterGroupAdapter2(mContext, mFilterGroups, mColorList);
        mFilterGroupAdapter.setFilterItemClickListener(new OnItemClickListener<FilterItemAdapter.FilterItemViewHolder, FilterOption>() {
            @Override
            public void onItemClick(int pos, FilterItemAdapter.FilterItemViewHolder holder, FilterOption item) {
                mFilterGroupAdapter.setDefaultFilterCode(null);
                FilterItemAdapter adapter = mFilterGroupAdapter.getItemAdapter(item.groupId);
                if (adapter.getCurrentPosition() == pos) {
                    adapter.changeShowParameterState();
                    adapter.notifyItemChanged(pos);
                    mConfigView.setVisibility(adapter.isShowParameter() ? View.VISIBLE : View.GONE);
                } else {
                    if (adapter.getPosition() != mFilterGroupAdapter.getCurrentPosition()){
                        mFilterGroupAdapter.setCurrentPosition(adapter.getPosition());
                    }
                    adapter.setCurrentPosition(pos);
                    mParameters = mFilterEngine.controller().changeFilter(item.code, mParameters);
                }
                mConfigView.setFilterArgs(mParameters);
            }
        });

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFilterGroupList.setNestedScrollingEnabled(false);
        mFilterGroupList.setHasFixedSize(true);
        mFilterGroupList.setLayoutManager(linearLayoutManager);
        mFilterGroupList.setAdapter(mFilterGroupAdapter);
        if (mDefaultFilterGroupIndex != -1){
            mFilterGroupAdapter.setCurrentPosition(mDefaultFilterGroupIndex);
        }
        mFilterGroupNames.clear();
        for (FilterGroup group : mFilterGroups){
            mFilterGroupNames.add(group.getName());
        }
        mFilterGroupNameTabs = mCurrentView.findViewById(R.id.lsq_TabIndicator);
        mFilterGroupNameTabs.setBaseAdapter(mFilterGroupAdapter);
        mFilterGroupNameTabs.setDefaultVisibleCounts(mFilterGroupNames.size());
        mFilterGroupNameTabs.setOnTabClickListener(new RecyclerViewTabPagerIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {
                smoothScroller.setTargetPosition(position);
                mFilterGroupList.getLayoutManager().startSmoothScroll(smoothScroller);
            }
        });
        mFilterGroupNameTabs.setTabItems(mFilterGroupNames);
        mFilterGroupNameTabs.setHighLightText(0);


        mFilterGroupList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取当前滚动到的条目位置
                    int firstIndex = linearLayoutManager.findLastVisibleItemPosition();
                    mFilterGroupNameTabs.setHighLightText(firstIndex);
                    mFilterGroupNameTabs.scroll(firstIndex,0);
                }
            }
        });


    }

    private boolean mShouldScroll;

    private int mToPosition;

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    @Override
    public void setParameters(SelesParameters parameters) {
        super.setParameters(parameters);
    }

    public void setDefaultFilter(int groupIndex,String filterCode){
        if (mFilterGroupAdapter != null){
            mFilterGroupAdapter.setCurrentPosition(groupIndex);
            mFilterGroupAdapter.setDefaultFilterCode(filterCode);
        }
        mDefaultFilterGroupIndex = groupIndex;
        mDefaultFilterCode = filterCode;
    }
}
