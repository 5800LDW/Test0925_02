package kld.com.rfid.ldw.demand2.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

import com.ldw.xyz.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;


/**
 * Created by liudongwen on 2018/9/12.
 */

public class SoundUtil {

    private static final String TAG = "SoundUtil";

    private static PriorityBlockingQueue<PriorityEntity> queue ;
    private static ExecutorService executor ;
    private static ExecutorService singleThreadexecutor ;


    public static void initExecutorSoundUtil() {
        queue = new PriorityBlockingQueue<>();
        executor = Executors.newSingleThreadExecutor();
        singleThreadexecutor = Executors.newSingleThreadExecutor();
        initAllSoundPool();
    }


    public static void put(final int soundID, final int lineUp) {
        executor.execute(new Runnable() {
            public void run() {
                PriorityEntity priorityEntity = new PriorityEntity();
                priorityEntity.setSoundID(soundID);
                priorityEntity.setLineUp(lineUp);
                queue.put(priorityEntity);
            }
        });


    }

    public static void playSound() {
        singleThreadexecutor.execute(new Runnable() {
            public void run() {
                try {
                    if (queue.size() >0) {

                        int soundID = queue.take().getSoundID();
                        soundErrorNow(soundID);

                        //暂停

                        int sleepTime =2000;
                        if(soundID == R.raw.sound_cuanhuo3 ||
                                soundID == R.raw.sound_louhuo3 ||
                                soundID == R.raw.sound_quxiao3
                                ){
                            sleepTime = 1500;
                        }

                        Thread.sleep(sleepTime);




                        playSound();

                    }

                } catch (Exception e) {
                    LogUtil.e("LogEpcID", "*** Exception *** -----------");
                    LogUtil.e("LogEpcID", "e =" + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }



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

    static void shake(){
        vibrator.vibrate(3000);
    }

    public static void soundErrorNow(final int rawId){

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
//        shake();

    }

    private static void playMySound(int id ){
        allSoundPool.play(id, 1f, 1f, 0, 0, 1.0f);

    }
}
