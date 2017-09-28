package com.ldw.xyz.util.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by LDW10000000 on 31/08/2017.
 */

public class IOUtil {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}