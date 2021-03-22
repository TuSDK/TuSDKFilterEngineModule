package org.lasque.tusdkdemohelper.tusdk.model;

import com.example.tusdkdemohelper.R;
import com.tusdk.pulse.filter.filters.TusdkFaceMonsterFilter;

import org.lasque.tusdkpulse.core.TuSdkContext;
import org.lasque.tusdkpulse.core.seles.SelesParameters;

import java.util.ArrayList;
import java.util.List;

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
        super(SelesParameters.FilterModel.MonsterFace,propsItemMonsters);
    }

    /**
     * 获取所有哈哈镜分类
     *
     * @return List<PropsItemMonsterCategory>
     */
    public static List<PropsItemMonsterCategory> allCategories() {

        String[] faceTypes =
                {
                        TusdkFaceMonsterFilter.TYPE_BigNose, // 大鼻子
                        TusdkFaceMonsterFilter.TYPE_PapayaFace, // 木瓜脸
                        TusdkFaceMonsterFilter.TYPE_PieFace, // 大饼脸
                        TusdkFaceMonsterFilter.TYPE_SnakeFace, // 蛇精脸
                        TusdkFaceMonsterFilter.TYPE_SquareFace, // 国字脸
                        TusdkFaceMonsterFilter.TYPE_ThickLips, // 厚嘴唇
                        TusdkFaceMonsterFilter.TYPE_SmallEyes, // 眯眯眼

                };


        // 缩略图后缀
        String[] faceTypeTitles =
                {
                        "bignose",
                        "papaya",
                        "pie",
                        "snake",
                        "square",
                        "thicklips",
                        "smalleyes"
                };



        List<PropsItemMonsterCategory> categories = new ArrayList<>();
        List<PropsItemMonster> monsters = new ArrayList<>();

        for (int i = 0; i<faceTypes.length; i++) {

            PropsItemMonster monster = new PropsItemMonster(faceTypes[i]);
            monster.setThumbName(faceTypeTitles[i]);
            monsters.add(monster);
        }

        PropsItemMonsterCategory monsterCategory = new PropsItemMonsterCategory(monsters);
        monsterCategory.setName(TuSdkContext.getString(R.string.lsq_face_monster));
        categories.add(monsterCategory);

        return categories;
    }
}

