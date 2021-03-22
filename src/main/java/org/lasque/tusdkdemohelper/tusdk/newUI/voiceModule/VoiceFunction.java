package org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule;

import com.example.tusdkdemohelper.R;
/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.voiceModule
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/9/1  9:40
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public enum VoiceFunction {

    GIRL("Lolita","萝莉", R.drawable.voice_girl_ic,R.drawable.voice_girl_sel_ic),
    MONSTER("Monster","怪兽",R.drawable.voice_monster_ic,R.drawable.voice_monster_sel_ic),
    UNCLE("Uncle","大叔",R.drawable.voice_uncle_ic,R.drawable.voice_uncle_sel_ic),
    WOMAN("Girl","女生",R.drawable.voice_woman_ic,R.drawable.voice_woman_sel_ic);

    public String title;
    public int defIconId;
    public int selIconId;
    public String type;

    VoiceFunction(String type,String title, int defIconId, int selIconId) {
        this.title = title;
        this.defIconId = defIconId;
        this.selIconId = selIconId;
        this.type = type;
    }
}
