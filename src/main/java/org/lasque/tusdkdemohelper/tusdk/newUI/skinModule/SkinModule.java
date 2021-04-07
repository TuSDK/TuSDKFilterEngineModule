package org.lasque.tusdkdemohelper.tusdk.newUI.skinModule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.Config;
import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.FilterPipe;
import com.tusdk.pulse.filter.filters.TusdkBeautFaceV2Filter;
import com.tusdk.pulse.filter.filters.TusdkImageFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.skinModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  15:50
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class SkinModule extends BaseModule {
    public enum SkinMode{
        EXTREME(R.drawable.skin_extreme_ic,"极致美肤"),
        PRECISION(R.drawable.skin_precision_ic,"精准美肤"),
        BEAUTY(R.drawable.lsq_ic_skin_new_normal,"自然美肤"),
        EMPTY(-1,"无美颜");

        public int iconId;
        public String title;

        SkinMode(int iconId,String title){
            this.iconId = iconId;
            this.title = title;
        }
    }

    private TextView mNotTitle,mSkinModeTitle,mSkinWhiteTitle,mSkinDermabrasionTitle,mSkinRuddyTitle;
    private ImageView mNotIcon,mSkinModeIcon,mSkinWhiteIcon,mSkinDermabrasionIcon,mSkinRuddyIcon;

    private SkinMode mCurrentMode = SkinMode.EXTREME;

    private boolean isClear = false;


    public SkinModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.SKIN, context);
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_skin_module_layout,null);
    }

    @Override
    protected void findViews() {
        mNotTitle = mCurrentView.findViewById(R.id.lsq_skin_not_title);
        mSkinModeTitle = mCurrentView.findViewById(R.id.lsq_skin_mode_title);
        mSkinWhiteTitle = mCurrentView.findViewById(R.id.lsq_skin_white_title);
        mSkinDermabrasionTitle = mCurrentView.findViewById(R.id.lsq_skin_dermabrasion_title);
        mSkinRuddyTitle = mCurrentView.findViewById(R.id.lsq_skin_ruddy_title);

        mNotIcon = mCurrentView.findViewById(R.id.lsq_monster_remove);
        mSkinModeIcon = mCurrentView.findViewById(R.id.lsq_skin_mode_icon);
        mSkinWhiteIcon = mCurrentView.findViewById(R.id.lsq_skin_white_icon);
        mSkinDermabrasionIcon = mCurrentView.findViewById(R.id.lsq_skin_dermabrasion_icon);
        mSkinRuddyIcon = mCurrentView.findViewById(R.id.lsq_skin_ruddy_icon);

        mNotIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchConfigSkin(SkinMode.EMPTY);
                isClear = true;
                if (mConfigView!= null){
                    mConfigView.setVisibility(View.GONE);
                }
                Glide.with(mContext).load(R.drawable.skin_white_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_ic).into(mSkinDermabrasionIcon);
                Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);

                mSkinWhiteTitle.setTextColor(Color.parseColor("#999999"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#999999"));
                mSkinRuddyTitle.setTextColor(Color.parseColor("#999999"));
                showToast("美肤效果清除");
            }
        });
        mSkinModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode){
                    case EXTREME:
                        mCurrentMode = SkinMode.PRECISION;
                        break;
                    case PRECISION:
                        mCurrentMode = SkinMode.BEAUTY;
                        break;
                    case BEAUTY:
                        mCurrentMode = SkinMode.EXTREME;
                        break;
                    case EMPTY:
                        break;
                }
                switchConfigSkin(mCurrentMode);
                Glide.with(mContext).load(mCurrentMode.iconId).into(mSkinModeIcon);
                mSkinModeTitle.setText(mCurrentMode.title);
                showToast(mCurrentMode.title);

                if (mCurrentMode == SkinMode.BEAUTY){
                    Glide.with(mContext).load(R.drawable.ic_sharpen_norl).into(mSkinRuddyIcon);
                } else {
                    Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);
                }
            }
        });
        mSkinWhiteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClear) switchConfigSkin(mCurrentMode);
                showConfigView("whitening");
                Glide.with(mContext).load(R.drawable.skin_white_sel_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_ic).into(mSkinDermabrasionIcon);
                if (mCurrentMode == SkinMode.BEAUTY){
                    Glide.with(mContext).load(R.drawable.ic_sharpen_norl).into(mSkinRuddyIcon);
                } else {
                    Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);
                }

                mSkinWhiteTitle.setTextColor(Color.parseColor("#FFB602"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#999999"));
                mSkinRuddyTitle.setTextColor(Color.parseColor("#999999"));
            }
        });
        mSkinDermabrasionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClear) switchConfigSkin(mCurrentMode);
                showConfigView("smoothing");

                Glide.with(mContext).load(R.drawable.skin_white_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_sel_ic).into(mSkinDermabrasionIcon);
                Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);

                mSkinWhiteTitle.setTextColor(Color.parseColor("#999999"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#FFB602"));
                if (mCurrentMode == SkinMode.BEAUTY){
                    Glide.with(mContext).load(R.drawable.ic_sharpen_norl).into(mSkinRuddyIcon);
                } else {
                    Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);
                }
            }
        });
        mSkinRuddyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClear) switchConfigSkin(mCurrentMode);
                showConfigView(mCurrentMode == SkinMode.BEAUTY ? "sharpen" : "ruddy");

                Glide.with(mContext).load(R.drawable.skin_white_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_ic).into(mSkinDermabrasionIcon);
                if (mCurrentMode == SkinMode.BEAUTY){
                    Glide.with(mContext).load(R.drawable.ic_sharpen_select).into(mSkinRuddyIcon);
                } else {
                    Glide.with(mContext).load(R.drawable.skin_red_sel_ic).into(mSkinRuddyIcon);
                }

                mSkinWhiteTitle.setTextColor(Color.parseColor("#999999"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#999999"));
                mSkinRuddyTitle.setTextColor(Color.parseColor("#FFB602"));
            }
        });

        mCurrentView.findViewById(R.id.lsq_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
    }

    @Override
    public void attach(ViewGroup parent) {
        super.attach(parent);
    }

    @Override
    public void detach(ViewGroup parent) {
        super.detach(parent);
    }

    private void showConfigView(String key) {
        if (mConfigView != null) {
            mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg(key)));
            mConfigView.setVisibility(View.VISIBLE);
        }
    }

    public void switchConfigSkin(final SkinMode skinMode) {
        isClear = false;
        syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                FilterPipe fp = mController.getFilterPipe();
                int index = ModuleController.mFilterMap.get(SelesParameters.FilterModel.SkinFace);
                mParameters = new SelesParameters();
                mParameters.appendFloatArg("whitening", 0.3f);
                mParameters.appendFloatArg("smoothing", 0.8f);
                Filter skinFilter = null;
                Config config = null;
                boolean ret = false;

                switch (skinMode) {
                    case EXTREME:
                        skinFilter = new Filter(fp.getContext(), TusdkImageFilter.TYPE_NAME);
                        config = new Config();
                        config.setString(TusdkImageFilter.CONFIG_NAME, TusdkImageFilter.NAME_SkinNatural);
                        skinFilter.setConfig(config);
                        TusdkImageFilter.SkinNaturalPropertyBuilder naturalPropertyBuilder = new TusdkImageFilter.SkinNaturalPropertyBuilder();
                        naturalPropertyBuilder.smoothing = 0.8;
                        naturalPropertyBuilder.fair = 0.3;
                        naturalPropertyBuilder.ruddy = 0.4;
                        mController.getPropertyMap().put(SelesParameters.FilterModel.SkinFace, naturalPropertyBuilder);
                        mParameters.appendFloatArg("ruddy", 0.4f);
                        ret = fp.addFilter(index, skinFilter);
                        skinFilter.setProperty(TusdkImageFilter.PROP_PARAM, naturalPropertyBuilder.makeProperty());
                        break;
                    case PRECISION:
                        skinFilter = new Filter(fp.getContext(), TusdkImageFilter.TYPE_NAME);
                        config = new Config();
                        config.setString(TusdkImageFilter.CONFIG_NAME, TusdkImageFilter.NAME_SkinHazy);
                        skinFilter.setConfig(config);

                        TusdkImageFilter.SkinHazyPropertyBuilder hazyPropertyBuilder = new TusdkImageFilter.SkinHazyPropertyBuilder();
                        hazyPropertyBuilder.smoothing = 0.8;
                        hazyPropertyBuilder.fair = 0.3;
                        hazyPropertyBuilder.ruddy = 0.4;

                        mController.getPropertyMap().put(SelesParameters.FilterModel.SkinFace, hazyPropertyBuilder);
                        mParameters.appendFloatArg("ruddy", 0.4f);
                        ret = fp.addFilter(index, skinFilter);
                        skinFilter.setProperty(TusdkImageFilter.PROP_PARAM, hazyPropertyBuilder.makeProperty());
                        break;
                    case BEAUTY:
                        skinFilter = new Filter(fp.getContext(), TusdkBeautFaceV2Filter.TYPE_NAME);

                        TusdkBeautFaceV2Filter.PropertyBuilder builder = new TusdkBeautFaceV2Filter.PropertyBuilder();
                        builder.smoothing = 0.8;
                        builder.whiten = 0.3;
                        builder.sharpen = 0.6f;
                        mController.getPropertyMap().put(SelesParameters.FilterModel.SkinFace, builder);
                        mParameters.appendFloatArg("sharpen", 0.6f);
                        ret = fp.addFilter(index, skinFilter);
                        skinFilter.setProperty(TusdkBeautFaceV2Filter.PROP_PARAM, builder.makeProperty());

                        break;
                    case EMPTY:
                        isClear = true;
                        ret = fp.deleteFilter(index);
                        break;
                }

                if (skinMode == SkinMode.EMPTY) {
                    mController.getCurrentFilterMap().remove(SelesParameters.FilterModel.SkinFace);
                } else {
                    mController.getCurrentFilterMap().put(SelesParameters.FilterModel.SkinFace, skinFilter);
                }

                mParameters.setListener(new SelesParameters.SelesParametersListener() {
                    @Override
                    public void onUpdateParameters(SelesParameters.FilterModel filterModel, String s, final SelesParameters.FilterArg filterArg) {
                        syncRun(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                boolean ret = false;
                                Object skinProperty = mController.getPropertyMap().get(SelesParameters.FilterModel.SkinFace);
                                Filter currentFilter = mController.getCurrentFilterMap().get(SelesParameters.FilterModel.SkinFace);
                                String key = filterArg.getKey();
                                double progress = filterArg.getValue();

                                switch (mCurrentMode) {
                                    case EXTREME:
                                        TusdkImageFilter.SkinNaturalPropertyBuilder naturalPropertyBuilder = (TusdkImageFilter.SkinNaturalPropertyBuilder) skinProperty;
                                        switch (key) {
                                            case "whitening":
                                                naturalPropertyBuilder.fair = progress;
                                                break;
                                            case "smoothing":
                                                naturalPropertyBuilder.smoothing = progress;
                                                break;
                                            case "ruddy":
                                                naturalPropertyBuilder.ruddy = progress;
                                                break;
                                        }

                                        ret = currentFilter.setProperty(TusdkImageFilter.PROP_PARAM, naturalPropertyBuilder.makeProperty());
                                        break;
                                    case PRECISION:
                                        TusdkImageFilter.SkinHazyPropertyBuilder hazyPropertyBuilder = (TusdkImageFilter.SkinHazyPropertyBuilder) skinProperty;
                                        switch (key) {
                                            case "whitening":
                                                hazyPropertyBuilder.fair = progress;
                                                break;
                                            case "smoothing":
                                                hazyPropertyBuilder.smoothing = progress;
                                                break;
                                            case "ruddy":
                                                hazyPropertyBuilder.ruddy = progress;
                                                break;
                                        }

                                        ret = currentFilter.setProperty(TusdkImageFilter.PROP_PARAM, hazyPropertyBuilder.makeProperty());
                                        break;
                                    case BEAUTY:
                                        TusdkBeautFaceV2Filter.PropertyBuilder faceProperty = (TusdkBeautFaceV2Filter.PropertyBuilder) skinProperty;
                                        switch (key) {
                                            case "whitening":
                                                faceProperty.whiten = progress;
                                                break;
                                            case "smoothing":
                                                faceProperty.smoothing = progress;
                                                break;
                                            case "sharpen":
                                                faceProperty.sharpen = progress;
                                                break;
                                        }
                                        ret = currentFilter.setProperty(TusdkBeautFaceV2Filter.PROP_PARAM, faceProperty.makeProperty());
                                        break;
                                    case EMPTY:
                                        break;
                                }
                                return ret;
                            }
                        });
                    }
                });

                return ret;
            }
        });
    }
}
