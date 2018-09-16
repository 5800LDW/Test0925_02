package kld.com.huizhan.util.db;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.Collection;
import java.util.List;

import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.department.Department;
import kld.com.huizhan.bean.download.giveback.GiveBackAssets;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.bean.download.stock.Stock;
import kld.com.huizhan.bean.download.supplier.Supplier;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.local.AssetsCheck;
import kld.com.huizhan.bean.upload.check.local.UploadCheck;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBack;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.bean.upload.repair.UpLoadRepair;

/**
 * Created by LDW10000000 on 23/11/2017.
 */

public class DataFunctionUtil {


    private static String TAG = "DataFunctionUtil";


    public static int count(Class<?> modelClass) {
        return DataSupport.count(modelClass);
    }

    public static int countAssetCheckIsCheck() {
        return DataSupport.where("checkIsSucceed = ? ","已盘").count(AssetsCheck.class);
    }



    public static <T extends DataSupport> void saveAll(Collection<T> collection) throws DataSupportException {
//        try{
            DataSupport.saveAll(collection);
//        }catch (Exception e){
//            ExceptionUtil.handleException(e);
//            LogUtil.e(TAG, "保存数据发生错误 " +e.toString());
//        }
    }

    /**
     * @param modelClass
     * @return The number of rows affected.
     */
    public static int deleteAll(Class<?> modelClass){
        return DataSupport.deleteAll(modelClass);
    }


    public static int findUser(String name , String pwd){
        return  DataSupport.where("lName = ? and password = ?", name,pwd).count(User.class);
    }




    public static User findUser(String name ){
        return  DataSupport.where("lName = ? ", name).findFirst(User.class);
    }


    //20180730
    public static int findUserCount(String name ){
        return  DataSupport.where("lName = ? ", name).count(User.class);
    }

    //20180730 查找默认是自动填充的账号
    public static List findUserFillIsTrue(){
        return  DataSupport.where("fill = ?","1").find(User.class,true);
    }

    //20180730
    public static User findUserAndFillIsTrue(String name ){
        return  DataSupport.where("lName = ? and fill = ?", name,"1").findFirst(User.class);
    }



    public static int findIsUnUploadIsSucceed(){
        return  DataSupport.where("upLoadIsSucceed = ? ", "未上传").count(AssetsCheck.class);
    }




    public static User findFirstUserByName(String lName){
       return  DataSupport.where("lName = ? ",lName).findFirst(User.class);
    }


    public static Assets findFirstAssetsByCode(String code){

        return  DataSupport.where("code = ? ",code).findFirst(Assets.class);
    }

    public static Assets findAssetsByAssetID(String assetID){

        return  DataSupport.where("assetID = ? ",assetID).findFirst(Assets.class);
    }


    public static List<Upload> findAllUpload(){
        List<Upload> lists = DataSupport.findAll(Upload.class);
        return  lists;
    }
    public static int findUploadByBillNo(String BillNo){
        return DataSupport.where("BillNo = ?", BillNo).count(Upload.class);
    }





    public static int deleteUploadByBillNo(String BillNo){
        return  DataSupport.deleteAll(Upload.class, "BillNo = ? ", BillNo);
    }






    public static List<Assets> findAllAssets(){
        List<Assets> lists = DataSupport.findAll(Assets.class);
        return  lists;
    }


    public static List<Stock> findAllStock(){
        List<Stock> lists = DataSupport.findAll(Stock.class);
        return  lists;
    }

    public static List<Department> findAllDepartment(){
        List<Department> lists = DataSupport.findAll(Department.class);
        return  lists;
    }

    public static List<OutPutUpLoadList> findAllOutPutLoadList(){
        List<OutPutUpLoadList> lists = DataSupport.findAll(OutPutUpLoadList.class,true);
        return  lists;
    }


    public static int deleteOutPutLoadListByCode(String BillNo){
        return  DataSupport.deleteAll(OutPutUpLoadList.class, "BillNo = ? ", BillNo);
    }
    public static int deleteOutPutLoadByBillNo(String BillNo){
        return  DataSupport.deleteAll(OutPutUpLoad.class, "BillNo = ? ", BillNo);
    }



    public static int findOutPutLoadListByBillNo(String BillNo){
        return DataSupport.where("BillNo = ?", BillNo).count(OutPutUpLoadList.class);
    }





    public static List<GiveBackBill> getAllGiveBackBill(){
        List<GiveBackBill> lists = DataSupport.findAll(GiveBackBill.class,true);
        return  lists;
    }

    public static List<GiveBackBill> findGiveBackBillByNo(String OutDepotNo){
        List<GiveBackBill> lists =  DataSupport.where("OutDepotNo = ? ",OutDepotNo).find(GiveBackBill.class,true);
        return  lists;
    }

    public static int findUploadGiveBackCountByID(String OutDepotID){
        return DataSupport.where("OutDepotID = ?", OutDepotID).count(UploadGiveBack.class);
    }

    public static int deleteUploadGiveBackCountByID(String OutDepotID){

        return  DataSupport.deleteAll(UploadGiveBack.class, "OutDepotID = ? ", OutDepotID);
    }

    //20180106
    public static void updateGiveBackAssets(String AssetsID,String ReturnCheck ,String ReturnNum){
        ContentValues values = new ContentValues();
        values.put("ReturnCheck", ReturnCheck);
        values.put("ReturnNum", ReturnNum);
        DataSupport.updateAll(GiveBackAssets.class, values, "AssetsID = ? ",AssetsID);
    }






    public static List<UploadGiveBack> findAllUploadGiveBack(){
        List<UploadGiveBack> lists =  DataSupport.findAll(UploadGiveBack.class,true);
        return  lists;
    }

    public static List<Assets> findAssetsNameAndModelById(String assetID){
        return  DataSupport.select("name", "model").where("assetID = ?", assetID).find(Assets.class);
    }

    public static List<UpLoadRepair> findAllUpLoadRepair(){
        return  DataSupport.findAll(UpLoadRepair.class);
    }

    public static int deleteUpLoadRepairByCode(String AssetCode){
        return  DataSupport.deleteAll(UpLoadRepair.class, "AssetCode = ? ", AssetCode);
    }

    public static List<UploadCheck> findUploadCheckByAssetID(String AssetsID ){

        return  DataSupport.where("AssetsID = ? ",AssetsID).find(UploadCheck.class);

    }

    public static int updateUploadCheckByAssetID(String num , String AssetsID){
        ContentValues values = new ContentValues();
        values.put("Num", num);
       return DataSupport.updateAll(UploadCheck.class, values, "AssetsID = ? ", AssetsID);
    }

    public static List<Supplier> getAllSupplier(){
        List<Supplier> lists = DataSupport.findAll(Supplier.class);
        return  lists;
    }

    public static List<UploadCheck> getAllUploadCheck(){
        List<UploadCheck> lists = DataSupport.findAll(UploadCheck.class);
        return  lists;
    }


//    public static List<UploadCheck> getAllUploadCheckIsUnUpload(){
//        List<UploadCheck> lists = DataSupport.where("upLoadIsSucceed = ?", "未提交").find(UploadCheck.class);
//        return  lists;
//    }


    public static List<AssetsCheck> getAssetsCheckList(){
        List<AssetsCheck> lists = DataSupport.where("checkIsSucceed = ? ","已盘").find(AssetsCheck.class);
        return  lists;
    }

    public static List<AssetsCheck> getAssetsUnCheckList(){
        List<AssetsCheck> lists = DataSupport.where("checkIsSucceed = ? ","未盘").find(AssetsCheck.class);
        return  lists;
    }


    public static  int updateAssetCheckUnload2Uploaded(){
        ContentValues values = new ContentValues();
        values.put("upLoadIsSucceed", "已上传");
        return  DataSupport.updateAll(AssetsCheck.class, values, "checkIsSucceed = ? and upLoadIsSucceed = ?","已盘","未上传");

    }


    //更新是不是自动填充
    public static int updateUserFill(String lName ,String fill){

        ContentValues values = new ContentValues();
        values.put("fill", fill);
        return DataSupport.updateAll(User.class, values, "lName = ? ", lName);

    }





}
