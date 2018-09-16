package kld.com.rfid.ldw.demand2.logepc;

import com.ldw.xyz.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by liudongwen on 2018/9/10.
 */

public class LogEpcID {

//    private static PriorityBlockingQueue<PriorityEntity> queue = new PriorityBlockingQueue<>();
//    private static ExecutorService executor = Executors.newCachedThreadPool();
//    private static ExecutorService singleThreadexecutor = Executors.newSingleThreadExecutor();

    private static PriorityBlockingQueue<PriorityEntity> queue ;
    private static ExecutorService executor ;
    private static ExecutorService singleThreadexecutor ;


    public static void initExecutor() {
        queue = new PriorityBlockingQueue<>();
        executor = Executors.newCachedThreadPool();
        singleThreadexecutor = Executors.newSingleThreadExecutor();
    }


    public static void put(final String epc, final int lineUp) {
        executor.execute(new Runnable() {
            public void run() {
                PriorityEntity priorityEntity = new PriorityEntity();
                priorityEntity.setEpcID(epc);
                priorityEntity.setLineUp(lineUp);
                queue.put(priorityEntity);
            }
        });

        take();
    }

    public static void take() {
        singleThreadexecutor.execute(new Runnable() {
            public void run() {
                try {
                    if (queue.size() != 0) {

                        List<PriorityEntity> list = new ArrayList<>();
                        int size = queue.drainTo(list);
                        LogUtil.e("LogEpcID", "*** size *** -----------");
                        LogUtil.e("LogEpcID", "size = " + size);


                        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
                        String date = sDateFormat.format(new Date());


                        LogUtil.e("LogEpcID", "*** list *** -----------");
                        LogUtil.e("LogEpcID", "list = " + list.toString());

                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < size; i++) {
                            sb.append(list.get(i).getEpcID());

                            sb.append("\t\t" + date + "\n");
                        }

                        RecordEpcID2SDUtil.record(null, sb);

                    }


                } catch (Exception e) {
                    LogUtil.e("LogEpcID", "*** Exception *** -----------");
                    LogUtil.e("LogEpcID", "e =" + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

}









