package com.mixpush.client.huawei;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;
import com.mixpush.client.core.MixPushMessage;


public class HuaWeiPushMessageReceiver extends PushReceiver {
    private static final String TAG = "HuaWeiPushMessageReceiver";

    @SuppressLint("LongLogTag")
    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        Log.i(TAG, "belongId为:" + belongId);
        Log.i(TAG, "Token为:" + token);

    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        if (HuaWeiPushManager.sMixMessageProvider == null) {
            return false;
        }
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msg, "UTF-8");
            Log.i(TAG, "收到PUSH透传消息,消息内容为:" + content);
            MixPushMessage mixPushMessage = new MixPushMessage();
            mixPushMessage.setPlatform(HuaWeiPushManager.NAME);
            mixPushMessage.setContent(content);
            HuaWeiPushManager.sMixMessageProvider.onReceivePassThroughMessage(context, mixPushMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onEvent(Context context, Event event, Bundle extras) {

//        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
//            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
//            Log.i(TAG, "收到通知栏消息点击事件,notifyId:" + notifyId);
//            Toast.makeText(context, "通知栏点击事件", Toast.LENGTH_LONG).show();
//            if (0 != notifyId) {
//                NotificationManager manager = (NotificationManager) context
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.cancel(notifyId);
//            }
//        }
//
//        String message = extras.getString(BOUND_KEY.pushMsgKey);
        super.onEvent(context, event, extras);
        MixPushMessage mixPushMessage = new MixPushMessage();
        mixPushMessage.setPlatform(HuaWeiPushManager.NAME);
        HuaWeiPushManager.sMixMessageProvider.onNotificationMessageClicked(context, mixPushMessage);

    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        Log.i("TAG", "Push连接状态为:" + pushState);

    }
}