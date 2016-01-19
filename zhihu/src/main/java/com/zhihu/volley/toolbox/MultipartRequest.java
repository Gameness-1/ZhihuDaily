package com.zhihu.volley.toolbox;

import com.zhihu.net.mutipart.MultipartEntity;
import com.zhihu.tool.PalLog;
import com.zhihu.volley.AuthFailureError;
import com.zhihu.volley.NetworkResponse;
import com.zhihu.volley.Request;
import com.zhihu.volley.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libowen on 2015/7/16.
 */
public class MultipartRequest extends Request<String> {

    private MultipartEntity mMultiPartEntity;
    Map<String, String> mHeaders = new HashMap<String, String>();

    private final Response.Listener<String> mListener;

    /**
     * Creates a new request with the given url.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     */
    public MultipartRequest(String url, Response.Listener<String> listener) {
        this(url, listener, null);
    }

    /**
     * Creates a new POST request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public MultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
    }

    /**
     * @return
     */
    public MultipartEntity getMultiPartEntity() {
        return mMultiPartEntity;
    }

    public void setMultiPartEntity(MultipartEntity multiPartEntity) {
         mMultiPartEntity=multiPartEntity;
    }

    @Override
    public String getBodyContentType() {
        return mMultiPartEntity.getContentType().getValue();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public byte[] getBody() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // multipart body
            mMultiPartEntity.writeTo(bos);
        } catch (IOException e) {
            PalLog.e("MultipartRequest", "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = "";
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

}
