package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import org.lasque.tusdk.core.seles.tusdk.FilterGroup;
import org.lasque.tusdk.core.seles.tusdk.FilterLocalPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @Date 2018/11/26
 */

public class TuConfig {
    // 录制滤镜 code 列表, 每个 code 代表一种滤镜效果, 具体 code 可在 lsq_tusdk_configs.json 查看 (例如:lsq_filter_SkinNature02 滤镜的 code 为 SkinNature02)
//    public static final String[] CAMERA_FILTER_CODES = new String[]{"None","SkinLotus_2","SkinNatural_2","SkinFair_2",  "SkinBeckoning_2", "SkinTender_2",
//            "SkinLeisurely_2", "SkinRose_2", "SkinWarm_2","SkinClear_2","SkinConfession_2","SkinJapanese_2","SkinExtraordinary_2","SkinHoney_2",
//            "SkinButter_2","SkinDawn_2","SkinSummer_2","SkinSweet_2","SkinPlain_2","SkinDusk_2","SkinNostalgia_2"};


    public static final String[] CAMERA_FILTER_CODES = FilterLocalPackage.shared().getCodes().toArray(new String[FilterLocalPackage.shared().getCodes().size()]);

    public static List<FilterGroup> getFilterGroups() {
        List<FilterGroup> groups = FilterLocalPackage.shared().getGroups();
//        for (FilterGroup group : groups){
//            if (group.groupFiltersType != 0){
//                groups.remove(group);
//            }
//        }
        return groups;
    }
}
