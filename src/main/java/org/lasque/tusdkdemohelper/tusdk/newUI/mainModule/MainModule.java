package org.lasque.tusdkdemohelper.tusdk.newUI.mainModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.List;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.mainModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  15:36
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class MainModule extends BaseModule implements OnItemClickListener<FunctionMenuAdapter.FunctionMenuViewHolder, FunctionMenuItem> {

    private RecyclerView mFunctionItemRecycleView;
    private List<FunctionMenuItem> mMenuItems;
    private FunctionMenuAdapter mFunctionMenuAdapter;


    public MainModule(ModuleController controller, Context context, List<FunctionMenuItem> menuItems) {
        super(controller, FunctionsType.MAIN, context);
        this.mMenuItems = menuItems;
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_main_module_layout, null, false);
    }

    @Override
    protected void findViews() {
        mFunctionItemRecycleView = mCurrentView.findViewById(R.id.lsq_main_module_function_menu);
        mFunctionMenuAdapter = new FunctionMenuAdapter(mContext, mMenuItems);
        mFunctionMenuAdapter.setOnItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFunctionItemRecycleView.setLayoutManager(linearLayoutManager);
        mFunctionItemRecycleView.setAdapter(mFunctionMenuAdapter);
    }

    @Override
    public void onItemClick(int pos, FunctionMenuAdapter.FunctionMenuViewHolder holder, FunctionMenuItem item) {
        switch (item) {
            case FUNCTION_SKIN:
                mController.animatorSwitchModule(FunctionsType.SKIN);
                break;
            case FUNCTION_PLASTIC:
                mController.animatorSwitchModule(FunctionsType.PLASTIC);
                break;
            case FUNCTION_FILTER:
                mController.animatorSwitchModule(FunctionsType.FILTER);
                break;
            case FUNCTION_FACE_STICKER:
                mController.animatorSwitchModule(FunctionsType.STICKER);
                break;
            case FUNCTION_MONSTER_FACE:
                mController.animatorSwitchModule(FunctionsType.MONSTER);
                break;
            case FUNCTION_VOICE:
                mController.animatorSwitchModule(FunctionsType.VOICE);
                break;
        }
    }
}
