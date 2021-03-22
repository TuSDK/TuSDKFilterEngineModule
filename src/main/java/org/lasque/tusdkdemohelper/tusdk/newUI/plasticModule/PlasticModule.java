package org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.FilterPipe;
import com.tusdk.pulse.filter.filters.TusdkFacePlasticFilter;
import com.tusdk.pulse.filter.filters.TusdkReshapeFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import static org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule.PlasticFunction.*;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  11:41
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class PlasticModule extends BaseModule {

    /**
     * 微整形默认值  Float 为进度值
     */
    private HashMap<String, Float> mDefaultBeautyPercentParams = new HashMap<String, Float>() {
        {
            put(EYESIZE.code, 0.3f);
            put(CHINSIZE.code, 0.5f);
            put(CHEEKNARROW.code,0.0f);
            put(SMALLFACE.code,0.0f);
            put(NOSESIZE.code, 0.2f);
            put(NOSEHEIGHT.code,0.0f);
            put(MOUTHWIDTH.code, 0.5f);
            put(LIPS.code, 0.5f);
            put(PHILTERUM.code,0.5f);
            put(ARCHEYEBROW.code, 0.5f);
            put(BROWPOSITION.code, 0.5f);
            put(JAWSIZE.code, 0.5f);
            put(CHEEKLOWBONENARROW.code,0.0f);
            put(EYEANGLE.code, 0.5f);
            put(EYEINNERCONER.code,0.0f);
            put(EYEOUTERCONER.code,0.0f);
            put(EYEDIS.code, 0.5f);
            put(EYEHEIGHT.code,0.5f);
            put(FOREHEAD.code, 0.5f);
            put(CHEEKBONENARROW.code,0.0f);

            // Reshape

            put(EYELID.code,0.0f);
            put(EYEMAZING.code,0.0f);
            put(WHITENTEETH.code,0.0f);
            put(EYEDETAIL.code,0.0f);
            put(REMOVEPOUCH.code,0.0f);
            put(REMOVEWRINKLES.code,0.0f);
        }
    };

    private List<String> mReshapePlastics = new ArrayList(){
        {
            add(EYELID.code);
            add(EYEMAZING.code);
            add(WHITENTEETH.code);
            add(EYEDETAIL.code);
            add(REMOVEPOUCH.code);
            add(REMOVEWRINKLES.code);
        }
    };



    private ImageView mBackView;

    private ImageView mPlasticReset;

    private RecyclerView mPlasticList;

    private PlasticAdapter mPlasticAdapter;

    private Filter mPlasticFilter;

    private Filter mReshapeFilter;

    private TusdkFacePlasticFilter.PropertyBuilder mPlasticProperty = new TusdkFacePlasticFilter.PropertyBuilder();

    private TusdkReshapeFilter.PropertyBuilder mReshapeProperty = new TusdkReshapeFilter.PropertyBuilder();

    private List<PlasticFunction> mPlasticFunctions = Arrays.asList(EYESIZE, CHINSIZE, NOSESIZE, MOUTHWIDTH, LIPS, ARCHEYEBROW, BROWPOSITION, JAWSIZE, EYEANGLE, EYEDIS, FOREHEAD);

    public PlasticModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.PLASTIC, context);
        findViews();

        mParameters = new SelesParameters();
        for (String key : mDefaultBeautyPercentParams.keySet()){
            mParameters.appendFloatArg(key,mDefaultBeautyPercentParams.get(key));
            float value = mDefaultBeautyPercentParams.get(key);
            if (mReshapePlastics.contains(key)){
                switch (key){
                    case "eyelidAlpha":
                        mReshapeProperty.eyelidOpacity = value;
                        break;
                    case "eyemazingAlpha":
                        mReshapeProperty.eyemazingOpacity = value;
                        break;
                    case "whitenTeethAlpha":
                        mReshapeProperty.whitenTeethOpacity = value;
                        break;
                    case "eyeDetailAlpha":
                        mReshapeProperty.eyeDetailOpacity = value;
                        break;
                    case "removePouchAlpha":
                        mReshapeProperty.removePouchOpacity = value;
                        break;
                    case "removeWrinklesAlpha":
                        mReshapeProperty.removeWrinklesOpacity = value;
                        break;
                }
            } else {
                switch (key) {
                    case "eyeSize":
                        mPlasticProperty.eyeEnlarge = value;
                        break;
                    case "chinSize":
                        mPlasticProperty.cheekThin = value;
                        break;
                    case "cheekNarrow":
                        mPlasticProperty.cheekNarrow = value;
                        break;
                    case "smallFace":
                        mPlasticProperty.faceSmall = value;
                        break;
                    case "noseSize":
                        mPlasticProperty.noseWidth = value;
                        break;
                    case "noseHeight":
                        mPlasticProperty.noseHeight = value;
                        break;
                    case "mouthWidth":
                        mPlasticProperty.mouthWidth = value;
                        break;
                    case "lips":
                        mPlasticProperty.lipsThickness = value;
                        break;
                    case "philterum":
                        mPlasticProperty.philterumThickness = value;
                        break;
                    case "archEyebrow":
                        mPlasticProperty.browThickness = value;
                        break;
                    case "browPosition":
                        mPlasticProperty.browHeight = value;
                        break;
                    case "jawSize":
                        mPlasticProperty.chinThickness = value;
                        break;
                    case "cheekLowBoneNarrow":
                        mPlasticProperty.cheekLowBoneNarrow = value;
                        break;
                    case "eyeAngle":
                        mPlasticProperty.eyeAngle = value;
                        break;
                    case "eyeInnerConer":
                        mPlasticProperty.eyeInnerConer = value;
                        break;
                    case "eyeOuterConer":
                        mPlasticProperty.eyeOuterConer = value;
                        break;
                    case "eyeDis":
                        mPlasticProperty.eyeDistance = value;
                        break;
                    case "eyeHeight":
                        mPlasticProperty.eyeHeight = value;
                        break;
                    case "forehead":
                        mPlasticProperty.foreheadHeight = value;
                        break;
                    case "cheekBoneNarrow":
                        mPlasticProperty.cheekBoneNarrow = value;
                        break;

                }
            }
        }

        mParameters.setListener(new SelesParameters.SelesParametersListener() {
            @Override
            public void onUpdateParameters(SelesParameters.FilterModel filterModel, String s, SelesParameters.FilterArg filterArg) {
                String key = filterArg.getKey();
                float value = filterArg.getValue();
                if (mReshapePlastics.contains(key)){
                    switch (key){
                        case "eyelidAlpha":
                            mReshapeProperty.eyelidOpacity = value;
                            break;
                        case "eyemazingAlpha":
                            mReshapeProperty.eyemazingOpacity = value;
                            break;
                        case "whitenTeethAlpha":
                            mReshapeProperty.whitenTeethOpacity = value;
                            break;
                        case "eyeDetailAlpha":
                            mReshapeProperty.eyeDetailOpacity = value;
                            break;
                        case "removePouchAlpha":
                            mReshapeProperty.removePouchOpacity = value;
                            break;
                        case "removeWrinklesAlpha":
                            mReshapeProperty.removeWrinklesOpacity = value;
                            break;
                    }
                }
                else {
                    switch (key) {
                        case "eyeSize":
                            mPlasticProperty.eyeEnlarge = value;
                            break;
                        case "chinSize":
                            mPlasticProperty.cheekThin = value;
                            break;
                        case "cheekNarrow":
                            mPlasticProperty.cheekNarrow = value;
                            break;
                        case "smallFace":
                            mPlasticProperty.faceSmall = value;
                            break;
                        case "noseSize":
                            mPlasticProperty.noseWidth = value;
                            break;
                        case "noseHeight":
                            mPlasticProperty.noseHeight = value;
                            break;
                        case "mouthWidth":
                            mPlasticProperty.mouthWidth = value;
                            break;
                        case "lips":
                            mPlasticProperty.lipsThickness = value;
                            break;
                        case "philterum":
                            mPlasticProperty.philterumThickness = value;
                            break;
                        case "archEyebrow":
                            mPlasticProperty.browThickness = value;
                            break;
                        case "browPosition":
                            mPlasticProperty.browHeight = value;
                            break;
                        case "jawSize":
                            mPlasticProperty.chinThickness = value;
                            break;
                        case "cheekLowBoneNarrow":
                            mPlasticProperty.cheekLowBoneNarrow = value;
                            break;
                        case "eyeAngle":
                            mPlasticProperty.eyeAngle = value;
                            break;
                        case "eyeInnerConer":
                            mPlasticProperty.eyeInnerConer = value;
                            break;
                        case "eyeOuterConer":
                            mPlasticProperty.eyeOuterConer = value;
                            break;
                        case "eyeDis":
                            mPlasticProperty.eyeDistance = value;
                            break;
                        case "eyeHeight":
                            mPlasticProperty.eyeHeight = value;
                            break;
                        case "forehead":
                            mPlasticProperty.foreheadHeight = value;
                            break;
                        case "cheekBoneNarrow":
                            mPlasticProperty.cheekBoneNarrow = value;
                            break;

                    }
                }

                updateProperty();
            }
        });

        updateProperty();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_plastic_module_layout, null, false);
    }

    public void clearPlastic(){
        mParameters = null;
        syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                FilterPipe fp = mController.getFilterPipe();
                boolean plasticRes = fp.deleteFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.PlasticFace));
                boolean reshapeRes = fp.deleteFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.Reshape));
                return plasticRes && reshapeRes;
            }
        });
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
        mPlasticReset = mCurrentView.findViewById(R.id.lsq_plastic_module_reset);
        mPlasticReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlasticAdapter.setCurrentPos(-1);
                mConfigView.setVisibility(View.GONE);
                mParameters.reset();
                showToast("微整形参数重置");
            }
        });

        mPlasticAdapter = new PlasticAdapter(mContext, mPlasticFunctions);
        mPlasticAdapter.setOnItemClickListener(new OnItemClickListener<PlasticAdapter.PlasticViewHolder, PlasticFunction>() {
            @Override
            public void onItemClick(int pos, PlasticAdapter.PlasticViewHolder holder, PlasticFunction item) {
                mController.getMonsterModule().clearMonsterFace();
                SelesParameters.FilterArg arg = mParameters.getFilterArg(item.code);
                mConfigView.setFilterArgs(Arrays.asList(arg));
                mConfigView.setVisibility(View.VISIBLE);
                mPlasticAdapter.setCurrentPos(pos);
            }
        });
        mPlasticList = mCurrentView.findViewById(R.id.lsq_plastic_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPlasticList.setLayoutManager(linearLayoutManager);
        mPlasticList.setNestedScrollingEnabled(false);
        mPlasticList.setAdapter(mPlasticAdapter);
    }

    @Override
    public void detach(ViewGroup parent) {
        super.detach(parent);
        if (mPlasticAdapter != null) {
            mPlasticAdapter.setCurrentPos(-1);
        }
        mConfigView.setVisibility(View.GONE);
        mConfigView.setFilterArgs(((SelesParameters) null));
    }

    @Override
    public void attach(ViewGroup parent) {
        super.attach(parent);
    }

    public boolean updateProperty(){
        return syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                FilterPipe fp = mController.getFilterPipe();
                if (fp.getFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.PlasticFace)) == null){
                    mPlasticFilter = new Filter(fp.getContext(),TusdkFacePlasticFilter.TYPE_NAME);
                    fp.addFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.PlasticFace),mPlasticFilter);
                }
                boolean plasticRes = mPlasticFilter.setProperty(TusdkFacePlasticFilter.PROP_PARAM,mPlasticProperty.makeProperty());

                if (fp.getFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.Reshape)) == null){
                    mReshapeFilter = new Filter(fp.getContext(),TusdkReshapeFilter.TYPE_NAME);
                    fp.addFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.Reshape),mReshapeFilter);
                }

                boolean reshapeRes = mReshapeFilter.setProperty(TusdkReshapeFilter.PROP_PARAM,mReshapeProperty.makeProperty());
                return plasticRes && reshapeRes;
            }
        });
    }


}
