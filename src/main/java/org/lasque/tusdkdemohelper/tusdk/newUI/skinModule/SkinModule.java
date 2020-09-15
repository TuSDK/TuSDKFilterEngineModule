package org.lasque.tusdkdemohelper.tusdk.newUI.skinModule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdk.impl.view.widget.TuMessageHubImpl;
import org.lasque.tusdk.impl.view.widget.TuProgressHub;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;

import java.util.Arrays;
import java.util.List;

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
        EXTREME(R.drawable.skin_extreme_ic,"极致美颜"),
        PRECISION(R.drawable.skin_precision_ic,"精准美颜"),
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

    private SkinMode mCurrentMode = SkinMode.PRECISION;

    private boolean isClear = false;


    public SkinModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.SKIN, context);
        findViews();
    }

    @Override
    public void setParameters(SelesParameters parameters) {
        super.setParameters(parameters);
        mParameters.setFilterArg("whitening",0.3f);
        mParameters.setFilterArg("smoothing",0.7f);
        mParameters.setFilterArg("ruddy",0.2f);
        TuFilterCombo.TuComboSkinMode mode = TuFilterCombo.TuComboSkinMode.Sleek;
        switch (mCurrentMode){
            case EXTREME:
                mCurrentMode = SkinMode.PRECISION;
                mode = TuFilterCombo.TuComboSkinMode.Sleek;
                break;
            case PRECISION:
                mCurrentMode = SkinMode.EXTREME;
                mode = TuFilterCombo.TuComboSkinMode.Vein;
                break;
        }
        mParameters = mFilterEngine.controller().changeSkin(mode);
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
                setParameters(mParameters);
                mFilterEngine.controller().changeSkin(TuFilterCombo.TuComboSkinMode.Empty);
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
                TuFilterCombo.TuComboSkinMode mode = TuFilterCombo.TuComboSkinMode.Sleek;
                switch (mCurrentMode){
                    case EXTREME:
                        mCurrentMode = SkinMode.PRECISION;
                        mode = TuFilterCombo.TuComboSkinMode.Sleek;
                        break;
                    case PRECISION:
                        mCurrentMode = SkinMode.EXTREME;
                        mode = TuFilterCombo.TuComboSkinMode.Vein;
                        break;
                }
                mParameters = mFilterEngine.controller().changeSkin(mode);
                Glide.with(mContext).load(mCurrentMode.iconId).into(mSkinModeIcon);
                mSkinModeTitle.setText(mCurrentMode.title);
                showToast(mCurrentMode.title);
            }
        });
        mSkinWhiteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkinEffect();
                showConfigView("whitening");
                Glide.with(mContext).load(R.drawable.skin_white_sel_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_ic).into(mSkinDermabrasionIcon);
                Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);

                mSkinWhiteTitle.setTextColor(Color.parseColor("#FFB602"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#999999"));
                mSkinRuddyTitle.setTextColor(Color.parseColor("#999999"));
            }
        });
        mSkinDermabrasionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkinEffect();
                showConfigView("smoothing");

                Glide.with(mContext).load(R.drawable.skin_white_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_sel_ic).into(mSkinDermabrasionIcon);
                Glide.with(mContext).load(R.drawable.skin_red_ic).into(mSkinRuddyIcon);

                mSkinWhiteTitle.setTextColor(Color.parseColor("#999999"));
                mSkinDermabrasionTitle.setTextColor(Color.parseColor("#FFB602"));
                mSkinRuddyTitle.setTextColor(Color.parseColor("#999999"));
            }
        });
        mSkinRuddyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkinEffect();
                showConfigView("ruddy");

                Glide.with(mContext).load(R.drawable.skin_white_ic).into(mSkinWhiteIcon);
                Glide.with(mContext).load(R.drawable.skin_dermabrasion_ic).into(mSkinDermabrasionIcon);
                Glide.with(mContext).load(R.drawable.skin_red_sel_ic).into(mSkinRuddyIcon);

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

    private void addSkinEffect(){
        if (!isClear) return;
        TuFilterCombo.TuComboSkinMode mode = TuFilterCombo.TuComboSkinMode.Sleek;
        switch (mCurrentMode){
            case EXTREME:
                mode = TuFilterCombo.TuComboSkinMode.Vein;
                break;
            case PRECISION:
                mode = TuFilterCombo.TuComboSkinMode.Sleek;
                break;
        }
        mParameters = mFilterEngine.controller().changeSkin(mode);
        isClear = false;
    }

}
