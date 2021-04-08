package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import org.lasque.tusdkpulse.core.seles.tusdk.FilterGroup;
import org.lasque.tusdkpulse.core.seles.tusdk.FilterLocalPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


    /**
     * 编辑滤镜 filterCode 列表
     */
    public static String[] VIDEO_EDIT_FILTERS = {"None", "Olympus_1", "Leica_1", "Gold_1", "Cheerful_1",
            "White_1", "s1950_1", "Blurred_1", "Newborn_1", "Fade_1", "NewYork_1"};



    public static final String[] CAMERA_FILTER_CODES = FilterLocalPackage.shared().getCodes().toArray(new String[FilterLocalPackage.shared().getCodes().size()]);

    /**
     * @param hasCantoon
     * @return
     */
    public static List<FilterGroup> getCameraFilters(boolean hasCantoon){
        List<FilterGroup> filterGroup = FilterLocalPackage.shared().getLiveGroups();
        List<FilterGroup> result = new ArrayList<>();

        for (FilterGroup group : filterGroup){
            if (group.groupFiltersType == 0){
//                Collections.sort(group.filters, new Comparator<FilterOption>() {
//                    @Override
//                    public int compare(FilterOption o1, FilterOption o2) {
//                        return Long.compare(o1.id, o2.id);
//                    }
//                });
                result.add(group);
            }
        }

        Collections.sort(result, new Comparator<FilterGroup>() {
            @Override
            public int compare(FilterGroup o1, FilterGroup o2) {
                return Long.compare(o1.groupId, o2.groupId);
            }
        });

        if (hasCantoon){
            FilterGroup cartoon = FilterLocalPackage.shared().getFilterGroup(252);
            result.add(cartoon);
        }
        return result;
    }

    public static ArrayList<String> getColorList(boolean hasCantoon){

        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("CE4564");
        colorList.add("E09E0D");
        colorList.add("1F8737");
        colorList.add("1B61E3");
        if (hasCantoon)
            colorList.add("1B61E9");

        return colorList;
    }

}
