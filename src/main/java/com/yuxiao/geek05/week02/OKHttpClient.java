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


    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    private static final String URL = "http://localhost:8801";

    public static void main(String[] args) throws IOException {
        OkHttpClient httpClient = builder.build();
        Request request = new Request.Builder().url(URL).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().byteString().utf8());
        } else {
            throw new IOException("Unexpected code:" + response);
        }
    }

}
