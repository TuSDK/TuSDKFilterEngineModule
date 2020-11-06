/**
 * TuSDK
 * <p>
 * TuSDKEditorBarFragment.java
 *
 * @author H.ys
 * @Date 2019/4/30 15:38
 * @Copyright (c) 2019 tusdk.com. All rights reserved.
 */
package org.lasque.tusdkdemohelper;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.activity.TuSdkFragment;
import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterOption;
import org.lasque.tusdk.core.utils.ThreadHelper;
import org.lasque.tusdk.core.utils.json.JsonHelper;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdk.cx.api.TuFilterEngine;
import org.lasque.tusdk.modules.view.widget.sticker.StickerGroup;
import org.lasque.tusdkdemohelper.tusdk.BeautyPlasticRecyclerAdapter;
import org.lasque.tusdkdemohelper.tusdk.BeautyRecyclerAdapter;
import org.lasque.tusdkdemohelper.tusdk.MonsterFaceFragment;
import org.lasque.tusdkdemohelper.tusdk.StickerFragment;
import org.lasque.tusdkdemohelper.tusdk.StickerGroupCategories;
import org.lasque.tusdkdemohelper.tusdk.TabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.TabViewPagerAdapter;
import org.lasque.tusdkdemohelper.tusdk.filter.FilterConfigSeekbar;
import org.lasque.tusdkdemohelper.tusdk.filter.FilterConfigView;
import org.lasque.tusdkdemohelper.tusdk.filter.newFilterUI.FilterFragment;
import org.lasque.tusdkdemohelper.tusdk.filter.newFilterUI.FilterViewPagerAdapter;
import org.lasque.tusdkdemohelper.tusdk.model.PropsItemMonster;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 底部特效编辑栏
 */
public class TuSDKEditorBarFragment extends TuSdkFragment {

    public static TuSDKEditorBarFragment newInstance(FilterGroup[] mFilterGroup,boolean hasMonsterFace){
        TuSDKEditorBarFragment fragment = new TuSDKEditorBarFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FilterGroups",mFilterGroup);
        bundle.putBoolean("hasMonsterFace",hasMonsterFace);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean mHasMonsterFace = false;

    public void setFilterEngine(TuFilterEngine filterEngine) {
        this.mFilterEngine = filterEngine;

        ThreadHelper.postDelayed(new Runnable() {
            @Override
            public void run() {
                setDefaultFilter();
                switchConfigSkin(false);
                mFilterEngine.controller().changePlastic(true);
            }
        }, 500);
    }

    // TuSDK Filter Engine
    private TuFilterEngine mFilterEngine;

    // 参数调节视图
    private FilterConfigView mFilterConfigView;

    private View mFilterPanel;

    // 滤镜底部栏
    private View mFilterBottomView;

    // 贴纸视图
    private RelativeLayout mStickerLayout;
    // 取消贴纸
    private ImageView mStickerCancel;
    // 贴纸分类pager页
    private ViewPager2 mViewPager;
    // 贴纸Tab
    private TabPagerIndicator mTabPagerIndicator;
    // 贴纸Tab适配器
    private TabViewPagerAdapter mStickerPagerAdapter;
    // 贴纸数据
    private List<StickerGroupCategories> mStickerGroupCategoriesList;

    //微整形布局
    private View mBeautyPlasticBottomView;
    //微整形列表
    private RecyclerView mBeautyPlasticRecyclerView;
    // 微整形调节栏
    private FilterConfigView mBeautyConfigView;


    private ViewPager2 mFilterViewPager;
    private TabPagerIndicator mFilterTabIndicator;
    private FilterViewPagerAdapter mFilterViewPagerAdapter;
    private ImageView mFilterReset;

    private List<FilterFragment> mFilterFragments;

    private View.OnClickListener mCartoonButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mFilterBottomView.setVisibility(View.GONE);
            mBeautyPlasticBottomView.setVisibility(View.GONE);
            mStickerLayout.setVisibility(View.GONE);
            mFilterConfigView.setVisibility(View.GONE);
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener mBeautyPlasticButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mBeautyPlasticRecyclerView.setAdapter(mBeautyPlasticRecyclerAdapter);
            mBeautyPlasticBottomView.setVisibility(mBeautyPlasticBottomView.getVisibility() == View.VISIBLE
                    ? View.GONE : View.VISIBLE);
            mFilterBottomView.setVisibility(View.GONE);
            mStickerLayout.setVisibility(View.GONE);
            mFilterConfigView.setVisibility(View.GONE);
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener mFilterButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 显示 隐藏滤镜栏
            mFilterBottomView.setVisibility(mFilterBottomView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            // 隐藏微整形布局
            mBeautyPlasticBottomView.setVisibility(View.GONE);
            // 隐藏贴纸栏布局
            mStickerLayout.setVisibility(View.GONE);
            // 隐藏动漫滤镜
            // 隐藏滤镜调节栏
            mFilterConfigView.setVisibility(View.GONE);
            // 隐藏微整形调节栏
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener mBeautySkinButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mBeautyPlasticRecyclerView.setAdapter(mBeautySkinRecyclerAdapter);
            mBeautyPlasticBottomView.setVisibility(mBeautyPlasticBottomView.getVisibility() == View.VISIBLE
                    ? View.GONE : View.VISIBLE);
            mFilterBottomView.setVisibility(View.GONE);
            mStickerLayout.setVisibility(View.GONE);
            mFilterConfigView.setVisibility(View.GONE);
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };


    private View.OnClickListener mStickerButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mStickerLayout.setVisibility((mStickerLayout.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE);
            mFilterBottomView.setVisibility(View.GONE);
            mBeautyPlasticBottomView.setVisibility(View.GONE);
            mFilterConfigView.setVisibility(View.GONE);
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };


    /** 微整形默认值  Float 为进度值 */
    private HashMap<String, Float> mDefaultBeautyPercentParams = new HashMap<String, Float>() {
        {
            put("forehead",0.5f);
            put("chinSize",0.5f);
            put("browPosition",0.5f);
            put("archEyebrow",0.5f);
            put("eyeSize",0.5f);
            put("eyeAngle",0.5f);
            put("eyeDis",0.5f);
            put("noseSize",0.5f);
            put("mouthWidth",0.5f);
            put("lips",0.5f);
            put("jawSize",0.5f);
        }
    };

    /** 微整形参数 */
    private List<String> mBeautyPlastics = new ArrayList(){
        {
            add("reset");
            add("eyeSize");
            add("chinSize");
            add("noseSize");
            add("mouthWidth");
            add("lips");
            add("archEyebrow");
            add("browPosition");
            add("jawSize");
            add("eyeAngle");
            add("eyeDis");
            add("forehead");
        }
    };

    /**
     * 微整形适配器
     */
    private BeautyPlasticRecyclerAdapter mBeautyPlasticRecyclerAdapter;

    /**
     * 美肤适配器
     */
    private BeautyRecyclerAdapter mBeautySkinRecyclerAdapter;

    private SelesParameters mSkinParameters;

    private List<FilterGroup> mFilterGroups;

    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_parent_wrap_layout");
    }

    private View mParentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setRootViewLayoutId(getLayoutId());
        mHasMonsterFace = getArguments().getBoolean("hasMonsterFace",false);
        if (getArguments().containsKey("FilterGroups")){
            mFilterGroups = Arrays.asList(((FilterGroup[]) getArguments().getSerializable("FilterGroups")));
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initCreateView() {

    }

    @Override
    protected void loadView(ViewGroup viewGroup) {
        loadView();
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {

    }

    private void loadView() {
        initTuSDKViews();
    }

    private void initTuSDKViews() {
        // 微整形布局
        mBeautyPlasticBottomView = this.getViewById("lsq_beauty_bottom_view");
        // 微整形列表
        mBeautyPlasticRecyclerView = this.getViewById("lsq_beauty_recyclerView");
        // 微整形调节栏
        mBeautyConfigView = this.getViewById("lsq_beauty_config_view");
        if (mBeautyConfigView != null)
            mBeautyConfigView.setIgnoredKeys(new String[]{});


        // 滤镜调节栏
        mFilterConfigView = this.getViewById("lsq_filter_config_view");
        // 滤镜列表
        mFilterPanel = this.getViewById("lsq_filter_panel");
        // 滤镜布局
        mFilterBottomView = this.getViewById("lsq_filter_group_bottom_view_wrap");
        mFilterConfigView.setSeekBarDelegate(mFilterConfigViewSeekBarDelegate);


        // 贴纸布局
        mStickerLayout = this.getViewById("lsq_record_sticker_layout");
        // 贴纸Pager页
        mViewPager = this.getViewById("lsq_viewPager");
        // 贴纸Tab
        mTabPagerIndicator = this.getViewById("lsq_TabIndicator");
        // 贴纸取消按钮
        mStickerCancel = this.getViewById("lsq_cancel_button");

        // 准备贴纸视图
        prepareStickerViews();

        // 准备美肤视图
        prepareBeautySkinViews();

        // 准备微整形视图
        prepareBeautyPlasticViews();

        if (mFilterGroups != null){
            prepareFilterGroupsViews();
        }
    }

    private String mCurrentFilterCode = "";

    private void prepareFilterGroupsViews() {
        mFilterReset = this.getViewById("lsq_filter_reset");
        mFilterReset.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                mFilterEngine.controller().changeFilter(null,null);
                mFilterConfigView.setVisibility(View.GONE);
                mFilterViewPagerAdapter.notifyDataSetChanged();
            }
        });

        mFilterTabIndicator = this.getViewById("lsq_filter_tabIndicator");

        mFilterViewPager = this.getViewById("lsq_filter_view_pager");
        mFilterViewPager.requestDisallowInterceptTouchEvent(true);
        List<String> tabTitles = new ArrayList<>();
        List<FilterFragment> fragments = new ArrayList<>();
        for (FilterGroup group : mFilterGroups){
            FilterFragment fragment = FilterFragment.newInstance(group);
            fragment.setOnFilterItemClickListener(new FilterFragment.OnFilterItemClickListener() {
                @Override
                public void onFilterItemClick(String code) {
                    if (TextUtils.equals(mCurrentFilterCode,code)){
                        mFilterConfigView.setVisibility(mFilterConfigView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    } else {
                        mCurrentFilterCode = code;
                        SelesParameters selesParameters = mFilterEngine.controller().changeFilter(code,null);
                        mFilterConfigView.setFilterArgs(selesParameters);
                    }
                }
            });
            fragments.add(fragment);
            tabTitles.add(group.getName());
        }
        mFilterFragments = fragments;
        mFilterViewPagerAdapter = new FilterViewPagerAdapter(getFragmentManager(),getLifecycle(),mFilterFragments);
        mFilterViewPager.setAdapter(mFilterViewPagerAdapter);
        mFilterTabIndicator.setViewPager(mFilterViewPager,0);
        mFilterTabIndicator.setDefaultVisibleCounts(tabTitles.size());
        mFilterTabIndicator.setTabItems(tabTitles);
    }

    /********************** 微整形 ***********************

     /**
     * 初始化微整形视图
     */
    @UiThread
    public void prepareBeautyPlasticViews() {
        mBeautyPlasticRecyclerAdapter = new BeautyPlasticRecyclerAdapter(getContext(), mBeautyPlastics);
        mBeautyPlasticRecyclerAdapter.setOnBeautyPlasticItemClickListener(beautyPlasticItemClickListener);

        // 美型Bar
        mBeautyConfigView.setSeekBarDelegate(mBeautyConfigDelegate);
        mBeautyConfigView.setVisibility(View.GONE);
        mBeautyPlasticRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * 微整形Item点击事件
     */
    BeautyPlasticRecyclerAdapter.OnBeautyPlasticItemClickListener beautyPlasticItemClickListener = new BeautyPlasticRecyclerAdapter.OnBeautyPlasticItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            mBeautyConfigView.setVisibility(View.VISIBLE);
            switchBeautyPlasticConfig(position);
        }

        @Override
        public void onClear() {

            mBeautyConfigView.setVisibility(View.GONE);

            android.app.AlertDialog.Builder adBuilder = new android.app.AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            adBuilder.setTitle("是否重置？");
            adBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clearBeautyPlastic();
                    dialog.dismiss();
                }
            });
            adBuilder.show();
        }
    };

    /**
     * 切换微整形值
     *
     * @param position
     */
    private void switchBeautyPlasticConfig(int position) {

        SelesParameters selesParameters = mFilterEngine.controller().changePlastic(true);

        SelesParameters.FilterArg filterArg = selesParameters.getFilterArg(mBeautyPlastics.get(position));

        mBeautyConfigView.setFilterArgs(Arrays.asList(filterArg));
        mBeautyConfigView.setVisibility(View.VISIBLE);

    }

    /**
     * 重置微整形
     */
    private void clearBeautyPlastic() {
        mFilterEngine.controller().changePlastic(false);
    }

    /**
     * 美肤
     */
    @UiThread
    private void prepareBeautySkinViews() {
        mBeautySkinRecyclerAdapter = new BeautyRecyclerAdapter(getContext());
        mBeautySkinRecyclerAdapter.setOnSkinItemClickListener(mOnBeautyItemClickListener);

        // 美型Bar
        mBeautyConfigView.setSeekBarDelegate(mBeautyConfigDelegate);
        mBeautyConfigView.setVisibility(View.GONE);
        mBeautyPlasticRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * 美颜委托对象
     */
    private FilterConfigView.FilterConfigViewSeekBarDelegate mBeautyConfigDelegate = new FilterConfigView.FilterConfigViewSeekBarDelegate() {

        @Override
        public void onSeekbarDataChanged(FilterConfigSeekbar seekbar, SelesParameters.FilterArg arg) {
            submitPlasticFaceParamter(arg.getKey(), seekbar.getSeekbar().getProgress());
        }
    };

    /**
     * 应用整形值
     *
     * @param key
     * @param progress
     */
    private void submitPlasticFaceParamter(String key, float progress) {

    }

    BeautyRecyclerAdapter.OnBeautyItemClickListener mOnBeautyItemClickListener = new BeautyRecyclerAdapter.OnBeautyItemClickListener() {
        @Override
        public void onChangeSkin(View v, String key, boolean useSkinNatural) {
            mBeautyConfigView.setVisibility(View.VISIBLE);
            switchConfigSkin(useSkinNatural);

            SelesParameters.FilterArg filterArg = mSkinParameters.getFilterArg(key);
            mBeautyConfigView.setFilterArgs(Arrays.asList(filterArg));
        }

        @Override
        public void onClear() {
            mBeautyConfigView.setVisibility(View.GONE);
        }
    };

    /**
     * 切换美颜预设按键
     *
     * @param useSkinNatural true 自然(精准)美颜 false 极致美颜
     */
    private void switchConfigSkin(boolean useSkinNatural) {
        mSkinParameters = mFilterEngine.controller().changeSkin(useSkinNatural ? TuFilterCombo.TuComboSkinMode.Sleek : TuFilterCombo.TuComboSkinMode.Vein);
    }

    /**
     * 切换滤镜
     */
    public void setDefaultFilter() {

        if (mFilterEngine == null || mFilterGroups == null) return;
        String defaultFilterCode = mFilterGroups.get(0).getDefaultFilter().code;
        mViewPager.setCurrentItem(0);

        int mCurrentPosition = -1;
        List<FilterOption> defaultGroup = mFilterGroups.get(0).filters;
        for (int i=0;i<defaultGroup.size();i++){
            if (defaultGroup.get(i).code.equals(defaultFilterCode)){
                mCurrentPosition = i;
                break;
            }
        }
        if (mCurrentPosition != -1)
            mFilterFragments.get(0).setCurrentPos(mCurrentPosition);
    }

    // 准备贴纸view
    private void prepareStickerViews() {
        mStickerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消贴纸
                mFilterEngine.controller().sticker().setGroup(0);
                TabViewPagerAdapter.mStickerGroupId = 0;
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        });

        TabViewPagerAdapter.mStickerGroupId = 0;
        List<Fragment> fragments = new ArrayList<>();
        mStickerGroupCategoriesList = getRawStickGroupList();
        List<String> tabTitles = new ArrayList<>();
        for (StickerGroupCategories categories : mStickerGroupCategoriesList) {
            StickerFragment stickerFragment = StickerFragment.newInstance(categories);
            stickerFragment.setOnStickerItemClickListener(onStickerItemClickListener);
            fragments.add(stickerFragment);
            tabTitles.add(categories.getCategoryName());
        }
        //哈哈镜选项页,仅短视频可使用哈哈镜功能,并需要开启权限
        if (mHasMonsterFace){
            MonsterFaceFragment monsterFaceFragment = MonsterFaceFragment.newInstance();
            monsterFaceFragment.setOnStickerItemClickListener(onMonsterItemClickListener);
            fragments.add(monsterFaceFragment);
            tabTitles.add("哈哈镜");
        }
        mStickerPagerAdapter = new TabViewPagerAdapter(getFragmentManager(),fragments,getLifecycle());
        mViewPager.setAdapter(mStickerPagerAdapter);
        mTabPagerIndicator.setViewPager(mViewPager,0);
        mTabPagerIndicator.setDefaultVisibleCounts(tabTitles.size());
        mTabPagerIndicator.setTabItems(tabTitles);
    }

    /**
     * 获取贴纸
     *
     * @return
     */
    private List<StickerGroupCategories> getRawStickGroupList() {
        List<StickerGroupCategories> list = new ArrayList<StickerGroupCategories>();
        try {
            InputStream stream = getResources().openRawResource(TuSdkContext.getRawResId("customstickercategories"));

            if (stream == null) return null;

            byte buffer[] = new byte[stream.available()];
            stream.read(buffer);
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = JsonHelper.json(json);
            JSONArray jsonArray = jsonObject.getJSONArray("categories");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                StickerGroupCategories categories = new StickerGroupCategories();
                categories.setCategoryName(item.getString("categoryName"));
                List<StickerGroup> groupList = new ArrayList<StickerGroup>();
                JSONArray jsonArrayGroup = item.getJSONArray("stickers");
                for (int j = 0; j < jsonArrayGroup.length(); j++) {
                    JSONObject itemGroup = jsonArrayGroup.getJSONObject(j);
                    StickerGroup group = new StickerGroup();
                    group.groupId = itemGroup.optLong("id");
                    group.previewName = itemGroup.optString("previewImage");
                    group.name = itemGroup.optString("name");
                    groupList.add(group);
                }
                categories.setStickerGroupList(groupList);
                list.add(categories);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 贴纸点击事件
     */
    private StickerFragment.OnStickerItemClickListener onStickerItemClickListener = new StickerFragment.OnStickerItemClickListener() {
        @Override
        public void onStickerItemClick(StickerGroup itemData) {
            if (itemData != null) {
                mFilterEngine.controller().sticker().setGroup(itemData.groupId);
            }
        }
    };

    /**
     * 哈哈镜点击事件
     */
    private MonsterFaceFragment.OnMonsterItemClickListener onMonsterItemClickListener = new MonsterFaceFragment.OnMonsterItemClickListener() {
        @Override
        public void onMonsterItemClick(PropsItemMonster itemData) {
            if (itemData!=null){
                mFilterEngine.controller().changeMonster(itemData.effect());
            }
        }
    };

    /**
     * 滤镜参数调节
     */
    private FilterConfigView.FilterConfigViewSeekBarDelegate mFilterConfigViewSeekBarDelegate = new FilterConfigView.FilterConfigViewSeekBarDelegate() {
        @Override
        public void onSeekbarDataChanged(FilterConfigSeekbar seekbar, SelesParameters.FilterArg arg) {

        }
    };

    public View.OnClickListener getCartoonButtonClick() {
        return mCartoonButtonClick;
    }

    public View.OnClickListener getBeautyPlasticButtonClick() {
        return mBeautyPlasticButtonClick;
    }

    public View.OnClickListener getFilterButtonClick() {
        return mFilterButtonClick;
    }

    public View.OnClickListener getBeautySkinButtonClick() {
        return mBeautySkinButtonClick;
    }

    public View.OnClickListener getStickerButtonClick() {
        return mStickerButtonClick;
    }
}
