package com.ldw.xyz.listener;

import java.util.List;

/**
 * Created by LDW10000000 on 29/12/2016.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);



}
