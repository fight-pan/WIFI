package com.quark.wificontrol.alipay;

/**
 * 微信支付预订单
 * @author Administrator
 *
 */
public class ComfiyPayResponseWeixin  {

    public static String package_weixin = "Sign=WXPay";           //": "Sign=WXPay", 扩展字段
//	public static String api_key;        //": "uelives123uelives123uelives12345",
    public static String appid;              //": "wx4b0ab3ff218ae682",  应用ID
    public static String partnerid = "1412370802";      //": "1281968701",  商户号
    String sign;                //": "3974d40f0f43e1254e78eea6a15a707f50c7a5de",
    String prepayid;           //": "wx201511091057302ba7e2c1180732381372",
	String noncestr;        //": "8d6a06b2f1208b59454a9a749928b0c0",
	String timestamp;        //": "1447037807",

    public static String getPackage_weixin() {
        return package_weixin;
    }

    public static void setPackage_weixin(String package_weixin) {
        ComfiyPayResponseWeixin.package_weixin = package_weixin;
    }

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        ComfiyPayResponseWeixin.appid = appid;
    }

    public static String getPartnerid() {
        return partnerid;
    }

    public static void setPartnerid(String partnerid) {
        ComfiyPayResponseWeixin.partnerid = partnerid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
