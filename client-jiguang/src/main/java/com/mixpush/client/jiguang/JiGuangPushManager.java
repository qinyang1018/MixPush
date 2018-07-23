package com.mixpush.client.jiguang;

import android.content.Context;

import com.mixpush.client.core.Contans;
import com.mixpush.client.core.MixMessageProvider;
import com.mixpush.client.core.MixPushManager;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Wiki on 2017/6/1.
 */

public class JiGuangPushManager implements MixPushManager {
    public static final String NAME = Contans.NAME_JIGUANG;
    public static MixMessageProvider sMixMessageProvider;

    public JiGuangPushManager() {
    }

    @Override
    public void registerPush(Context context) {
        JPushInterface.init(context);
    }

    @Override
    public void unRegisterPush(Context context) {
        unsetAlias(context, null);
        JPushInterface.stopPush(context.getApplicationContext());
    }

    @Override
    public void setAlias(Context context, String alias) {
        JPushInterface.setAlias(context, 0, alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        JPushInterface.deleteAlias(context, 0);
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags) {
            JPushInterface.setAlias(context, 1, tag);
        }

    }

    @Override
    public void unsetTags(Context context, String... tags) {
        Set<String> sets = new HashSet<>();
        sets.toArray(tags);
        JPushInterface.deleteTags(context, 1, sets);
    }


    @Override
    public String getName() {
        return Contans.NAME_JIGUANG;
    }

    @Override
    public void setMessageProvider(MixMessageProvider provider) {
        sMixMessageProvider = provider;
    }

    @Override
    public void disable(Context context) {
        unRegisterPush(context);
    }
}
