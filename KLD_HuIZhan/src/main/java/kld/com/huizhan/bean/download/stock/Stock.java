package kld.com.huizhan.bean.download.stock;

import com.google.gson.annotations.SerializedName;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class Stock extends BaseBean {

    @SerializedName(value = "storageID", alternate = {"StockID"}) //"StockID": "1",
    private String storageID;

    @SerializedName(value = "storageNO", alternate = {"StockNo"}) //"StockNo": "0001",
    private String storageNO;

    @SerializedName(value = "storageName", alternate = {"StockName"}) //"StockName": "资产仓",
    private String storageName;

    @SerializedName(value = "storageManager", alternate = {"StockManager"}) //"StockManager": "张三",
    private String storageManager;

    @SerializedName(value = "storageAddress", alternate = {"StockAddr"}) //StockAddr": "开发区仓库"
    private String storageAddress;

    public Stock() {
    }

    public String getStorageID() {
        return storageID;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }

    public String getStorageNO() {
        return storageNO;
    }

    public void setStorageNO(String storageNO) {
        this.storageNO = storageNO;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(String storageManager) {
        this.storageManager = storageManager;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "storageID='" + storageID + '\'' +
                ", storageNO='" + storageNO + '\'' +
                ", storageName='" + storageName + '\'' +
                ", storageManager='" + storageManager + '\'' +
                ", storageAddress='" + storageAddress + '\'' +
                '}';
    }
}
