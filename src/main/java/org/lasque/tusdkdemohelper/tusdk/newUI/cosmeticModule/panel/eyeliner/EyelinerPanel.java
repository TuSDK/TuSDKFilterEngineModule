package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.eyeliner;

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

/**
 * TuSDK
 * panel.eyeliner
 * droid-sdk-video-refresh
 *
 * @author H.ys
 * @Date 2020/10/20  17:48
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class EyelinerPanel extends BasePanel {

    private CosmeticTypes.EyelinerType mCurrentType;
    private EyelinerAdapter mAdapter;


    public EyelinerPanel(CosmeticPanelController controller) {
        super(controller, CosmeticTypes.Types.Eyeliner);
    }

    @Override
    protected View createView() {
        View panel = LayoutInflater.from(mController.getContext()).inflate(R.layout.cosmetic_eyeliner_panel,null,false);
        final ImageView putAway = panel.findViewById(R.id.lsq_eyeliner_put_away);
        putAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPanelClickListener != null) onPanelClickListener.onClose(mType);
            }
        });

        final ImageView clearLips = panel.findViewById(R.id.lsq_eyeliner_null);
        clearLips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        mAdapter = new EyelinerAdapter(CosmeticPanelController.mEyelinerTypes,mController.getContext());
        mAdapter.setOnItemClickListener(new OnItemClickListener<EyelinerAdapter.EyelinerViewHolder,CosmeticTypes.EyelinerType>() {
            @Override
            public void onItemClick(int pos, EyelinerAdapter.EyelinerViewHolder holder, CosmeticTypes.EyelinerType item) {
                mCurrentType = item;
                mController.updateEyeliner(StickerLocalPackage.shared().getStickerGroup(item.mGroupId).stickers.get(0).stickerId);
                mAdapter.setCurrentPos(pos);
                if (onPanelClickListener != null) onPanelClickListener.onClick(mType);

            }
        });
        RecyclerView itemList = panel.findViewById(R.id.lsq_eyeliner_item_list);
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
        mController.closeEyeliner();
        mAdapter.setCurrentPos(-1);
        if (onPanelClickListener != null) onPanelClickListener.onClear(mType);
    }
}
