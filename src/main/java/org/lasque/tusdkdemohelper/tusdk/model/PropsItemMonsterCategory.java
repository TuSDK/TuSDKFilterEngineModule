package org.lasque.tusdkdemohelper.tusdk.model;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.cx.api.TuFilterCombo;

import java.util.ArrayList;
import java.util.List;
import static org.lasque.tusdk.cx.api.TuFilterCombo.TuFaceMonsterMode.*;

/******************************************************************
 * droid-sdk-video 
 * org.lasque.tusdkvideodemo.views.props.model
 *
 * @author sprint
 * @Date 2018/12/28 11:20 AM
 * @Copyright (c) 2018 tutucloud.com. All rights reserved.
 ******************************************************************/
// 哈哈镜分类
public class PropsItemMonsterCategory extends PropsItemCategory<PropsItemMonster> {

    /**
     * 根据哈哈镜道具列表初始化道具分类
     *
     * @param propsItemMonsters
     */
    public PropsItemMonsterCategory(List<PropsItemMonster> propsItemMonsters) {
        super(propsItemMonsters);
    }

    /**
     * 获取所有哈哈镜分类
     *
     * @return List<PropsItemMonsterCategory>
     */
    public static List<PropsItemMonsterCategory> allCategories() {

        TuFilterCombo.TuFaceMonsterMode[] faceTypes =
                {
                        BigNose, // 大鼻子
                        PapayaFace, // 木瓜脸
                        PieFace, // 大饼脸
                        SmallEyes, // 眯眯眼
                        SnakeFace, // 蛇精脸
                        SquareFace, // 国字脸
                        ThickLips // 厚嘴唇
                };


        // 缩略图后缀
        String[] faceTypeTitles =
                {
                        "bignose",
                        "papaya",
                        "pie",
                        "smalleyes",
                        "snake",
                        "square",
                        "thicklips"
                };



        List<PropsItemMonsterCategory> categories = new ArrayList<>();
        List<PropsItemMonster> monsters = new ArrayList<>();

        for (int i = 0; i<faceTypes.length; i++) {

            PropsItemMonster monster = new PropsItemMonster(faceTypes[i]);
            monster.setThumbName(faceTypeTitles[i]);
            monsters.add(monster);
        }

        PropsItemMonsterCategory monsterCategory = new PropsItemMonsterCategory(monsters);
        monsterCategory.setName(TuSdkContext.getString(TuSdkContext.getStringResId("lsq_face_monster")));
        categories.add(monsterCategory);

        return categories;
    }
}

