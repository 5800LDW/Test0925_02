package com.ldw.xyz.request;

/**
 * Created by LDW10000000 on 22/12/2016.
 */

/**
 * Created by chenjianwei on 2016/12/11.
 * 请求返回成功/失败，成功时，把服务器返回的结果回调出去，失败时回调异常信息
 * onSuccess中的参数类型，当然也可以为JSONObject，这里只是举个栗子，可按照实际需求变通
 */
public interface IRequestCallback {
    void onBefore(int id);
    void onAfter(int id);
    void onSuccess(String response);
    void onFailure(Throwable throwable);
}