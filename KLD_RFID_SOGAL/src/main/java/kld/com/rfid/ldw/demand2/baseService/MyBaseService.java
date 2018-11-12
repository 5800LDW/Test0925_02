package kld.com.rfid.ldw.demand2.baseService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.demand2.logepc.LogEpcID;
import kld.com.rfid.ldw.demand2.logepc2.LogEpcID2;

/**
 * Created by liudongwen on 2018/9/1.
 */

public abstract class MyBaseService extends Service implements BizInterface{

    public volatile boolean isCanRunRead = true;
    public  static List<Map<String, String>> list = new ArrayList<>();
    public  static Set<String> uploadSet = new HashSet<>();
//    public static Set<String> uploadSet = new LinkedHashSet<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public abstract  void scanBiz();


    public abstract void setPower();







//    public void soundError() {
//        mHandler.postDelayed(this, 100);
//    }
//
//    public Thread playSouncThread = new playSouncThread(new playSoundRunnable());
//
//    public void run() {
//        playSouncThread.run();
//    }
//    public class playSoundRunnable implements Runnable {
//        @Override
//        public void run() {
//            LogUtil.e("TAG", "已经执行");
//            playSound();
//        }
//    }
//    public class playSouncThread extends Thread {
//        public playSouncThread(Runnable target) {
//            super(target);
//        }
//    }
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




//    public SoundPool sp;
//    public int shoot1id = -1;
//    protected void playSound() {
//        initSoundPool();
//        LogUtil.e("TAG", "发出声音");
//        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
//    }
//    public void initSoundPool() {
//        if (sp == null) {
//            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//            shoot1id = sp.load(this, R.raw.alarmbell5s, 1);
//        }
//    }

//    public void soundOU(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int shoot1id = sp.load(MyBaseService.this, R.raw.sound_ou, 1);
//                sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
//            }
//        }).start();
//    }
//
//
//    public void soundWeiPaiCheng(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int shoot1id = sp.load(MyBaseService.this, R.raw.sound_weipaicheng, 1);
//                playMySound(soundPool,id);
//            }
//        }).start();
//    }
//
//
//    public void soundFeiYiDianShu(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int shoot1id = sp.load(MyBaseService.this, R.raw.sound_feiyidianshu, 1);
//                playMySound(soundPool,id);
//            }
//        }).start();
//    }
//
//    public void soundChuanHuo(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int id = soundPool.load(MyBaseService.this, R.raw.sound_chuanhuo, 1);
//                playMySound(soundPool,id);
//            }
//        }).start();
//    }
//
//
//    public void soundLouHuo(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int id = soundPool.load(MyBaseService.this, R.raw.sound_louhuo, 1);
//                playMySound(soundPool,id);
//
//            }
//        }).start();
//    }
//
//
//
//
//
//    public void soundQuXiao(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int id = soundPool.load(MyBaseService.this, R.raw.sound_quxiao, 1);
////
//                playMySound(soundPool,id);
//
////                mHandler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        soundPool.play(id, 1f, 1f, 0, 0, 1.0f);
////                    }
////                },300);
//
//            }
//        }).start();
//
//
//    }

//    Handler threadHandler ;


    static SoundPool allSoundPool;

    static int id_sound_ou3;
    static int id_sound_weipaicheng3;
    static int id_sound_feiyidianshu3;
    static int id_sound_cuanhuo3;
    static int id_sound_louhuo3;
    static int id_sound_quxiao3;
    static int id_alarmbell5s;
    static int id_sound_houtaierror;
    static int id_sound_success;


    public static void  initAllSoundPool(){
        allSoundPool  = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        id_sound_ou3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_ou3, 1);
        id_sound_weipaicheng3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_weipaicheng3, 1);
        id_sound_feiyidianshu3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_feiyidianshu3, 1);
        id_sound_cuanhuo3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_cuanhuo3, 1);
        id_sound_louhuo3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_louhuo3, 1);
        id_sound_quxiao3 = allSoundPool.load(RFIDApplication.instance, R.raw.sound_quxiao3, 1);//alarmbell5s//sound_houtaierror
        id_alarmbell5s = allSoundPool.load(RFIDApplication.instance, R.raw.alarmbell5s, 1);//alarmbell5s//sound_houtaierror
        id_sound_houtaierror = allSoundPool.load(RFIDApplication.instance, R.raw.sound_houtaierror, 1);//alarmbell5s//sound_houtaierror
        id_sound_success = allSoundPool.load(RFIDApplication.instance, R.raw.sound_success3, 1);
    }



    static Vibrator vibrator = (Vibrator) RFIDApplication.instance.getSystemService(Context.VIBRATOR_SERVICE);

    public void shake(){
        vibrator.vibrate(3000);
    }

    public void soundErrorNow(final int rawId){

        switch (rawId){
            case  R.raw.sound_ou3:
                playMySound(id_sound_ou3);
                break;

            case  R.raw.sound_weipaicheng3:
                playMySound(id_sound_weipaicheng3);
                break;

            case  R.raw.sound_feiyidianshu3:
                playMySound(id_sound_feiyidianshu3);
                break;

            case  R.raw.sound_cuanhuo3:
                playMySound(id_sound_cuanhuo3);
                break;

            case  R.raw.sound_louhuo3:
                playMySound(id_sound_louhuo3);
                break;

            case  R.raw.sound_quxiao3:
                playMySound(id_sound_quxiao3);
                break;

            case  R.raw.alarmbell5s:
                playMySound(id_alarmbell5s);
                break;

            case  R.raw.sound_houtaierror:
                playMySound(id_sound_houtaierror);
                break;
//            case  R.raw.sound_success3:
//                playMySound(id_sound_success);
//                break;

        }

        //180910 震动
        shake();

    }

    //180914
    public void soundSuccessNow(){
        playMySound(id_sound_success);
    }


    private static void playMySound(int id ){
        allSoundPool.play(id, 1f, 1f, 0, 0, 1.0f);

    }





//    public void soundErrorNow(final int rawId){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//                final int id = soundPool.load(MyBaseService.this, rawId, 1);
//                playMySound(soundPool,id);
//
//            }
//        }).start();
//    }
//
//
//    private static void playMySound(final SoundPool soundPool, final int id ){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                soundPool.play(id, 1f, 1f, 0, 0, 1.0f);
//                Vibrator vibrator = (Vibrator) RFIDApplication.instance.getSystemService(Context.VIBRATOR_SERVICE);
//                vibrator.vibrate(1000);
//            }
//        },600);
//    }


    public void logScanEpcIDString(String string,int id){
        if(RFIDApplication.getIsCanRecordEpcID()){
            LogEpcID.put(string ,id);
        }
    }


    public void logUnRightEpcIDString(String string,int id){
        LogEpcID2.put(string ,id);
    }


    public void release() {}





}
