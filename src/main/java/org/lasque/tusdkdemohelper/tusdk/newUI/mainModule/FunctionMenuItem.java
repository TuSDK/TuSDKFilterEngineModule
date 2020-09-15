package org.lasque.tusdkdemohelper.tusdk.newUI.mainModule;

import com.example.tusdkdemohelper.R;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/5  14:19
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public enum FunctionMenuItem {
    FUNCTION_SKIN("美肤", R.drawable.home_skin_ic),
    FUNCTION_PLASTIC("微整形", R.drawable.home_plastic_ic),
    FUNCTION_FILTER("滤镜", R.drawable.home_filter_ic),
    FUNCTION_FACE_STICKER("贴纸", R.drawable.home_stickers_ic),
    FUNCTION_MONSTER_FACE("哈哈镜", R.drawable.home_mirror_ic),
    FUNCTION_VOICE("变声", R.drawable.home_voice_ic);

    public String title;
    public int iconId;

    FunctionMenuItem(String title, int iconID) {
        this.title = title;
        this.iconId = iconID;
    }

}
