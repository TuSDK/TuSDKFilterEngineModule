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

import org.lasque.tubeautysetting.Beauty;
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
        mParameters = new SelesParameters();
        mParameters.appendFloatArg("whitening", 0.3f);
        mParameters.appendFloatArg("smoothing", 0.8f);
        switch (skinMode) {
            case EXTREME:
                mController.getBeautyManager().setBeautyStyle(Beauty.BeautySkinMode.SkinMoist);
                mParameters.appendFloatArg("ruddy", 0.4f);
                break;
            case PRECISION:
                mParameters.appendFloatArg("ruddy", 0.4f);
                mController.getBeautyManager().setBeautyStyle(Beauty.BeautySkinMode.SkinNatural);
                break;
            case BEAUTY:
                mParameters.appendFloatArg("sharpen", 0.6f);
                mController.getBeautyManager().setBeautyStyle(Beauty.BeautySkinMode.Beauty);
                break;
            case EMPTY:
                isClear = true;
                mController.getBeautyManager().setBeautyStyle(Beauty.BeautySkinMode.None);
                break;
        }

        mParameters.setListener(new SelesParameters.SelesParametersListener() {
            @Override
            public void onUpdateParameters(SelesParameters.FilterModel filterModel, String s, final SelesParameters.FilterArg filterArg) {
                String key = filterArg.getKey();
                float progress = filterArg.getValue();
                switch (key) {
                    case "whitening":
                        mController.getBeautyManager().setWhiteningLevel(progress);
                        break;
                    case "smoothing":
                        mController.getBeautyManager().setSmoothLevel(progress);
                        break;
                    case "ruddy":
                        mController.getBeautyManager().setRuddyLevel(progress);
                        break;
                    case "sharpen":
                        mController.getBeautyManager().setSharpenLevel(progress);
                        break;
                }
            }
        });
    }
}
