package com.ldw.xyz.util.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.ldw.xyz.util.LogUtil;

/**
 * Created by LDW10000000 on 30/06/2017.
 * <p>
 * 重要: 1.第一次用的时候不要直接调用play方法, 需要在activity的onCreate或什么地方,
 * 先调用initSoundPool方法;
 * 2.SoundPool sp首次调用传入null就行了;
 */

public class SoundUtil {

    private static int shoot1id = -1;

    /**
     * @param sp
     * @param context
     * @param soundPath
     */
    public static void play(SoundPool sp, Context context, int soundPath) {
        initSoundPool(sp, context, soundPath);
        LogUtil.e("TAG", "发出声音");
        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
    }

    /**
     * 先调用这个
     *
     * @param sp
     * @param context
     * @param soundId
     * @return
     */
    public static SoundPool initSoundPool(SoundPool sp, Context context, int soundId) {
        if (sp == null) {
            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            shoot1id = sp.load(context, soundId, 1);
        }
        return sp;
    }


}
