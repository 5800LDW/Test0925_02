package kld.com.huizhan.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ldw.xyz.util.ExceptionUtil;

import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.assets.AssetsList;
import kld.com.huizhan.bean.download.department.DepartmentList;
import kld.com.huizhan.bean.download.giveback.GiveBackBillList;
import kld.com.huizhan.bean.download.stock.StockList;
import kld.com.huizhan.bean.download.supplier.SupplierList;
import kld.com.huizhan.bean.download.user.UserList;
import kld.com.huizhan.bean.local.AssetsCheckList;
import kld.com.huizhan.bean.upload.check.UploadIsCheckList;
import kld.com.huizhan.bean.upload.repair.ULR;

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

    private GsonUtil() {}

    public static BaseBean toBaseBean(String response) {
        BaseBean bean = null;
        try {
            java.lang.reflect.Type type = new TypeToken<BaseBean>() {}.getType();
             bean = gson.fromJson(response, type);
        }catch (JsonSyntaxException e){
            ExceptionUtil.handleException(e);
        }
        return bean;
    }

    public static UserList toUserList(String response){
        java.lang.reflect.Type type = new TypeToken<UserList>() {}.getType();
        UserList bean = gson.fromJson(response, type);
        return bean;
    }

    public static StockList toStockList(String response){
        java.lang.reflect.Type type = new TypeToken<StockList>() {}.getType();
        StockList bean = gson.fromJson(response, type);
        return bean;
    }

    public static DepartmentList toDepartmentList(String response){
        java.lang.reflect.Type type = new TypeToken<DepartmentList>() {}.getType();
        DepartmentList bean = gson.fromJson(response, type);
        return bean;
    }

    public static AssetsList toAssetsList(String response){
        java.lang.reflect.Type type = new TypeToken<AssetsList>() {}.getType();
        AssetsList bean = gson.fromJson(response, type);
        return bean;
    }


    public static GiveBackBillList toGiveBackBillList(String response){
        java.lang.reflect.Type type = new TypeToken<GiveBackBillList>() {}.getType();
        GiveBackBillList bean = gson.fromJson(response, type);
        return bean;
    }

    public static ULR toULR(String response){
        java.lang.reflect.Type type = new TypeToken<ULR>() {}.getType();
        ULR bean = gson.fromJson(response, type);
        return bean;
    }

    public static SupplierList toSupplierList(String response){
        java.lang.reflect.Type type = new TypeToken<SupplierList>() {}.getType();
        SupplierList bean = gson.fromJson(response, type);
        return bean;
    }

    public static AssetsCheckList toAssetsCheckList(String response){
        java.lang.reflect.Type type = new TypeToken<AssetsCheckList>() {}.getType();
        AssetsCheckList bean = gson.fromJson(response, type);
        return bean;
    }

    public static UploadIsCheckList toUpLoadIsCheckList(String response){
        java.lang.reflect.Type type = new TypeToken<UploadIsCheckList>() {}.getType();
        UploadIsCheckList bean = gson.fromJson(response, type);
        return bean;
    }



    public static String toJson(Object object){
        return  gson.toJson(object);

    }








}
