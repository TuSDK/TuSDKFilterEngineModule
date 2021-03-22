package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule;

import android.content.Context;

import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.filters.TusdkCosmeticFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.BasePanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.blush.BlushPanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyebrow.EyebrowPanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyelash.EyelashPanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyeliner.EyelinerPanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyeshadow.EyeshadowPanel;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.lipstick.LipstickPanel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * TuSDK
 * org.lasque.tusdkvideodemo.views.cosmetic
 * droid-sdk-video-refresh
 *
 * @author H.ys
 * @Date 2020/10/20  11:18
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class CosmeticPanelController {
    public static HashMap<String, Float> mDefaultCosmeticPercentParams = new HashMap<String, Float>() {
        {
            put("lipAlpha", 0.4f);
            put("blushAlpha", 0.5f);
            put("eyebrowAlpha", 0.4f);
            put("eyeshadowAlpha", 0.5f);
            put("eyelineAlpha", 0.5f);
            put("eyelashAlpha", 0.5f);
        }
    };

    private static HashMap<String, Float> mDefaultCosmeticMaxPercentParams = new HashMap<String, Float>() {
        {
            put("lipAlpha", 0.8f);
            put("blushAlpha", 1.0f);
            put("eyebrowAlpha", 0.7f);
            put("eyeshadowAlpha", 1.0f);
            put("eyelineAlpha", 1.0f);
            put("eyelashAlpha", 1.0f);
        }
    };

    private boolean isFirstCommit = true;

    /**
     * 口红列表
     */
    public static List<CosmeticTypes.LipstickType> mLipstickTypes = Arrays.asList(CosmeticTypes.LipstickType.values());

    /**
     * 睫毛列表
     */
    public static List<CosmeticTypes.EyelashType> mEyelashTypes = Arrays.asList(CosmeticTypes.EyelashType.values());

    /**
     * 眉毛列表
     */
    public static List<CosmeticTypes.EyebrowType> mEyebrowTypes = Arrays.asList(CosmeticTypes.EyebrowType.values());

    /**
     * 腮红列表
     */
    public static List<CosmeticTypes.BlushType> mBlushTypes = Arrays.asList(CosmeticTypes.BlushType.values());

    /**
     * 眼影类型
     */
    public static List<CosmeticTypes.EyeshadowType> mEyeshadowTypes = Arrays.asList(CosmeticTypes.EyeshadowType.values());

    /**
     * 眼线类型
     */
    public static List<CosmeticTypes.EyelinerType> mEyelinerTypes = Arrays.asList(CosmeticTypes.EyelinerType.values());

    private CosmeticModule mModule;

    private TusdkCosmeticFilter.PropertyBuilder mProperty;

    private Filter mCosmeticFilter;

    public SelesParameters getParameters() {
        return mModule.getParameters();
    }

    public CosmeticPanelController(CosmeticModule module) {
        this.mModule = module;

        mProperty = new TusdkCosmeticFilter.PropertyBuilder();

        mModule.syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mCosmeticFilter = new Filter(mModule.getPipeContext(),TusdkCosmeticFilter.TYPE_NAME);

                mModule.addFilter(SelesParameters.FilterModel.CosmeticFace,mCosmeticFilter);

                mModule.setProperty(SelesParameters.FilterModel.CosmeticFace,mProperty);
                return true;
            }
        });

        for (String key : mDefaultCosmeticPercentParams.keySet()){
            mModule.getParameters().appendFloatArg(key,mDefaultCosmeticPercentParams.get(key));
            SelesParameters.FilterArg arg = mModule.getParameters().getFilterArg(key);
            arg.setMaxValueFactor(mDefaultCosmeticMaxPercentParams.get(key));
        }

        mModule.getParameters().setListener(new SelesParameters.SelesParametersListener() {
            @Override
            public void onUpdateParameters(SelesParameters.FilterModel filterModel, String s, SelesParameters.FilterArg filterArg) {
                double value = filterArg.getValue();
                switch (filterArg.getKey()){
                    case "lipAlpha":
                        mProperty.lipOpacity = value;
                        mProperty.lipEnable = 1;
                        break;
                    case "blushAlpha":
                        mProperty.blushOpacity = value;
                        mProperty.blushEnable = 1;
                        break;
                    case "eyebrowAlpha":
                        mProperty.browOpacity = value;
                        mProperty.browEnable = 1;
                        break;
                    case "eyeshadowAlpha":
                        mProperty.eyeshadowOpacity = value;
                        mProperty.eyeshadowEnable = 1;
                        break;
                    case "eyelineAlpha":
                        mProperty.eyelineOpacity = value;
                        mProperty.eyelineEnable = 1;
                        break;
                    case "eyelashAlpha":
                        mProperty.eyelashOpacity = value;
                        mProperty.eyelashEnable = 1;
                        break;
                    case "facialAlpha":
                        mProperty.facialOpacity = value;
                        mProperty.facialEnable = 1;
                        break;
                }

                updateProperty();
            }
        });

    }

    public LipstickPanel getLipstickPanel() {
        if (mLipstickPanel == null) {
            mLipstickPanel = new LipstickPanel(this);
        }
        return mLipstickPanel;
    }

    public BlushPanel getBlushPanel() {
        if (mBlushPanel == null) {
            mBlushPanel = new BlushPanel(this);
        }
        return mBlushPanel;
    }

    public EyebrowPanel getEyebrowPanel() {
        if (mEyebrowPanel == null) {
            mEyebrowPanel = new EyebrowPanel(this);
        }
        return mEyebrowPanel;
    }

    public EyeshadowPanel getEyeshadowPanel() {
        if (mEyeshadowPanel == null) {
            mEyeshadowPanel = new EyeshadowPanel(this);
        }
        return mEyeshadowPanel;
    }

    public EyelinerPanel getEyelinerPanel() {
        if (mEyelinerPanel == null) {
            mEyelinerPanel = new EyelinerPanel(this);
        }
        return mEyelinerPanel;
    }

    public EyelashPanel getEyelashPanel() {
        if (mEyelashPanel == null) {
            mEyelashPanel = new EyelashPanel(this);
        }
        return mEyelashPanel;
    }

    public BasePanel getPanel(CosmeticTypes.Types types) {
        BasePanel panel = null;
        switch (types) {
            case Lipstick:
                panel = getLipstickPanel();
                break;
            case Blush:
                panel = getBlushPanel();
                break;
            case Eyebrow:
                panel = getEyebrowPanel();
                break;
            case Eyeshadow:
                panel = getEyeshadowPanel();
                break;
            case Eyeliner:
                panel = getEyelinerPanel();
                break;
            case Eyelash:
                panel = getEyelashPanel();
                break;
        }
        return panel;
    }

    private LipstickPanel mLipstickPanel;
    private BlushPanel mBlushPanel;
    private EyebrowPanel mEyebrowPanel;
    private EyeshadowPanel mEyeshadowPanel;
    private EyelinerPanel mEyelinerPanel;
    private EyelashPanel mEyelashPanel;


    public Context getContext() {
        return mModule.getContext();
    }

    public void setPanelClickListener(BasePanel.OnPanelClickListener listener) {
        getLipstickPanel().setOnPanelClickListener(listener);
        getBlushPanel().setOnPanelClickListener(listener);
        getEyebrowPanel().setOnPanelClickListener(listener);
        getEyeshadowPanel().setOnPanelClickListener(listener);
        getEyelinerPanel().setOnPanelClickListener(listener);
        getEyelashPanel().setOnPanelClickListener(listener);
    }

    public void clearAllCosmetic() {
        for (CosmeticTypes.Types type : CosmeticTypes.Types.values()) {
            getPanel(type).clear();
        }
    }

    public void updateProperty(){
        mModule.syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mCosmeticFilter.setProperty(TusdkCosmeticFilter.PROP_PARAM,mProperty.makeProperty());
            }
        });
    }

    public void updateBlush(long blushId){
        mProperty.blushEnable = 1;
        mProperty.blushId = blushId;
        updateProperty();
    }

    public void closeBlush(){
        mProperty.blushEnable = 0;
        updateProperty();
    }

    public void updateEyebrow(long eyebrowId){
        mProperty.browEnable = 1;
        mProperty.browId = eyebrowId;
        updateProperty();
    }

    public void closeEyebrow(){
        mProperty.browEnable = 0;
        updateProperty();
    }

    public void updateEyelash(long eyelashId){
        mProperty.eyelashEnable = 1;
        mProperty.eyelashId = eyelashId;
        updateProperty();
    }

    public void closeEyelash(){
        mProperty.eyelashEnable = 0;
        updateProperty();
    }

    public void updateEyeliner(long eyelinerId){
        mProperty.eyelineEnable = 1;
        mProperty.eyelineId = eyelinerId;
        updateProperty();
    }

    public void closeEyeliner(){
        mProperty.eyelineEnable = 0;
        updateProperty();
    }

    public void updateEyeshadow(long eyeshadowId){
        mProperty.eyeshadowEnable = 1;
        mProperty.eyeshadowId = eyeshadowId;
        updateProperty();
    }

    public void closeEyeshadow(){
        mProperty.eyeshadowEnable = 0;
        updateProperty();
    }

    public void updateLips(int type,int color){
        mProperty.lipEnable = 1;
        mProperty.lipStyle = type;
        mProperty.lipColor = color;
        updateProperty();
    }

    public void closeLips(){
        mProperty.lipEnable = 0;
        updateProperty();
    }

    public TusdkCosmeticFilter.PropertyBuilder getProperty(){
        return mProperty;
    }

}
