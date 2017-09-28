package com.ldw.xyz.request;

/**
 * Created by LDW10000000 on 22/12/2016.
 */
/*
 * 该类的作用是用于返回一个IRequestManager对象，这个IRequestManager的实现类
 * 可以是使用Volley实现的http请求对象，也可以是OkHttp实现的http请求对象
 * Activity/Fragment/Presenter中，只要调用getRequestManager()方法就能得到
 * http请求的操作接口，而不用关心具体是使用什么实现的。
 */
/*
 * 此接口提供的就是http请求通用的方法，该接口可以用Volley来实现，也能用OkHttp等其它方式来实现
 * 接口说明：
 * 	get方法参数包含一个url，以及返回数据的接口
 * 	post/put/delete方法还需要提供一个请求体参数
 */
public interface IRequestManager {

    static IRequestManager getInstance() {
//        return new b();
        return  null;
    }

    void doGet(String url, IRequestCallback requestCallback);
    void doPost(String url, String requestBodyJson, IRequestCallback requestCallback);
    void doPut(String url, String requestBodyJson, IRequestCallback requestCallback);
    void doDelete(String url, String requestBodyJson, IRequestCallback requestCallback);
}