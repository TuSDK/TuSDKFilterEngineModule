package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.FilterContext;

import org.lasque.tubeautysetting.Beauty;
import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkdemohelper.tusdk.filter.FilterConfigView;

import java.util.concurrent.Callable;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.base
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  15:19
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public abstract class BaseModule {
    protected FunctionsType mModuleType;
    protected View mCurrentView;

    protected Context mContext;
    protected ModuleController mController;

    protected SelesParameters mParameters;

    protected FilterConfigView mConfigView;

    protected FragmentManager mFragmentManager;

    protected Lifecycle mLifecycle;

    protected Toast mCurrentToast;

    protected BaseModule(ModuleController controller, FunctionsType type, Context context) {
        this.mModuleType = type;
        this.mController = controller;
        this.mContext = context;
        this.mCurrentView = getView();
    }

    protected BaseModule(ModuleController controller, FunctionsType type, Context context, FragmentManager fragmentManager, Lifecycle lifecycle) {
        this.mModuleType = type;
        this.mController = controller;
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mLifecycle = lifecycle;
        this.mCurrentView = getView();
    }

    public FunctionsType getType() {
        return mModuleType;
    }

    public boolean attachView(ViewGroup viewGroup) {
        if (mCurrentView != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(mCurrentView);
            return true;
        }
        return false;
    }

    public void setConfigView(FilterConfigView view) {
        this.mConfigView = view;
    }

    public void setFragmentManager(FragmentManager manager) {
        this.mFragmentManager = manager;
    }

    public void setLifecycle(Lifecycle lifecycle) {
        this.mLifecycle = lifecycle;
    }

    protected abstract View getView();

    protected abstract void findViews();

    public void attach(ViewGroup parent) {
        attachView(parent);
    }

    public void detach(ViewGroup parent) {
        parent.removeAllViews();
        if (mConfigView != null) {
            mConfigView.setVisibility(View.GONE);
        }
    }

    protected void backToMain() {
        mController.animatorSwitchModule(FunctionsType.MAIN);
    }

    public View getCurrentView() {
        return mCurrentView;
    }

    public SelesParameters getParameters() {
        return mParameters;
    }

    protected void showToast(String value) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(mContext, value, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }


    public Context getContext() {
        return mContext;
    }

    public Beauty getBeautyManager(){
        return mController.getBeautyManager();
    }
}
