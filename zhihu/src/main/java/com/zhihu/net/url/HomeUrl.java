package com.zhihu.net.url;

import com.zhihu.constants.NetConstants;
import com.zhihu.net.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjweilingzi on 2016/1/20.
 */
public class HomeUrl {

    public static String getListUrl(String cursor, int pagesize) {
        Map<String, String> params = new HashMap<>();
        params.put("cursor", cursor);
        return StringUtil.appendUrl(NetConstants.URL_HOME_LIST, params);
    }

}
