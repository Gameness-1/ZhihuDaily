package com.zhihu.net;

import com.google.gson.JsonParseException;
import com.zhihu.net.util.VGson;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjweilingzi on 2016/1/19.
 */
public class result {

    public static Object data = "";

    /**
     * 转成bean
     *
     * @param gsonStr 要转化的字符串
     * @param cls     转化的类
     * @param <T>     泛型
     * @return
     */
    public <T> T getBean(String gsonStr, Class<T> cls) {
        T bean = null;
        try {
            bean = VGson.getGson().fromJson(gsonStr, cls);
        } catch (Exception e) {
            bean = null;
        }
        return bean;
    }

    /**
     * 转成beanList
     *
     * @param listType
     * @param <T>
     * @return
     */

    public static  <T> List<T> getBeanList(Type listType) {
        List<T> bean = new ArrayList<T>();
        try {
            if(data == null){
                data = new JSONArray().toString();
            }
            bean = VGson.getGson().fromJson(data.toString(),
                    listType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

}
