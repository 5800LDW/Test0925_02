package com.ldw.xyz.util.webService;

import android.os.Handler;
import android.os.Message;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 访问WebService的工具类,
 */
public class WebServiceUtils2 {

    // 命名空间
    private static String NAMESPACE = "http://tempuri.org/";
    // 含有3个线程的线程池
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static String getWebServiceUrl() {
        return WEB_SERVICE_URL;
    }

    private static String WEB_SERVICE_URL = "http://www.connectek.com.cn:8089/WebSrv.asmx";

    public static void setWebServiceUrl(String url) {
        WEB_SERVICE_URL = url;
        LogUtil.i("TAG", "WEB_SERVICE_URL = " + WEB_SERVICE_URL);
    }


    /**
     * @param methodName         WebService的调用方法名
     * @param properties         WebService的参数
     * @param webServiceCallBack 回调接口
     */
    public static void CallWebService(final String methodName, HashMap<String, String> properties,
                                      final WebServiceCallBack webServiceCallBack) {
        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(getWebServiceUrl());
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = true;
        httpTransportSE.debug = true;

        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.obj instanceof Exception) {
                    webServiceCallBack.failedCallBack((Exception) msg.obj);
                } else {
                    // 将返回值回调到callBack的参数中
                    webServiceCallBack.succeedCallBack((SoapObject) msg.obj);
                }
            }
        };

        // 开启线程去访问WebService
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                SoapObject resultSoapObject = null;

                Exception myException = null;

                try {
                    httpTransportSE.call(NAMESPACE + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                    }
                } catch (HttpResponseException e) {
                    ExceptionUtil.handleException(e);
                }
                // the network is unconnect
                catch (java.net.UnknownHostException e) {
                    myException = e;
                    ExceptionUtil.handleException(e);
                } catch (XmlPullParserException e) {
                    myException = e;
                    ExceptionUtil.handleException(e);
                } catch (IOException e) {
                    myException = e;
                    ExceptionUtil.handleException(e);
                    LogUtil.i("TAG", " =" + e.toString());
                } catch (Exception e) {
                    myException = e;
                    ExceptionUtil.handleException(e);
                } finally {
                    if (myException == null) {
                        // 将获取的消息利用Handler发送到主线程
                        mHandler.sendMessage(mHandler.obtainMessage(0, resultSoapObject));
                    } else {
                        mHandler.sendMessage(mHandler.obtainMessage(0, myException));
                    }

                }
            }
        });
    }

    public interface WebServiceCallBack {
        void succeedCallBack(SoapObject result);

        void failedCallBack(Exception e);
    }

}
