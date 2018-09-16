package kld.com.rfid.ldw.demand2.way3;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.ldw.xyz.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.RFIDApplication;

/**
 * Created by liudongwen on 2018/9/2.
 */

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";

    Map<Integer, Boolean> handledMap = new HashMap<>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo nodeInfo = event.getSource();



        LogUtil.e(TAG, "***  *** ------------------");
        if(nodeInfo!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                LogUtil.e(TAG, "nodeInfo.getViewIdResourceName() = " + nodeInfo.getViewIdResourceName());
            }
            LogUtil.e(TAG, "nodeInfo.toString() = " + nodeInfo);
        }
        LogUtil.e(TAG, "***  *** ------------------ ***  ***");




        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                    eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handledMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handledMap.put(event.getWindowId(), true);
                    }
                }
            }
        }
    }

    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            //nodeInfo.getViewIdResourceName();
            LogUtil.e(TAG, "***  *** ------------------");
            LogUtil.e(TAG, "nodeInfo.getClassName() " + nodeInfo.getClassName());

            if (nodeInfo.getText() != null) {
                LogUtil.e(TAG, "content is " + nodeInfo.getText());

            }
            LogUtil.e(TAG, "***  *** ------------------ ***  ***");

            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeContent = nodeInfo.getText().toString();
                LogUtil.e(TAG, "content is " + nodeContent);
                if ("安装".equals(nodeContent)
                        || "打开".equals(nodeContent)
                        || "确定".equals(nodeContent)
                        ) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    if("打开".equals(nodeContent)){
                        Toast.makeText(this, "更新完成!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }

            } else if ("android.widget.CheckedTextView".equals(nodeInfo.getClassName())) {
                if ("设备内存".equals(nodeInfo.getText())) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            } else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;

    }


    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        LogUtil.e("TAG1", "getScanCode=========>" + event.getScanCode());
        LogUtil.e("TAG1", "getKeyCode=========>" + event.getKeyCode());

//        int key = event.getKeyCode();
        if (event.getScanCode() == Const.KEY_SCAN_BUTTON && event.getAction() == KeyEvent.ACTION_UP) {

//            Intent mIntent = new Intent();
//            mIntent.setAction("$_ActionUp");
//            sendBroadcast(mIntent);
            if (RFIDApplication.instance != null && RFIDApplication.instance.floatService != null) {
                RFIDApplication.instance.floatService.scanBiz();
            }


            LogUtil.e("TAG1", "getAction=========>" + event.getAction());
            return false;
        } else {
            return super.onKeyEvent(event);
        }


    }
}


























