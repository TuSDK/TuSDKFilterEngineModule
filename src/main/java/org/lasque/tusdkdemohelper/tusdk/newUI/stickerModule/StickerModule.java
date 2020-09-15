package org.lasque.tusdkdemohelper.tusdk.newUI.stickerModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tusdkdemohelper.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.utils.json.JsonHelper;
import org.lasque.tusdk.modules.view.widget.sticker.StickerGroup;
import org.lasque.tusdkdemohelper.tusdk.StickerFragment;
import org.lasque.tusdkdemohelper.tusdk.StickerGroupCategories;
import org.lasque.tusdkdemohelper.tusdk.TabPagerIndicator;
import org.lasque.tusdkdemohelper.tusdk.TabViewPagerAdapter;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.stickerModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  15:23
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class StickerModule extends BaseModule {

    private ImageView mBackView;

    private ImageView mStickerCancelButton;

    private TabPagerIndicator mStickerTab;

    private TabViewPagerAdapter mStickerPagerAdapter;

    private ViewPager2 mStickerViewPager;

    private List<Fragment> mFragments;

    // 贴纸数据
    private List<StickerGroupCategories> mStickerGroupCategoriesList;

    public StickerModule(ModuleController controller, Context context, FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(controller, FunctionsType.STICKER, context, fragmentManager, lifecycle);
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_record_sticker_layout, null);
    }

    @Override
    protected void findViews() {
        mBackView = mCurrentView.findViewById(R.id.lsq_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
        mStickerCancelButton = mCurrentView.findViewById(R.id.lsq_cancel_button);
        mStickerCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrePos != -1){
                    ((StickerFragment) mFragments.get(mPrePos)).clearSelect();
                }
                // 取消贴纸
                clearSticker();
            }
        });
        mStickerTab = mCurrentView.findViewById(R.id.lsq_TabIndicator);
        mStickerViewPager = mCurrentView.findViewById(R.id.lsq_viewPager);

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
        mFragments = fragments;
        mStickerPagerAdapter = new TabViewPagerAdapter(mFragmentManager, fragments, mLifecycle);
        mStickerViewPager.setAdapter(mStickerPagerAdapter);
        mStickerTab.setViewPager(mStickerViewPager, 0);
        mStickerTab.setDefaultVisibleCounts(tabTitles.size());
        mStickerTab.setTabItems(tabTitles);
    }

    /**
     * 获取贴纸
     *
     * @return
     */
    private List<StickerGroupCategories> getRawStickGroupList() {
        List<StickerGroupCategories> list = new ArrayList<StickerGroupCategories>();
        try {
            InputStream stream = mContext.getResources().openRawResource(TuSdkContext.getRawResId("customstickercategories"));

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

    private int mPrePos = -1;

    /**
     * 贴纸点击事件
     */
    private StickerFragment.OnStickerItemClickListener onStickerItemClickListener = new StickerFragment.OnStickerItemClickListener() {
        @Override
        public void onStickerItemClick(StickerGroup itemData) {
            if (itemData != null) {
                if (mPrePos != -1 && mPrePos != mStickerViewPager.getCurrentItem()){
                    ((StickerFragment) mFragments.get(mPrePos)).clearSelect();
                }
                mPrePos = mStickerViewPager.getCurrentItem();
                mFilterEngine.controller().sticker().setGroup(itemData.groupId);
                mStickerPagerAdapter.notifyDataSetChanged();
                mController.getMonsterModule().clearMonsterFace();
            } else {
                clearSticker();
            }
        }
    };

    private void clearSticker() {
        mFilterEngine.controller().sticker().setGroup(0);
        TabViewPagerAdapter.mStickerGroupId = 0;
        mStickerViewPager.getAdapter().notifyDataSetChanged();
        showToast("贴纸移除");
    }

    @Override
    public void attach(ViewGroup parent) {
        super.attach(parent);
    }

    public void clearSelect(){
        if (mPrePos != -1){
            ((StickerFragment) mFragments.get(mPrePos)).clearSelect();
            mPrePos = -1;
        }
    }
}
