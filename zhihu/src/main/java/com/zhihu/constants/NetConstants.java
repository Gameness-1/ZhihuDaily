package com.zhihu.constants;

/**
 * Created by bjweilingzi on 2016/1/18.
 */
public class NetConstants {
    public static boolean TEST = false;
    public static String C_OPEN_DOMAIN;
    public static final String URL_C_OPEN_DOMAIN = "http://c.open.163.com";
    public static final String TEST_URL_C_OPEN_DOMAIN = "http://test.c.open.163.com";// 测试服务器

    public final static String sUserAgent = "NETS_Android";

    static {
        if (TEST)
            C_OPEN_DOMAIN = TEST_URL_C_OPEN_DOMAIN;
        else
            C_OPEN_DOMAIN = URL_C_OPEN_DOMAIN;
    }

    //首页列表
    public static final String URL_HOME_LIST = C_OPEN_DOMAIN + "/mob/home/list.do";

}
