package org.lasque.tusdkdemohelper.tusdk.newUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.filter.FilterPipe;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterGroup;
import org.lasque.tusdkpulse.core.utils.ThreadHelper;
import org.lasque.tusdkdemohelper.tusdk.filter.FilterConfigView;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.mainModule.FunctionMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/7  14:03
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class TuSDKModuleFragment extends Fragment {

    public static final String COLOR_LIST_KEY = "colorList";
    public static final String FILTER_GROUPS_KEY = "filterGroups";
    public static final String HAS_MONSTER_FACE_KEY = "monsterFace";
    public static final String HAS_VOICE_KEY = "voice";

    public static TuSDKModuleFragment newInstance(FilterGroup[] filterGroups, ArrayList<String> colorList, boolean hasMonsterFace, boolean hasVoice) {
        TuSDKModuleFragment fragment = new TuSDKModuleFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(COLOR_LIST_KEY, colorList);
        bundle.putSerializable(FILTER_GROUPS_KEY, filterGroups);
        bundle.putBoolean(HAS_MONSTER_FACE_KEY, hasMonsterFace);
        bundle.putBoolean(HAS_VOICE_KEY, hasVoice);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FrameLayout mParentView;

    private FilterConfigView mConfigView;

    private ModuleController mController;

    private List<FilterGroup> mFilterGroups;

    private List<String> mColorList;


    // ----------------------------- FilterPipe ---------------------------------

    private FilterPipe mFP;

    private ExecutorService mRenderPool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.tusdk_module_layout, container,false);
        mParentView = parentView.findViewById(R.id.lsq_module_view_panel);
        mConfigView = parentView.findViewById(R.id.lsq_config_view);
        ArrayList<FunctionMenuItem> menuItems = new ArrayList<>();
        menuItems.add(FunctionMenuItem.FUNCTION_SKIN);
        menuItems.add(FunctionMenuItem.FUNCTION_PLASTIC);
        menuItems.add(FunctionMenuItem.FUNCTION_COSMETIC);
        menuItems.add(FunctionMenuItem.FUNCTION_FILTER);
        menuItems.add(FunctionMenuItem.FUNCTION_FACE_STICKER);
        boolean hasMonsterFace = getArguments().getBoolean(HAS_MONSTER_FACE_KEY, false);
        if (hasMonsterFace) menuItems.add(FunctionMenuItem.FUNCTION_MONSTER_FACE);
        boolean hasVoice = getArguments().getBoolean(HAS_VOICE_KEY, false);
        if (hasVoice) menuItems.add(FunctionMenuItem.FUNCTION_VOICE);
        if (getArguments().containsKey(FILTER_GROUPS_KEY)) {
            mFilterGroups = Arrays.asList(((FilterGroup[]) getArguments().getSerializable(FILTER_GROUPS_KEY)));
        }
        if (getArguments().containsKey(COLOR_LIST_KEY)) {
            mColorList = getArguments().getStringArrayList(COLOR_LIST_KEY);
        }
        mController = new ModuleController(getContext(), getChildFragmentManager(), getLifecycle(), mParentView, menuItems);
        mController.setFilters(mFilterGroups, mColorList);
        mController.setConfigView(mConfigView);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParentView.invalidate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController.switchModule(FunctionsType.MAIN);
    }

    public void setFilterPipe(FilterPipe fp,ExecutorService renderPool){
        mFP = fp;
        mRenderPool = renderPool;
        mController.setFilterPipe(fp,renderPool);
    }


//    public void setAudioEngine(TuSdkAudioPitchEngine engine){
//        mAudioEngine = engine;
//    }

//    /**
//     * 切换滤镜
//     *
//     * @param code
//     */
//    public void changeFilter(String code) {
//        mFilterEngine.controller().changeFilter(code, null);
//    }
//
//    /**
//     * 切换美颜预设按键
//     *
//     * @param useSkinNatural true 自然(精准)美颜 false 极致美颜
//     */
//    private void switchConfigSkin(boolean useSkinNatural) {
//        SelesParameters parameters = mFilterEngine.controller().changeSkin(useSkinNatural ? TuFilterCombo.TuComboSkinMode.Sleek : TuFilterCombo.TuComboSkinMode.Vein);
//    }
}
