package com.zhihu.volley;

import com.zhihu.volley.VolleyError;

/**
 * Created by libowen on 2015/8/21.
 */
public class ResultError extends VolleyError {
    public int errorCode;
    public String errorMsg;

    public ResultError(int errorCode, String errorMsg) {
        super();
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }
}
