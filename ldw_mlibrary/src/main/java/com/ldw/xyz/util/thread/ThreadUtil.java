package com.ldw.xyz.util.thread;

/**
 * Created by LDW10000000 on 28/08/2017.
 */

public class ThreadUtil {

    public static <T> void myWait(T lock) throws IllegalMonitorStateException ,InterruptedException {
        synchronized (lock) {
                lock.wait();
        }
    }

    public static <T> void myNotify(T lock) throws InterruptedException {
        synchronized (lock) {
            lock.notify();
        }
    }

    public static <T> void myNotifyAll(T lock) throws InterruptedException {
        synchronized (lock) {
            lock.notifyAll();
        }
    }


}
