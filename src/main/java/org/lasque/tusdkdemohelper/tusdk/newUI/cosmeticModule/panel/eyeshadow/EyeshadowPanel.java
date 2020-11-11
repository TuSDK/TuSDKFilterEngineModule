package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyeshadow;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;
import com.example.tusdkdemohelper.R;

import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticPanelController;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticTypes;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.BasePanel;

import static org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticTypes.Types.Eyeshadow;

/**
 * TuSDK
 * panel.eyeshadow
 * droid-sdk-video-refresh
 *
 * @author H.ys
 * @Date 2020/10/20  17:19
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class EyeshadowPanel extends BasePanel {

    private CosmeticTypes.EyeshadowType mCurrentType;
    private EyeshadowAdapter mAdapter;

    public EyeshadowPanel(CosmeticPanelController controller) {
        super(controller, Eyeshadow);
    }

    @Override
    protected View createView() {
        View panel = LayoutInflater.from(mController.getContext()).inflate(R.layout.cosmetic_eyeshadow_panel,null,false);
        ImageView putAway = panel.findViewById(R.id.lsq_eyeshadow_put_away);
        putAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPanelClickListener != null) onPanelClickListener.onClose(mType);
            }
        });

        ImageView clear = panel.findViewById(R.id.lsq_eyeshadow_null);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        mAdapter = new EyeshadowAdapter(CosmeticPanelController.mEyeshadowTypes,mController.getContext());
        mAdapter.setOnItemClickListener(new OnItemClickListener<EyeshadowAdapter.EyeshadowViewHolder,CosmeticTypes.EyeshadowType>() {
            @Override
            public void onItemClick(int pos, EyeshadowAdapter.EyeshadowViewHolder holder, CosmeticTypes.EyeshadowType item) {
                mCurrentType = item;
                mController.getEngine().controller().changeCosmetic(TuFilterCombo.TuCosmeticMode.EyeShadow,StickerLocalPackage.shared().getStickerGroup(item.mGroupId).stickers.get(0).stickerId,-1, TuFilterCombo.TuCosmeticLipGlossStyle.None);
                mAdapter.setCurrentPos(pos);
                if (onPanelClickListener != null) onPanelClickListener.onClick(mType);

            }
        });
        RecyclerView itemList = panel.findViewById(R.id.lsq_eyeshadow_item_list);
        LinearLayoutManager manager = new LinearLayoutManager(mController.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        itemList.setLayoutManager(manager);
        itemList.setAdapter(mAdapter);
        itemList.setNestedScrollingEnabled(false);
        return panel;
    }

    @Override
    public void clear() {
        mCurrentType = null;
        mController.getEngine().controller().closeEyeshadow();
        mAdapter.setCurrentPos(-1);
        if (onPanelClickListener != null) onPanelClickListener.onClear(mType);
    }
}
