package org.lasque.tusdkdemohelper.tusdk.model;

import org.lasque.tusdk.core.seles.tusdk.TuSDKMonsterFaceWrap;
import org.lasque.tusdk.cx.api.TuFilterCombo;

/******************************************************************
 * droid-sdk-video 
 * org.lasque.tusdkvideodemo.views.props.model
 *
 * @author sprint
 * @Date 2018/12/28 5:58 PM
 * @Copyright (c) 2018 tutucloud.com. All rights reserved.
 ******************************************************************/
// 哈哈镜道具
public class PropsItemMonster extends PropsItem {


    /** 哈哈镜特效 */
    private TuFilterCombo.TuFaceMonsterMode mMonsterFaceEffect;

    /** 缩略图名称 */
    private String mThumbName;


    public PropsItemMonster(TuFilterCombo.TuFaceMonsterMode monsterFaceType) {
        this.mMonsterFaceEffect = monsterFaceType;
    }

    /**
     * 设置缩略图名称
     *
     * @param thumbName
     */
    public void setThumbName(String thumbName) {
        this.mThumbName = thumbName;
    }

    /**
     * 获取缩略图名称
     *
     * @return
     */
    public String getThumbName() {
        return mThumbName;
    }

    /**
     * 获取道具对应的 SDK 特效
     *
     * @return TuSdkMediaEffectData
     */
    public TuFilterCombo.TuFaceMonsterMode effect() {
        return mMonsterFaceEffect;
    }
}

