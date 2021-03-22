package org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.Config;
import com.tusdk.pulse.filter.Filter;
import com.tusdk.pulse.filter.FilterPipe;
import com.tusdk.pulse.filter.filters.TusdkFaceMonsterFilter;

import org.lasque.tusdkpulse.core.seles.SelesParameters;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.BIGNOSE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.PAPAYAFACE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.PIEFACE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.SMALLEYES;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.SNAKEFACE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.SQUAREFACE;
import static org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule.MonsterFunction.THICKLIPS;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  17:31
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class MonsterModule extends BaseModule {

    private RecyclerView mMonsterList;

    private ImageView mRemoveMonsterView;

    private ImageView mBackView;

    private MonsterAdapter mMonsterAdapter;

    private List<MonsterFunction> mMonsterFunction = Arrays.asList(SQUAREFACE, PIEFACE, BIGNOSE, SNAKEFACE, THICKLIPS, SMALLEYES, PAPAYAFACE);

    public MonsterModule(ModuleController controller, Context context) {
        super(controller, FunctionsType.MONSTER, context);
        findViews();
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_monster_module_layout, null);
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

        mRemoveMonsterView = mCurrentView.findViewById(R.id.lsq_monster_remove);
        mRemoveMonsterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMonsterFace();
                showToast("哈哈镜移除");
                mController.getPlasticModule().updateProperty();
            }
        });

        mMonsterAdapter = new MonsterAdapter(mContext,mMonsterFunction);
        mMonsterAdapter.setOnItemClickListener(new OnItemClickListener<MonsterAdapter.MonsterViewHolder, MonsterFunction>() {
            @Override
            public void onItemClick(int pos, MonsterAdapter.MonsterViewHolder holder, MonsterFunction item) {
                mMonsterAdapter.setCurrentPos(pos);
                changeMonsterFace(item.mode);
                mController.getPlasticModule().clearPlastic();
                mController.getStickerModule().clearSelect();
            }
        });
        mMonsterList = mCurrentView.findViewById(R.id.lsq_monster_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMonsterList.setLayoutManager(linearLayoutManager);
        mMonsterList.setNestedScrollingEnabled(false);
        mMonsterList.setAdapter(mMonsterAdapter);
    }

    public void clearMonsterFace(){
        if (mMonsterAdapter != null){
            mMonsterAdapter.setCurrentPos(-1);
        }
        syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                FilterPipe fp = mController.getFilterPipe();
                boolean res = fp.deleteFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.MonsterFace));
                return res;
            }
        });
    }

    public boolean changeMonsterFace(final String code){
        return syncRun(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                FilterPipe fp = mController.getFilterPipe();
                fp.deleteFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.MonsterFace));
                Filter monsterFace = new Filter(fp.getContext(), TusdkFaceMonsterFilter.TYPE_NAME);
                Config config = new Config();
                config.setString(TusdkFaceMonsterFilter.CONFIG_TYPE,code);
                monsterFace.setConfig(config);
                boolean res = fp.addFilter(ModuleController.mFilterMap.get(SelesParameters.FilterModel.MonsterFace),monsterFace);
                return res;
            }
        });
    }
}
