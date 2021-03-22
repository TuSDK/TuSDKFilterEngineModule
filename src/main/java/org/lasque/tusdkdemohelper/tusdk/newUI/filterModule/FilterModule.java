package org.lasque.tusdkdemohelper.tusdk.newUI.filterModule;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.Config;
import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.filters.TusdkImageFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterGroup;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterOption;
import org.lasque.tusdkpulse.core.utils.ThreadHelper;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.List;
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

    private TusdkImageFilter.MapPropertyBuilder builder;



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
                changeFilter("");
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
                    changeFilter(item.code);
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
