package com.boc.jobleader.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boc.jobleader.R;
import com.boc.jobleader.action.HandlerAction;
import com.boc.jobleader.base.BaseDialog;
import com.boc.jobleader.custom.WaitDialog;
import com.boc.jobleader.module.login.LoginActivity;
import com.boc.jobleader.wechat.Constants;
import com.boc.jobleader.wechat.NetworkUtil;
import com.hjq.toast.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, HandlerAction {

    private static String TAG = "MicroMsg.WXEntryActivity";

    private MyHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new MyHandler(this);

        try {
            Intent intent = getIntent();
            Constants.wx_api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        Constants.wx_api.handleIntent(intent, this);
    }


    private static class MyHandler extends Handler {
        private final WeakReference<Context> wxEntryActivityWeakReference;

        public MyHandler(Context context){
            wxEntryActivityWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag) {
                case NetworkUtil.GET_TOKEN: {
                    Bundle data = msg.getData();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data.getString("result"));
                        String openId, accessToken, refreshToken, scope;
                        openId = json.getString("openid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                        scope = json.getString("scope");

                        Constants.openId = openId;
                        Intent intent = new Intent(wxEntryActivityWeakReference.get(), LoginActivity.class);
                        wxEntryActivityWeakReference.get().startActivity(intent);
                    } catch (JSONException e) {

                    }
                }
            }
        }
    }


    @Override
    public void onReq(BaseReq req) {
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

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp)resp;
            final String code = authResp.code;
            NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constants.APP_ID,
                    Constants.APP_SECRECT, code), NetworkUtil.GET_TOKEN);
        }
        finish();
    }
}