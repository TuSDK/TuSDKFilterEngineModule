package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.Config;
import com.tusdk.pulse.Engine;
import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.filters.TusdkImageFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterGroup;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterOption;
import org.lasque.tusdkdemohelper.tusdk.TabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.newUI.CustomUi.RecyclerViewTabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

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

    private TusdkImageFilter.MapPropertyBuilder builder;

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
        mParameters = new SelesParameters();
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
                mFilterGroupAdapter.setCurrentPos(-1);
                mFilterGroupAdapter.setDefaultFilterCode(null);
                changeFilter("");
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
                if (adapter.getCurrentPos() == pos) {
                    adapter.changeShowParameterState();
                    adapter.notifyItemChanged(pos);
                    mConfigView.setVisibility(adapter.isShowParameter() ? View.VISIBLE : View.GONE);
                } else {
                    if (adapter.getPosition() != mFilterGroupAdapter.getCurrentPos()){
                        mFilterGroupAdapter.setCurrentPos(adapter.getPosition());
                    }
                    adapter.setCurrentPos(pos);
                    changeFilter(item.code);
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
            mFilterGroupAdapter.setCurrentPos(mDefaultFilterGroupIndex);
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

    public void setDefaultFilter(int groupIndex,String filterCode){
        if (mFilterGroupAdapter != null){
            mFilterGroupAdapter.setCurrentPos(groupIndex);
            mFilterGroupAdapter.setDefaultFilterCode(filterCode);
        }
        mDefaultFilterGroupIndex = groupIndex;
        mDefaultFilterCode = filterCode;
    }


    public boolean changeFilter(final String code){
        boolean res = syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mController.getFilterPipe().deleteFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.Filter));
                if (TextUtils.isEmpty(code)) return true;
                final Filter filter = new Filter(getPipeContext(), TusdkImageFilter.TYPE_NAME);
                Config config = new Config();
                config.setString(TusdkImageFilter.CONFIG_NAME,code);
                filter.setConfig(config);
                boolean ret = mController.getFilterPipe().addFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.Filter),filter);

                builder = new TusdkImageFilter.MapPropertyBuilder(filter.getProperty(TusdkImageFilter.PROP_PARAM));

                mParameters = new SelesParameters();
                for (String key : builder.pars.keySet()){
                    mParameters.appendFloatArg(key,builder.pars.get(key).floatValue());
                }
                mController.getPropertyMap().put(SelesParameters.FilterModel.Filter,builder);
                mParameters.setListener(new SelesParameters.SelesParametersListener() {
                    @Override
                    public void onUpdateParameters(SelesParameters.FilterModel filterModel, String s, SelesParameters.FilterArg filterArg) {
                        builder.pars.put(filterArg.getKey(), (double) filterArg.getValue());
                        filter.setProperty(TusdkImageFilter.PROP_PARAM,builder.makeProperty());
                    }
                });

                mConfigView.setFilterArgs(mParameters);
                if (ret){
                    mController.getCurrentFilterMap().put(SelesParameters.FilterModel.Filter,filter);
                }
                return ret;
            }
        });

        return res;
    }

}
