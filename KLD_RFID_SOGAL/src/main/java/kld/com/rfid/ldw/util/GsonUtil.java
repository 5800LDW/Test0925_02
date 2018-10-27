package kld.com.rfid.ldw.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ldw.xyz.util.exception.ExceptionUtil;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.bean.ResponseObj;
import kld.com.rfid.ldw.bean.channel.ChannelInfo;
import kld.com.rfid.ldw.bean.orginfo.DownloadOrganization;
import kld.com.rfid.ldw.bean.update.UpdateBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    //
    public static DownloadOrganization toDownloadOrganization(String response) {
        DownloadOrganization bean = null;
        try {
            java.lang.reflect.Type type = new TypeToken<DownloadOrganization>() {
            }.getType();
            bean = gson.fromJson(response, type);
        } catch (JsonSyntaxException e) {
            ExceptionUtil.handleException(e);
        }
        return bean;
    }

    public static ChannelInfo toChannelInfo(String response) {
        ChannelInfo bean = null;
        try {
            java.lang.reflect.Type type = new TypeToken<ChannelInfo>() {
            }.getType();
            bean = gson.fromJson(response, type);
        } catch (JsonSyntaxException e) {
            ExceptionUtil.handleException(e);
        }
        return bean;
    }


    public static ResponseObj toResponseObj(String response) {
        ResponseObj bean = null;
        try {
            java.lang.reflect.Type type = new TypeToken<ResponseObj>() {
            }.getType();
            bean = gson.fromJson(response, type);
        } catch (JsonSyntaxException e) {
            ExceptionUtil.handleException(e);
        }
        return bean;
    }


    public static UpdateBean toUpdateBean(String response) {
        UpdateBean bean = null;
        try {
            java.lang.reflect.Type type = new TypeToken<UpdateBean>() {
            }.getType();
            bean = gson.fromJson(response, type);
        } catch (JsonSyntaxException e) {
            if (Const.IsCanTest == true) {

            } else {
                ExceptionUtil.handleException(e);
            }

        }
        return bean;
    }


    public static String toJson(Object object) {
        return gson.toJson(object);

    }


}
