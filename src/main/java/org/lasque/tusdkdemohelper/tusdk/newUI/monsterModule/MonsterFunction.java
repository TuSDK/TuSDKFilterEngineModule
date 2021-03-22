package org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.filter.filters.TusdkFaceMonsterFilter;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/6  15:50
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public enum MonsterFunction {
    BIGNOSE(TusdkFaceMonsterFilter.TYPE_BigNose, R.drawable.lsq_ic_face_monster_bignose,"大鼻子"),
    PIEFACE(TusdkFaceMonsterFilter.TYPE_PieFace,R.drawable.lsq_ic_face_monster_pie,"大饼脸"),
    SQUAREFACE(TusdkFaceMonsterFilter.TYPE_SquareFace,R.drawable.lsq_ic_face_monster_square,"国字脸"),
    THICKLIPS(TusdkFaceMonsterFilter.TYPE_ThickLips,R.drawable.lsq_ic_face_monster_thicklips,"厚嘴唇"),
    SMALLEYES(TusdkFaceMonsterFilter.TYPE_SmallEyes,R.drawable.lsq_ic_face_monster_smalleyes,"眯眯眼"),
    PAPAYAFACE(TusdkFaceMonsterFilter.TYPE_PapayaFace,R.drawable.lsq_ic_face_monster_papaya,"木瓜脸"),
    SNAKEFACE(TusdkFaceMonsterFilter.TYPE_SnakeFace,R.drawable.lsq_ic_face_monster_snake,"蛇精脸");


    public String mode;
    public int iconId;
    public String title;

    MonsterFunction(String mode, int iconId, String title) {
        this.mode = mode;
        this.iconId = iconId;
        this.title = title;
    }
}
