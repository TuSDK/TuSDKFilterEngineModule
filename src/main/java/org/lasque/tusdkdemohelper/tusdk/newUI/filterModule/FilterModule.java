package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;
import org.lasque.tusdk.core.utils.ThreadHelper;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

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
public class FilterModule extends BaseModule {

    private ImageView mBackView;
    private ImageView mFilterRemoveView;
    private RecyclerView mFilterGroupList;

    private HorizontalScrollView mFilterScrollView;

    private FilterGroupAdapter mFilterGroupAdapter;

    private List<FilterGroup> mFilterGroups;

    private List<String> mColorList;

    private int mDefaultFilterGroupIndex = -1;
    private String mDefaultFilterCode;


    public FilterModule(ModuleController controller, Context context, List<FilterGroup> filterGroups, List<String> colorList) {
        super(controller, FunctionsType.FILTER, context);
        this.mFilterGroups = filterGroups;
        this.mColorList = colorList;
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_filter_module_layout, null);
    }

    @Override
    protected void findViews() {
        mFilterScrollView = mCurrentView.findViewById(R.id.lsq_filter_hsv);
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
                mFilterEngine.controller().changeFilter(null, null);
            }
        });
        mFilterGroupList = mCurrentView.findViewById(R.id.lsq_filter_group_list);
        mFilterGroupAdapter = new FilterGroupAdapter(mContext, mFilterGroups, mColorList);
        mFilterGroupAdapter.setOnItemClickListener(new OnItemClickListener<FilterGroupAdapter.FilterViewHolder, FilterGroup>() {
            @Override
            public void onItemClick(final int pos, FilterGroupAdapter.FilterViewHolder holder, FilterGroup item) {
                mFilterGroupAdapter.setCurrentPos(-1);
                mFilterGroupList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFilterGroupAdapter.setCurrentPos(pos);
                    }
                },10);
            }
        });
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
                    adapter.setCurrentPos(pos);
                    mParameters = mFilterEngine.controller().changeFilter(item.code, mParameters);
                }
                mConfigView.setFilterArgs(mParameters);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFilterGroupList.setNestedScrollingEnabled(false);
        mFilterGroupList.setHasFixedSize(true);
        mFilterGroupList.setLayoutManager(linearLayoutManager);
        mFilterGroupList.setAdapter(mFilterGroupAdapter);
        if (mDefaultFilterGroupIndex != -1){
            mFilterGroupAdapter.setCurrentPos(mDefaultFilterGroupIndex);
        }
    }

    @Override
    public void setParameters(SelesParameters parameters) {
        super.setParameters(parameters);
    }

    public void setDefaultFilter(int groupIndex,String filterCode){
        if (mFilterGroupAdapter != null){
            mFilterGroupAdapter.setCurrentPos(groupIndex);
            mFilterGroupAdapter.setDefaultFilterCode(filterCode);
        }
        mDefaultFilterGroupIndex = groupIndex;
        mDefaultFilterCode = filterCode;
    }
}
