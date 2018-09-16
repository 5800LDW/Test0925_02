package kld.com.rfid.ldw;

import android.app.Dialog;
import android.app.TabActivity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

import com.ldw.xyz.util.LogUtil;
import com.maning.mndialoglibrary.MStatusDialog;

import kld.com.rfid.ldw.util.ProgressUtil;

/**
 * Created by LDW10000000 on 20/11/2017.
 */

public class SuoFeiYaBaseActivity extends TabActivity implements Runnable{


    public Dialog showUploadErrorDialog(String content){

        return ProgressUtil.reminderError(this,"上传失败",content);
    }

    public static Handler mHandler = new Handler();


    public void soundError() {
        mHandler.postDelayed(this, 100);
    }

    public Thread playSouncThread = new playSouncThread(new playSoundRunnable());
    @Override
    public void run() {
        playSouncThread.run();
    }
    public class playSoundRunnable implements Runnable {
        @Override
        public void run() {
            LogUtil.e("TAG", "已经执行");
            playSound();
        }
    }
    public class playSouncThread extends Thread {
        public playSouncThread(Runnable target) {
            super(target);
        }
    }
    public SoundPool sp;
    public int shoot1id = -1;
    protected void playSound() {
        initSoundPool();
        LogUtil.e("TAG", "发出声音");
        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
    }
    public void initSoundPool() {
        if (sp == null) {
            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            shoot1id = sp.load(this, R.raw.alarmbell5s, 1);
        }
    }





    public void showSuccessUploadDialog(){
//        PromptDialog.viewAnimDuration= 300;
//        PromptDialog pd =  new PromptDialog(this);
//        pd.showSuccess("上传成功");
//        return  pd;

        //默认
         new MStatusDialog(this).show2("上传成功", getResources().getDrawable(R.drawable.mn_icon_dialog_ok),1500);
    }


























}




































