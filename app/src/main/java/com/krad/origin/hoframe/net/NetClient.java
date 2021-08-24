package com.krad.origin.hoframe.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetClient {

    private Api api;

    protected NetClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(getBaseUrl())
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        this.api = retrofit.create(Api.class);
    }

    protected String getBaseUrl() {
        return NetConfig.Companion.getBaseUrl();
    }

    public Api getApi() {
        return api;
    }

    private static class NetClientHolder {
        private static final NetClient INSTANCE = new NetClient();
    }

    public static NetClient getInstance() {
        return NetClientHolder.INSTANCE;
    }
}
