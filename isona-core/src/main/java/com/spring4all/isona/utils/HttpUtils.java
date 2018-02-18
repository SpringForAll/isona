package com.spring4all.isona.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HttpUtils {

    private static OkHttpClient okHttpClient;
    private static HttpUtils httpUtils;

    @Autowired
    public HttpUtils(OkHttpClient okHttpClient) {
        httpUtils.okHttpClient = okHttpClient;
    }

    public static Object call(String url, Class clazz) {
        // TODO 可以更优雅一些
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();
            return JSONObject.parseObject(body.string(), clazz);
        } catch (Exception e) {
            log.warn(url + ", " + e.getMessage());
            return null;
        }
    }

}
