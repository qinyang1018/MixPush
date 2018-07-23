package com.mixpush.client.huawei;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.mixpush.client.core.Contans;
import com.mixpush.client.core.MixMessageProvider;
import com.mixpush.client.core.MixPushManager;


/**
 * Created by Wiki on 2017/6/1.
 */

public class HuaWeiPushManager implements MixPushManager, HuaweiApiClient.ConnectionCallbacks, HuaweiApiClient.OnConnectionFailedListener {
    public static final String TAG = "HuaWeiPushManager";
    public static final String NAME = Contans.NAME_HUWEI;
    public static MixMessageProvider sMixMessageProvider;

    //华为移动服务Client
    private HuaweiApiClient client;

    public HuaWeiPushManager() {
    }

    @Override
    public void registerPush(Context context) {
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (context instanceof Activity) {
            client.connect((Activity) context);
        }
    }


    @Override
    public void unRegisterPush(Context context) {

    }

    @Override
    public void setAlias(Context context, String alias) {
    }

    @Override
    public void unsetAlias(Context context, String alias) {
    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }


    @Override
    public String getName() {
        return Contans.NAME_HUWEI;
    }

    @Override
    public void setMessageProvider(MixMessageProvider provider) {
        sMixMessageProvider = provider;
    }

    @Override
    public void disable(Context context) {
        unRegisterPush(context);
    }

    @Override
    public void onConnected() {
        //华为移动服务client连接成功，在这边处理业务自己的事件
        Log.i(TAG, "HuaweiApiClient 连接成功");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "HuaweiApiClient 连接断开");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "HuaweiApiClient连接失败，错误码：" + result.getErrorCode());

    }
}
