package com.zhihu.net.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bjweilingzi on 2016/1/19.
 */
public class SPUtil {
    public Context mContext;
    public SharedPreferences sharedPreferences;

    public SPUtil(Context context) {
        mContext = context;
    }

    public String getStringForShare(String shareName, String key) {
        sharedPreferences = mContext.getSharedPreferences(shareName, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public String getStringForShare(String shareName, String key, String defultValue) {
        sharedPreferences = mContext.getSharedPreferences(shareName, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, defultValue);
    }

    public void setStringForShare(String shareName, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(shareName, Activity.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getIntForShare(String shareName, String key, int defultValue) {
        sharedPreferences = mContext.getSharedPreferences(shareName, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defultValue);
    }

    public void setIntForShare(String shareName, String key, int value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(shareName, Activity.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
