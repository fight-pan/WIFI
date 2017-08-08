package com.quark.wificontrol.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.quark.wificontrol.R;
import com.quark.wificontrol.mainview.FragmentOne;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, FragmentOne.appid, false);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        System.out.println("===========234===========");
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "22";

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "成功";
                Intent intent = new Intent("payReceiver");
                intent.putExtra("option", "success");
                intent.putExtra("result", result);
                sendBroadcast(intent);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
                Intent intent2 = new Intent("payReceiver");
                intent2.putExtra("option", "cancel");
                intent2.putExtra("result", result);
                sendBroadcast(intent2);
                break;

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "错误"+resp.errCode;
                Intent intent3 = new Intent("payReceiver");
                intent3.putExtra("option", "false");
                intent3.putExtra("result", result);
                sendBroadcast(intent3);
                break;
            default:
                result = "其他错误"+resp.errCode;
                Intent intent4 = new Intent("payReceiver");
                intent4.putExtra("option", "false");
                intent4.putExtra("result", result);
                sendBroadcast(intent4);
                break;
        }

//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        Log.e("payresult",result);
        finish();
    }

}