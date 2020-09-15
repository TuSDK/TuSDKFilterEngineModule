package org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.core.media.codec.audio.processor.TuSdkAudioPitchEngine;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import static org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule.VoiceFunction.GIRL;
import static org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule.VoiceFunction.MONSTER;
import static org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule.VoiceFunction.UNCLE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule.VoiceFunction.WOMAN;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/9/1  9:34
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class VoiceModule extends BaseModule {

    private ImageView mBackView;

    private ImageView mVoiceReset;

    private RecyclerView mVoiceList;

    private VoiceAdapter mVoiceAdapter;

    private TuSdkAudioPitchEngine mAudioEngine;

    private List<VoiceFunction> mVoiceFunctions = Arrays.asList(MONSTER,UNCLE,WOMAN,GIRL);

    public VoiceModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.VOICE, context);
        findViews();
    }

    public void setAudioEngine(TuSdkAudioPitchEngine audioEngine) {
        this.mAudioEngine = audioEngine;
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_voice_module_layout, null, false);
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
        mVoiceReset = mCurrentView.findViewById(R.id.lsq_voice_module_reset);
        mVoiceReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVoiceAdapter.setCurrentPosition(-1);
            }
        });
        mVoiceAdapter = new VoiceAdapter(mContext,mVoiceFunctions);
        mVoiceAdapter.setOnItemClickListener(new OnItemClickListener<VoiceAdapter.VoiceViewHolder, VoiceFunction>() {
            @Override
            public void onItemClick(int pos, VoiceAdapter.VoiceViewHolder holder, VoiceFunction item) {
                mVoiceAdapter.setCurrentPosition(pos);
                mAudioEngine.setSoundPitchType(item.type);
            }
        });
        mVoiceList = mCurrentView.findViewById(R.id.lsq_voice_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVoiceList.setLayoutManager(linearLayoutManager);
        mVoiceList.setNestedScrollingEnabled(false);
        mVoiceList.setAdapter(mVoiceAdapter);
    }
}
