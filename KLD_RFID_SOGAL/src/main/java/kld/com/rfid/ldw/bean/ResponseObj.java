package kld.com.rfid.ldw.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudongwen on 2018/8/27.
 */

public class ResponseObj {


//    private String package ;
//    private String package ;

    @SerializedName(value = "myPackage", alternate = {"package"})//"AssetsID": "1",
    private String myPackage;

    @SerializedName(value = "myCollude", alternate = {"collude"})//"AssetsID": "1",
    private String myCollude;

    @SerializedName(value = "errormsg", alternate = {"errormessage"})//"AssetsID": "1",
    private String errormsg;

    public String getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(String myPackage) {
        this.myPackage = myPackage;
    }

    public String getMyCollude() {
        return myCollude;
    }

    public void setMyCollude(String myCollude) {
        this.myCollude = myCollude;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    @Override
    public String toString() {
        return "ResponseObj{" +
                "myPackage='" + myPackage + '\'' +
                ", myCollude='" + myCollude + '\'' +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }


    //    private String number ;
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//
//    @Override
//    public String toString() {
//        return "ResponseObj{" +
//                "number='" + number + '\'' +
//                '}';
//    }
}
