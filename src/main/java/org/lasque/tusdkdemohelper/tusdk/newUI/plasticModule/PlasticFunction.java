package org.lasque.tusdkdemohelper.tusdk.newUI.plasticModule;

import com.example.tusdkdemohelper.R;

import java.util.HashMap;

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

    EYESIZE("eyeSize", "大眼", R.drawable.plastic_bigeye_ic, R.drawable.plastic_bigeye_sel_ic),
    CHINSIZE("chinSize", "瘦脸", R.drawable.plastic_thinface_ic, R.drawable.plastic_thinface_sel_ic),
    CHEEKNARROW("cheekNarrow","窄脸",R.drawable.lsq_ic_cheeknarrow_normal,R.drawable.lsq_ic_cheeknarrow_sel),
    SMALLFACE("smallFace","小脸",R.drawable.lsq_ic_smallface_normal,R.drawable.lsq_ic_smallface_sel),
    NOSESIZE("noseSize", "瘦鼻", R.drawable.plastic_thinnoise_ic, R.drawable.plastic_thinnoise_sel_ic),
    NOSEHEIGHT("noseHeight","长鼻",R.drawable.lsq_ic_noseheight_normal,R.drawable.lsq_ic_noseheight_sel),
    MOUTHWIDTH("mouthWidth", "嘴型", R.drawable.plastic_mouse_ic, R.drawable.plastic_mouse_sel_ic),
    LIPS("lips", "唇厚", R.drawable.plastic_lips_ic, R.drawable.plastic_lips_sel_ic),
    PHILTERUM("philterum","缩人中",R.drawable.lsq_ic_philterum_normal,R.drawable.lsq_ic_philterum_sel),
    ARCHEYEBROW("archEyebrow", "细眉", R.drawable.plastic_eyebrows_ic, R.drawable.plastic_eyebrows_sel_ic),
    BROWPOSITION("browPosition", "眉高", R.drawable.plastic_eyebrowheight_ic, R.drawable.plastic_eyebrowheight_sel_ic),
    JAWSIZE("jawSize", "下巴", R.drawable.plastic_chin_ic, R.drawable.plastic_chin_sel_ic),
    CHEEKLOWBONENARROW("cheekLowBoneNarrow","下颌骨",R.drawable.lsq_ic_cheeklowbonenarrow_normal,R.drawable.lsq_ic_cheeklowbonenarrow_sel),
    EYEANGLE("eyeAngle", "眼角", R.drawable.plastic_eyecorner_ic, R.drawable.plastic_eyecorner_sel_ic),
    EYEINNERCONER("eyeInnerConer","开内眼角",R.drawable.lsq_ic_eyeinnerconer_normal,R.drawable.lsq_ic_eyeinnerconer_sel),
    EYEOUTERCONER("eyeOuterConer","开外眼角",R.drawable.lsq_ic_eyeouterconer_normal,R.drawable.lsq_ic_eyeouterconer_sel),
    EYEDIS("eyeDis", "眼距", R.drawable.plastic_eyedistance_ic, R.drawable.plastic_eyedistance_sel_ic),
    EYEHEIGHT("eyeHeight","眼移动",R.drawable.lsq_ic_eyeheight_normal,R.drawable.lsq_ic_eyeheight_sel),
    FOREHEAD("forehead", "发际线", R.drawable.plastic_hairline_ic, R.drawable.plastic_hairline_sel_ic),
    CHEEKBONENARROW("cheekBoneNarrow","瘦颚骨",R.drawable.lsq_ic_cheekbonenarrow_normal,R.drawable.lsq_ic_cheekbonenarrow_sel),

    /** ---------------------------- Reshape ----------------------------------------------- */
    EYELID("eyelidAlpha","双眼皮",R.drawable.lsq_ic_eyelidalpha_normal,R.drawable.lsq_ic_eyelidalpha_sel),
    EYEMAZING("eyemazingAlpha","卧蚕",R.drawable.lsq_ic_eyemazingalpha_normal,R.drawable.lsq_ic_eyemazingalpha_sel),
    WHITENTEETH("whitenTeethAlpha","白牙",R.drawable.lsq_ic_whitenteethalpha_normal,R.drawable.lsq_ic_whitenteethalpha_sel),
    EYEDETAIL("eyeDetailAlpha","亮眼",R.drawable.lsq_ic_eyedetailalpha_normal,R.drawable.lsq_ic_eyedetailalpha_sel),
    REMOVEPOUCH("removePouchAlpha","黑眼圈",R.drawable.lsq_ic_removepouchalpha_normal,R.drawable.lsq_ic_removepouchalpha_sel),
    REMOVEWRINKLES("removeWrinklesAlpha","法令纹",R.drawable.lsq_ic_removewrinklesalpha_normal,R.drawable.lsq_ic_removewrinklesalpha_sel);

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
