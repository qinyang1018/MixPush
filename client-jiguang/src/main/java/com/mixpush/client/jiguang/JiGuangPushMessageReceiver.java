package com.mixpush.client.jiguang;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mixpush.client.core.MixPushMessage;

import cn.jpush.android.api.JPushInterface;


public class JiGuangPushMessageReceiver extends BroadcastReceiver {
    private static final String TAG = "JiGuangPushMessageReceiver";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "ddddd");
        if (JiGuangPushManager.sMixMessageProvider == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        MixPushMessage mixPushMessage = new MixPushMessage();
        mixPushMessage.setPlatform(JiGuangPushManager.NAME);
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            mixPushMessage.setContent(bundle.getString(JPushInterface.EXTRA_MESSAGE));
            mixPushMessage.setTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
//          mixPushMessage.setDescription(message.getDescription());
//          mixPushMessage.setAlias(message.getAlias());
//          mixPushMessage.setPassThrough(message.getPassThrough());
            JiGuangPushManager.sMixMessageProvider.onReceivePassThroughMessage(context, mixPushMessage);


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            mixPushMessage.setContent(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            mixPushMessage.setTitle(bundle.getString(JPushInterface.EXTRA_ALERT));
            JiGuangPushManager.sMixMessageProvider.onNotificationMessageArrived(context, mixPushMessage);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            mixPushMessage.setContent(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            mixPushMessage.setTitle(bundle.getString(JPushInterface.EXTRA_ALERT));
            JiGuangPushManager.sMixMessageProvider.onNotificationMessageClicked(context, mixPushMessage);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }

}