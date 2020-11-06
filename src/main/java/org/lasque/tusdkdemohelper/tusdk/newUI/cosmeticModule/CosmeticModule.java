package org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.cx.api.TuFilterEngine;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.BaseModule;
import org.lasque.tusdkdemohelper.tusdk.newUI.base.ModuleController;
import org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule.panel.BasePanel;

import java.util.Arrays;
import java.util.HashSet;

import static org.lasque.tusdkdemohelper.tusdk.newUI.base.FunctionsType.COSMETIC;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.cosmeticModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/11/6  14:37
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class CosmeticModule extends BaseModule {

    private CosmeticPanelController mCosmeticController;

    private LinearLayout mLipstick, mBlush, mEyebrow, mEyeshadow, mEyeliner, mEyelash, mCosmeticClear;

    private RelativeLayout mLipstickPanel, mBlushPanel, mEyebrowPanel, mEyeshadowPanel, mEyelinerPanel, mEyelashPanel;

    private ImageView mBackView;

    private CosmeticTypes.Types mCurrentType;

    private HashSet<CosmeticTypes.Types> types = new HashSet<>();

    private HorizontalScrollView mCosmeticScroll;

    private int lastPos = -1;

    private BasePanel.OnPanelClickListener panelClickListener = new BasePanel.OnPanelClickListener() {
        @Override
        public void onClear(CosmeticTypes.Types type) {
            int viewID = -1;
            switch (type) {
                case Lipstick:
                    viewID = R.id.lsq_lipstick_add;
                    break;
                case Blush:
                    viewID = R.id.lsq_blush_add;
                    break;
                case Eyebrow:
                    viewID = R.id.lsq_eyebrow_add;
                    break;
                case Eyeshadow:
                    viewID = R.id.lsq_eyeshadow_add;
                    break;
                case Eyeliner:
                    viewID = R.id.lsq_eyeliner_add;
                    break;
                case Eyelash:
                    viewID = R.id.lsq_eyelash_add;
                    break;
            }
            mCurrentView.findViewById(viewID).setVisibility(View.GONE);
            mConfigView.setVisibility(View.GONE);
            types.remove(types);
        }

        @Override
        public void onClose(CosmeticTypes.Types type) {
            switch (type) {
                case Lipstick:
                    mLipstickPanel.setVisibility(View.GONE);
                    break;
                case Blush:
                    mBlushPanel.setVisibility(View.GONE);
                    break;
                case Eyebrow:
                    mEyebrowPanel.setVisibility(View.GONE);
                    break;
                case Eyeshadow:
                    mEyeshadowPanel.setVisibility(View.GONE);
                    break;
                case Eyeliner:
                    mEyelinerPanel.setVisibility(View.GONE);
                    break;
                case Eyelash:
                    mEyelashPanel.setVisibility(View.GONE);
                    break;
            }
            mCurrentType = null;
            mConfigView.setVisibility(View.GONE);
            mPreButton.setVisibility(View.VISIBLE);
            mCosmeticScroll.scrollTo(lastPos, 0);
        }

        @Override
        public void onClick(CosmeticTypes.Types type) {
            int viewID = -1;
            switch (type) {
                case Lipstick:
                    viewID = R.id.lsq_lipstick_add;
                    break;
                case Blush:
                    viewID = R.id.lsq_blush_add;
                    break;
                case Eyebrow:
                    viewID = R.id.lsq_eyebrow_add;
                    break;
                case Eyeshadow:
                    viewID = R.id.lsq_eyeshadow_add;
                    break;
                case Eyeliner:
                    viewID = R.id.lsq_eyeliner_add;
                    break;
                case Eyelash:
                    viewID = R.id.lsq_eyelash_add;
                    break;
            }
            mCurrentView.findViewById(viewID).setVisibility(View.VISIBLE);
            mConfigView.setVisibility(View.VISIBLE);
            types.add(type);
        }
    };

    private View mPreButton;

    public CosmeticModule(ModuleController controller, Context context) {
        super(controller, COSMETIC, context);
        mCosmeticController = new CosmeticPanelController(context);
        findViews();
    }

    @Override
    public void setFilterEngine(TuFilterEngine engine) {
        super.setFilterEngine(engine);
        mCosmeticController.setEngine(engine);
    }

    @Override
    protected View getView() {
        return LayoutInflater.from(mContext).inflate(R.layout.tusdk_cosmetic_module_layout,null,false);
    }

    private void initCosmeticView() {
        mCosmeticController.setPanelClickListener(panelClickListener);

        mCosmeticClear = mCurrentView.findViewById(R.id.lsq_cosmetic_item_clear);
        mCosmeticClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCosmetic();
            }
        });

        mLipstick = mCurrentView.findViewById(R.id.lsq_cosmetic_item_lipstick);
        mLipstick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Lipstick;
                mLipstick.setVisibility(View.GONE);
                mPreButton = mLipstick;
                mLipstickPanel.setVisibility(mLipstickPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mBlushPanel.setVisibility(View.GONE);
                mEyebrowPanel.setVisibility(View.GONE);
                mEyeshadowPanel.setVisibility(View.GONE);
                mEyelinerPanel.setVisibility(View.GONE);
                mEyelashPanel.setVisibility(View.GONE);
                if (mLipstickPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_lipstick_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("lipAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mLipstickPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);
                }
            }
        });
        mLipstickPanel = mCurrentView.findViewById(R.id.lsq_lipstick_panel);
        mLipstickPanel.addView(mCosmeticController.getLipstickPanel().getPanel());

        mBlush = mCurrentView.findViewById(R.id.lsq_cosmetic_item_blush);
        mBlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Blush;
                mBlush.setVisibility(View.GONE);
                mPreButton = mBlush;
                mLipstickPanel.setVisibility(View.GONE);
                mBlushPanel.setVisibility(mBlushPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mEyebrowPanel.setVisibility(View.GONE);
                mEyeshadowPanel.setVisibility(View.GONE);
                mEyelinerPanel.setVisibility(View.GONE);
                mEyelashPanel.setVisibility(View.GONE);
                if (mBlushPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_blush_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("blushAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mBlushPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);
                }
            }
        });
        mBlushPanel = mCurrentView.findViewById(R.id.lsq_blush_panel);
        mBlushPanel.addView(mCosmeticController.getBlushPanel().getPanel());

        mEyebrow = mCurrentView.findViewById(R.id.lsq_cosmetic_item_eyebrow);
        mEyebrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Eyebrow;
                mEyebrow.setVisibility(View.GONE);
                mPreButton = mEyebrow;
                mLipstickPanel.setVisibility(View.GONE);
                mBlushPanel.setVisibility(View.GONE);
                mEyebrowPanel.setVisibility(mEyebrowPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mEyeshadowPanel.setVisibility(View.GONE);
                mEyelinerPanel.setVisibility(View.GONE);
                mEyelashPanel.setVisibility(View.GONE);
                if (mEyebrowPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_eyebrow_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("eyebrowAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mEyebrowPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);
                }
            }
        });
        mEyebrowPanel = mCurrentView.findViewById(R.id.lsq_eyebrow_panel);
        mEyebrowPanel.addView(mCosmeticController.getEyebrowPanel().getPanel());

        mEyeshadow = mCurrentView.findViewById(R.id.lsq_cosmetic_item_eyeshadow);
        mEyeshadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Eyeshadow;
                mEyeshadow.setVisibility(View.GONE);
                mPreButton = mEyeshadow;
                mLipstickPanel.setVisibility(View.GONE);
                mBlushPanel.setVisibility(View.GONE);
                mEyebrowPanel.setVisibility(View.GONE);
                mEyeshadowPanel.setVisibility(mEyeshadowPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mEyelinerPanel.setVisibility(View.GONE);
                mEyelashPanel.setVisibility(View.GONE);
                if (mEyeshadowPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_eyeshadow_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("eyeshadowAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mEyeshadowPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);
                }
            }
        });
        mEyeshadowPanel = mCurrentView.findViewById(R.id.lsq_eyeshadow_panel);
        mEyeshadowPanel.addView(mCosmeticController.getEyeshadowPanel().getPanel());

        mEyeliner = mCurrentView.findViewById(R.id.lsq_cosmetic_item_eyeliner);
        mEyeliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Eyeliner;
                mEyeliner.setVisibility(View.GONE);
                mPreButton = mEyeliner;
                mLipstickPanel.setVisibility(View.GONE);
                mBlushPanel.setVisibility(View.GONE);
                mEyebrowPanel.setVisibility(View.GONE);
                mEyeshadowPanel.setVisibility(View.GONE);
                mEyelinerPanel.setVisibility(mEyelinerPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mEyelashPanel.setVisibility(View.GONE);
                if (mEyelinerPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_eyeliner_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("eyelineAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mEyelinerPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);
                }
            }
        });
        mEyelinerPanel = mCurrentView.findViewById(R.id.lsq_eyeliner_panel);
        mEyelinerPanel.addView(mCosmeticController.getEyelinerPanel().getPanel());

        mEyelash = mCurrentView.findViewById(R.id.lsq_cosmetic_item_eyelash);
        mEyelash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPos = mCosmeticScroll.getScrollX();
                if (mPreButton != null) mPreButton.setVisibility(View.VISIBLE);
                mCurrentType = CosmeticTypes.Types.Eyelash;
                mEyelash.setVisibility(View.GONE);
                mPreButton = mEyelash;
                mLipstickPanel.setVisibility(View.GONE);
                mBlushPanel.setVisibility(View.GONE);
                mEyebrowPanel.setVisibility(View.GONE);
                mEyeshadowPanel.setVisibility(View.GONE);
                mEyelinerPanel.setVisibility(View.GONE);
                mEyelashPanel.setVisibility(mEyelashPanel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (mEyelashPanel.getVisibility() == View.VISIBLE) {
                    mConfigView.setVisibility(mCurrentView.findViewById(R.id.lsq_eyelash_add).getVisibility());
//                    mConfigView.setFilterArgs(Arrays.asList(mParameters.getFilterArg("eyelashAlpha")));
                    mCurrentView.findViewById(R.id.list_panel).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            mCosmeticScroll.scrollTo(mEyelashPanel.getLeft(), 0);
                            mCurrentView.findViewById(R.id.list_panel).removeOnLayoutChangeListener(this);
                        }
                    });
                } else {
                    mConfigView.setVisibility(View.GONE);

                }
            }
        });
        mEyelashPanel = mCurrentView.findViewById(R.id.lsq_eyelash_panel);
        mEyelashPanel.addView(mCosmeticController.getEyelashPanel().getPanel());
        mEyelashPanel.setVisibility(View.VISIBLE);
        mEyelashPanel.requestLayout();
        mEyelashPanel.setVisibility(View.GONE);

        mCosmeticScroll = mCurrentView.findViewById(R.id.lsq_cosmetic_scroll_view);
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
        initCosmeticView();
    }

    private void clearCosmetic() {
        if (types.size() == 0) return;
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        adBuilder.setTitle(R.string.lsq_text_cosmetic_type);
        adBuilder.setMessage(R.string.lsq_clear_beauty_cosmetic_hit);
        adBuilder.setNegativeButton(R.string.lsq_audioRecording_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adBuilder.setPositiveButton(R.string.lsq_audioRecording_next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCosmeticController.clearAllCosmetic();
                types.removeAll(Arrays.asList(CosmeticTypes.Types.values()));
            }
        });
        adBuilder.show();
    }
}
