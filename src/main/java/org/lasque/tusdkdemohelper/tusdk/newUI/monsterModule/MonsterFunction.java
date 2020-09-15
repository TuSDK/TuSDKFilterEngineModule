package org.lasque.tusdkdemohelper.tusdk.newUI.monsterModule;

import com.example.tusdkdemohelper.R;

import org.lasque.tusdk.cx.api.TuFilterCombo;
import org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode;

import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.BigNose;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.PapayaFace;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.PieFace;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.SmallEyes;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.SnakeFace;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.SquareFace;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.ThickLips;

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
    BIGNOSE(BigNose, R.drawable.lsq_ic_face_monster_bignose,"大鼻子"),
    PIEFACE(PieFace,R.drawable.lsq_ic_face_monster_pie,"大饼脸"),
    SQUAREFACE(SquareFace,R.drawable.lsq_ic_face_monster_square,"国字脸"),
    THICKLIPS(ThickLips,R.drawable.lsq_ic_face_monster_thicklips,"厚嘴唇"),
    SMALLEYES(SmallEyes,R.drawable.lsq_ic_face_monster_smalleyes,"眯眯眼"),
    PAPAYAFACE(PapayaFace,R.drawable.lsq_ic_face_monster_papaya,"木瓜脸"),
    SNAKEFACE(SnakeFace,R.drawable.lsq_ic_face_monster_snake,"蛇精脸");


    public TuFaceMonsterMode mode;
    public int iconId;
    public String title;

    MonsterFunction(TuFaceMonsterMode mode, int iconId, String title) {
        this.mode = mode;
        this.iconId = iconId;
        this.title = title;
    }
}
