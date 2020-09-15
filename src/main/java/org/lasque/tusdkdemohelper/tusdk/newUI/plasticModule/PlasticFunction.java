package org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule;

import com.example.tusdkdemohelper.R;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  11:42
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public enum PlasticFunction {

    FOREHEAD("forehead", "发际线", R.drawable.plastic_hairline_ic, R.drawable.plastic_hairline_sel_ic),
    CHINSIZE("chinSize", "瘦脸", R.drawable.plastic_thinface_ic, R.drawable.plastic_thinface_sel_ic),
    BROWPOSITION("browPosition", "眉高", R.drawable.plastic_eyebrowheight_ic, R.drawable.plastic_eyebrowheight_sel_ic),
    ARCHEYEBROW("archEyebrow", "细眉", R.drawable.plastic_eyebrows_ic, R.drawable.plastic_eyebrows_sel_ic),
    EYESIZE("eyeSize", "大眼", R.drawable.plastic_bigeye_ic, R.drawable.plastic_bigeye_sel_ic),
    EYEANGLE("eyeAngle", "眼角", R.drawable.plastic_eyecorner_ic, R.drawable.plastic_eyecorner_sel_ic),
    EYEDIS("eyeDis", "眼距", R.drawable.plastic_eyedistance_ic, R.drawable.plastic_eyedistance_sel_ic),
    NOSESIZE("noseSize", "瘦鼻", R.drawable.plastic_thinnoise_ic, R.drawable.plastic_thinnoise_sel_ic),
    MOUTHWIDTH("mouthWidth", "嘴型", R.drawable.plastic_mouse_ic, R.drawable.plastic_mouse_sel_ic),
    LIPS("lips", "唇厚", R.drawable.plastic_lips_ic, R.drawable.plastic_lips_sel_ic),
    JAWSIZE("jawSize", "下巴", R.drawable.plastic_chin_ic, R.drawable.plastic_chin_sel_ic);


    public String code;
    public String title;
    public int defIconId;
    public int selIconId;

    PlasticFunction(String code, String title, int defIconId, int selIconId) {
        this.code = code;
        this.title = title;
        this.defIconId = defIconId;
        this.selIconId = selIconId;
    }
}
