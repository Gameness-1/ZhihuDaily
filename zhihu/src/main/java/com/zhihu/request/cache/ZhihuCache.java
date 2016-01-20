package com.zhihu.request.cache;

import com.google.gson.reflect.TypeToken;
import com.zhihu.ZhihuApp;
import com.zhihu.bean.News;
import com.zhihu.net.util.SPUtil;
import com.zhihu.net.util.VGson;

import java.util.List;

/**
 * Created by bjweilingzi on 2016/1/19.
 */
public class ZhihuCache {

    public static final String NEWSFILE = "news_file";
    public static final String NEWSLISTKEY = "news_list_key";

    /*public static List<ContentInfo> getHomeList(){
        String json = share.getStringForShare(HOME_FILE, HOME_LIST_KEY);
        return VGson.getGson().fromJson(json, new TypeToken<List<ContentInfo>>() {
        }.getType());
    }

    public static void saveHomeList(List<ContentInfo> list){
        if(list != null){
            share.setStringForShare(HOME_FILE, HOME_LIST_KEY, VGson.getGson().toJson(list));
        }
    }*/

    private static SPUtil share = new SPUtil(new ZhihuApp().getZhihuAppInstance());

    public static void saveNewList(List<News> list) {
        if (list != null)
            share.setStringForShare(NEWSFILE, NEWSLISTKEY, VGson.getGson().toJson(list));
    }

    public static List<News> getNewList() {
        String json = share.getStringForShare(NEWSFILE, NEWSLISTKEY);
        return VGson.getGson().fromJson(json, new TypeToken<List<News>>() {}.getType());
    }
}
