package org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.solver.widgets.ConstraintHorizontalLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
            put(FOREHEAD.code, 0.5f);
            put(CHINSIZE.code, 0.5f);
            put(BROWPOSITION.code, 0.5f);
            put(ARCHEYEBROW.code, 0.5f);
            put(EYESIZE.code, 0.3f);
            put(EYEANGLE.code, 0.5f);
            put(EYEDIS.code, 0.5f);
            put(NOSESIZE.code, 0.2f);
            put(MOUTHWIDTH.code, 0.5f);
            put(LIPS.code, 0.5f);
            put(JAWSIZE.code, 0.5f);
        }
    };

    private ImageView mBackView;

    private ImageView mPlasticReset;

    private RecyclerView mPlasticList;

    private PlasticAdapter mPlasticAdapter;

    private List<PlasticFunction> mPlasticFunctions = Arrays.asList(EYESIZE, CHINSIZE, NOSESIZE, MOUTHWIDTH, LIPS, ARCHEYEBROW, BROWPOSITION, JAWSIZE, EYEANGLE, EYEDIS, FOREHEAD);

    public PlasticModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.PLASTIC, context);
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_plastic_module_layout, null, false);
    }

    @Override
    public void setParameters(SelesParameters parameters) {
        super.setParameters(parameters);
        for (String s : mDefaultBeautyPercentParams.keySet()) {
            float v = mDefaultBeautyPercentParams.get(s);
            mParameters.setFilterArg(s, v);
        }
        mParameters = mFilterEngine.controller().changePlastic(true);
    }

    public void clearPlastic(){
        mParameters = null;
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
                mFilterEngine.controller().changeMonster(TuFilterCombo.TuFaceMonsterMode.Empty);
                mController.getMonsterModule().clearMonsterFace();
                if (mParameters == null) {
                    mParameters = mFilterEngine.controller().changePlastic(true);
                    setParameters(mParameters);
                }
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
}
