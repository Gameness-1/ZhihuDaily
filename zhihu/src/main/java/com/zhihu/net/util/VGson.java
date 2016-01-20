package com.zhihu.net.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by bjweilingzi on 2016/1/19.
 */
public class VGson {

    private static Gson gson;

    public static synchronized Gson getGson() {
        if(gson == null)
            gson = new GsonBuilder().serializeNulls().create();
        return gson;
    }
}
