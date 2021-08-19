package com.yuxiao.geek05.week02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Http客户端请求
 * @author yangjunwei
 * @date 2021-08-15 10:03
 */
public class OKHttpClient {

    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    /**
     * 要转发的url
     */
    private static final String PROXY_URL = "http://localhost:8801";

    public Response get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return httpClient.newCall(request).execute();
    }


    public Response post(String url, Object body) {
        return null;
    }


    public static void main(String[] args) throws IOException {
        Request request = new Request.Builder().url(PROXY_URL).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().byteString().utf8());
        } else {
            throw new IOException("Unexpected code:" + response);
        }
    }

}
