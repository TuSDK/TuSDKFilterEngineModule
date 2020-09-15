package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import org.lasque.tusdk.core.media.codec.audio.processor.TuSdkAudioPitchEngine;
import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.utils.ThreadHelper;
import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdk.cx.api.TuFilterEngine;
import org.lasque.tusdkdemohelper.tusdk.filter.FilterConfigView;
import org.lasque.tusdkdemohelper.tusdk.newUI.filterModule.FilterModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.filterModule.FilterModule2;
import org.lasque.tusdkdemohelper.tusdk.newUI.mainModule.FunctionMenuItem;
import org.lasque.tusdkdemohelper.tusdk.newUI.mainModule.MainModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule.PlasticModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.skinModule.SkinModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.stickerModule.StickerModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule.VoiceModule;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.base
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  15:33
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
final public class ModuleController {
    private MainModule mHomeModule;
    private FilterModule mFilterModule;
    private FilterModule2 mFilterModule2;
    private PlasticModule mPlasticModule;
    private SkinModule mSkinModule;
    private MonsterModule mMonsterModule;
    private StickerModule mStickerModule;
    private VoiceModule mVoiceModule;

    private List<FunctionMenuItem> mMenuItems;

    //动画持续时间
    private static final int DURATION = 250;

    private ObjectAnimator mBottomShowAnimator = new ObjectAnimator();

    private ObjectAnimator mBottomHideAnimator = new ObjectAnimator();

    private ViewGroup mBottomView;

    private BaseModule mCurrentModule;

    private FunctionsType mModuleType;

    private FragmentManager mFragmentManger;

    private Lifecycle mLifecycle;

    private Context mContext;

    private List<FilterGroup> mFilterGroup;

    private List<String> mFilterColorList;

    private TuFilterEngine mFilterEngine;

    private FilterConfigView mConfigView;

    // TuSdk Audio Engine

    private TuSdkAudioPitchEngine mAudioEngine;

    public ModuleController(
            Context context,
            FragmentManager fragmentManager,
            Lifecycle lifecycle,
            ViewGroup bottomView,
            List<FunctionMenuItem> menuItems) {
        this.mContext = context;
        this.mFragmentManger = fragmentManager;
        this.mLifecycle = lifecycle;
        this.mBottomView = bottomView;
        this.mMenuItems = menuItems;
    }

    public void setFilters(List<FilterGroup> filters,List<String> filterColors){
        this.mFilterGroup = filters;
        this.mFilterColorList = filterColors;
    }

    public void setFilterEngine(TuFilterEngine engine){
        mFilterEngine = engine;
        ThreadHelper.postDelayed(new Runnable() {
            @Override
            public void run() {
                String defaultCode = mFilterGroup.get(0).getDefaultFilter().code;
                changeFilter(defaultCode);
                switchConfigSkin(false);
                SelesParameters selesParameters = mFilterEngine.controller().changePlastic(true);
                getPlasticModule().setParameters(selesParameters);
                getFilterModule2().setDefaultFilter(0,defaultCode);
            }
        }, 500);
    }

    public void setAudioEngine(TuSdkAudioPitchEngine engine){
        this.mAudioEngine = engine;
    }



    /**
     * 切换滤镜
     *
     * @param code
     */
    public void changeFilter(String code) {
        SelesParameters selesParameters = mFilterEngine.controller().changeFilter(code, null);
        getFilterModule2().setParameters(selesParameters);
    }

    /**
     * 切换美颜预设按键
     *
     * @param useSkinNatural true 自然(精准)美颜 false 极致美颜
     */
    private void switchConfigSkin(boolean useSkinNatural) {
        SelesParameters parameters = mFilterEngine.controller().changeSkin(useSkinNatural ? TuFilterCombo.TuComboSkinMode.Sleek : TuFilterCombo.TuComboSkinMode.Vein);
        getSkinModule().setParameters(parameters);
    }

    public void setConfigView(FilterConfigView configView){
        this.mConfigView = configView;
    }

    //进入动画监听
    private Animator.AnimatorListener mShowAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mBottomShowAnimator.removeAllListeners();

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    //隐藏动画监听
    private Animator.AnimatorListener mHideAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mBottomHideAnimator.removeAllListeners();
            switchModule(mModuleType);
            showModule();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void switchModule(FunctionsType type) {
        if (mCurrentModule != null)
            mCurrentModule.detach(mBottomView);
        switch (type) {
            case MAIN:
                mCurrentModule = getHomeModule();
                break;
            case SKIN:
                mCurrentModule = getSkinModule();
                break;
            case PLASTIC:
                mCurrentModule = getPlasticModule();
                break;
            case FILTER:
                mCurrentModule = getFilterModule2();
                break;
            case STICKER:
                mCurrentModule = getStickerModule();
                break;
            case MONSTER:
                mCurrentModule = getMonsterModule();
                break;
            case VOICE:
                mCurrentModule = getVoiceModule();
                break;
        }
        if (mCurrentModule != null)
            mCurrentModule.attach(mBottomView);
    }



    public void animatorSwitchModule(FunctionsType type) {
        mModuleType = type;
        hideModule();
    }

    public void showModule() {
        if (mBottomView == null) {
            return;
        }

        final int bottomHeight = getBottomHeight();
        if (mBottomHideAnimator.isRunning()) mBottomHideAnimator.end();
        mBottomShowAnimator.setIntValues(bottomHeight, 0);
        mBottomShowAnimator.setDuration(DURATION);
        mBottomShowAnimator.addListener(mShowAnimatorListener);
        mBottomShowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCurrentModule.getCurrentView().setTranslationY(value);
            }
        });
        mBottomShowAnimator.start();
    }

    public void hideModule() {
        if (mBottomView == null) {
            return;
        }
        final int bottomHeight = getBottomHeight();
        if (mBottomShowAnimator.isRunning()) mBottomShowAnimator.end();
        mBottomHideAnimator.setIntValues(0, bottomHeight);
        mBottomHideAnimator.setDuration(DURATION);
        mBottomHideAnimator.addListener(mHideAnimatorListener);
        mBottomHideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCurrentModule.getCurrentView().setTranslationY(value);
            }
        });
        mBottomHideAnimator.start();
    }

    public BaseModule getCurrentModule() {
        return mCurrentModule;
    }

    private int getBottomHeight() {
        if (getCurrentModule() == null || getCurrentModule().getCurrentView() == null) {
            return 0;
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mCurrentModule.getCurrentView().measure(w, h);
        return mCurrentModule.getCurrentView().getMeasuredHeight();

    }

    public MainModule getHomeModule() {
        if (mHomeModule == null) {
            mHomeModule = new MainModule(this, mContext, mMenuItems);
            mHomeModule.setConfigView(mConfigView);
        }
        return mHomeModule;
    }

    public FilterModule getFilterModule() {
        if (mFilterModule == null) {
            mFilterModule = new FilterModule(this, mContext, mFilterGroup, mFilterColorList);
            mFilterModule.setFilterEngine(mFilterEngine);
            mFilterModule.setConfigView(mConfigView);
        }
        return mFilterModule;
    }

    public FilterModule2 getFilterModule2(){
        if (mFilterModule2 == null){
            mFilterModule2 = new FilterModule2(this,mContext,mFilterGroup,mFilterColorList);
            mFilterModule2.setFilterEngine(mFilterEngine);
            mFilterModule2.setConfigView(mConfigView);
        }
        return mFilterModule2;
    }

    public PlasticModule getPlasticModule() {
        if (mPlasticModule == null) {
            mPlasticModule = new PlasticModule(this, mContext);
            mPlasticModule.setFilterEngine(mFilterEngine);
            mPlasticModule.setConfigView(mConfigView);
        }
        return mPlasticModule;
    }

    public SkinModule getSkinModule() {
        if (mSkinModule == null) {
            mSkinModule = new SkinModule(this, mContext);
            mSkinModule.setFilterEngine(mFilterEngine);
            mSkinModule.setConfigView(mConfigView);
        }
        return mSkinModule;
    }

    public MonsterModule getMonsterModule() {
        if (mMonsterModule == null) {
            mMonsterModule = new MonsterModule(this, mContext);
            mMonsterModule.setFilterEngine(mFilterEngine);
        }
        return mMonsterModule;
    }

    public StickerModule getStickerModule() {
        if (mStickerModule == null) {
            mStickerModule = new StickerModule(this, mContext, mFragmentManger, mLifecycle);
            mStickerModule.setFilterEngine(mFilterEngine);
        }
        return mStickerModule;
    }

    public VoiceModule getVoiceModule() {
        if (mVoiceModule == null){
            mVoiceModule = new VoiceModule(this,mContext);
            mVoiceModule.setAudioEngine(mAudioEngine);
        }
        return mVoiceModule;
    }

}
