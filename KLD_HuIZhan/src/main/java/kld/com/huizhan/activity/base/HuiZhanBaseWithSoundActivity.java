package kld.com.huizhan.activity.base;

import android.media.AudioManager;
import android.media.SoundPool;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.R;

/**
 * Created by LDW10000000 on 20/11/2017.
 */

public class HuiZhanBaseWithSoundActivity extends HuiZhanBaseActivity implements Runnable {


    @Override
    public void setContentView() {
        super.setContentView();
        //初始化
        initSoundPool();
    }


    protected void soundError() {
        mHandler.postDelayed(this, 100);
    }


    Thread playSouncThread = new playSouncThread(new playSoundRunnable());

    @Override
    public void run() {
        playSouncThread.run();
    }

    public class playSoundRunnable implements Runnable {
        @Override
        public void run() {
            LogUtil.e(TAG, "已经执行");
            playSound();
        }
    }

    public class playSouncThread extends Thread {
        public playSouncThread(Runnable target) {
            super(target);
        }
    }

    SoundPool sp;
    int shoot1id = -1;
    protected void playSound() {
        initSoundPool();
        LogUtil.e(TAG, "发出声音");
        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
    }

    protected void initSoundPool() {
        if (sp == null) {
            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            shoot1id = sp.load(this, R.raw.error, 1);
        }
    }
}




































