package kld.com.huizhan.util;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class Const {


    public static final int KEY_SCAN_CODE = 139;

    public static final int KEY_SCAN_CODE_2 = 139;
    public static final int KEY_SCAN_CODE_3 = 126;
    public static final int KEY_SCAN_CODE_4 = 0;


    public static final int KEY_CODE_101 = 101;
    public static final String KEY_VALUE_01 = "$_value01";
    public static final String KEY_VALUE_02 = "$_value02";


    public static final String KEY_URL_SERVER = "$_url";

    private static final String WEBAPI_DOWNLOAD = "webapi/DownLoad";

    public static final String STATE_EXIST = "在库";
    public static final String STATE_APPLYING = "领用申请";
    public static final String STATE_OUTPUTED = "已出库";
    public static final String STATE_LOSS = "已损耗";

    public static final String TYPE_CODE_COMMON = "共用条码";
    public static final String TYPE_CODE_OWN = "独立条码";
    public static final String TYPE_CODE_INDEX = "下标条码";



    /**物资信息*/
    public static final String URL_DOWNLOAD_ASSETS = WEBAPI_DOWNLOAD + "/DownLoadAssets";
    /**用户*/
    public static final String URL_DOWNLOAD_USERS = WEBAPI_DOWNLOAD + "/DownLoadUsers";
    /**仓库信息*/
    public static final String URL_DOWNLOAD_STOCKS = WEBAPI_DOWNLOAD + "/DownLoadStocks";
    /**部门信息*/
    public static final String URL_DOWNLOAD_DEPARTMENT = WEBAPI_DOWNLOAD + "/DownLoadDepartment";
    /**供应商信息**/
    public static final String URL_DOWNLOAD_SUPPLIER = WEBAPI_DOWNLOAD + "/DownLoadSuplier";// //http://www.connectek.com.cn:9514/webapi/DownLoad/DownLoadSuplier


    private static final String WEBAPI_UPLOAD_STOCK = "webapi/InStock";
    public static final String URL_UPLOAD_PUTIN =WEBAPI_UPLOAD_STOCK + "/UploadInstock";



    private static final String WEBAPI_UPLOAD_DEPOT = "webapi/OutDepot";
    public static final String URL_UPLOAD_OUTPUT = WEBAPI_UPLOAD_DEPOT + "/UploadOutstock";
    //OutDepot/DownLoadOutDepot?AssetStockID=1
    public static final String URL_DOWNLOAD_GIVBACK = WEBAPI_UPLOAD_DEPOT + "/DownLoadOutDepot?AssetStockID=";

    //http://www.connectek.com.cn:9514/WebAPI/OutDepot/UploadReturn
    public static final String URL_UPLOAD_GIVBACK = WEBAPI_UPLOAD_DEPOT + "/UploadReturn";


    //http://www.connectek.com.cn:9514/webapi/RepairService/UploadRepairService
    private static final String WEBAPI_REPAIR = "webapi/RepairService";
    public static final String URL_UPLOAD_REPAIR = WEBAPI_REPAIR + "/UploadRepairService";


    public static final String URL_UPLOAD_ISCHECK = "webapi/Invent/UploadInventResult";





    public static boolean isCanMove = false;

}




























