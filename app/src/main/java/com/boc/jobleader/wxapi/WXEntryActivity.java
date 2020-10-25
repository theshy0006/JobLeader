package com.boc.jobleader.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.boc.jobleader.wechat.Constants;
import com.boc.jobleader.wechat.NetworkUtil;
import com.hjq.toast.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Constants.wx_api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i("WXTest", "onResp OK");
                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i("WXTest", "onResp ERR_USER_CANCEL ");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i("WXTest", "onResp ERR_AUTH_DENIED");
                //发送被拒绝
                break;
            default:
                Log.i("WXTest", "onResp default errCode " + resp.errCode);
                //发送返回
                break;
        }
        finish();
    }

}