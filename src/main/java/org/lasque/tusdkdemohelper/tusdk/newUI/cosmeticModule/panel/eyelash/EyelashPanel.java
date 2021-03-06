package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyelash;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdkdemohelper.tusdk.newUI.base.OnItemClickListener;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticPanelController;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticTypes;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.BasePanel;
import org.lasque.tusdkpulse.modules.view.widget.sticker.StickerLocalPackage;

import static org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.CosmeticTypes.Types.Eyelash;

/**
 * TuSDK
 * panel.eyelash
 * droid-sdk-video-refresh
 *
 * @author H.ys
 * @Date 2020/10/20  15:31
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class EyelashPanel extends BasePanel {

    private CosmeticTypes.EyelashType mCurrentType;

    private EyelashAdapter mAdapter;

    public EyelashPanel(CosmeticPanelController controller) {
        super(controller, Eyelash);
    }

    @Override
    protected View createView() {
        final View panel = LayoutInflater.from(mController.getContext()).inflate(R.layout.cosmetic_eyelash_panel,null,false);
        ImageView putAway = panel.findViewById(R.id.lsq_eyelash_put_away);
        putAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPanelClickListener != null) onPanelClickListener.onClose(mType);
            }
        });
        ImageView clear = panel.findViewById(R.id.lsq_eyelash_null);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        mAdapter = new EyelashAdapter(CosmeticPanelController.mEyelashTypes,mController.getContext());
        mAdapter.setOnItemClickListener(new OnItemClickListener<EyelashAdapter.EyelashViewHolder,CosmeticTypes.EyelashType>() {
            @Override
            public void onItemClick(int pos, EyelashAdapter.EyelashViewHolder holder, CosmeticTypes.EyelashType item) {
                mCurrentType = item;
                mController.updateEyelash(StickerLocalPackage.shared().getStickerGroup(item.mGroupId).stickers.get(0).stickerId);
                mAdapter.setCurrentPos(pos);
                if (onPanelClickListener != null) onPanelClickListener.onClick(mType);

            }
        });
        RecyclerView itemList = panel.findViewById(R.id.lsq_eyelash_item_list);
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
        mController.closeEyelash();
        mAdapter.setCurrentPos(-1);
        if (onPanelClickListener != null) onPanelClickListener.onClear(mType);
    }
}
