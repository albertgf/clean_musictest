package com.test.musictrial.data.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.musictrial.data.entity.ApiContentResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by albert on 17/02/15.
 */
public class RestClient {

    private static final String TAG = RestClient.class.getSimpleName();
    private static final String API_URL = "https://itunes.apple.com/";
    private static volatile RestClient sMusicClient;
    private final Context mContext;
    private RestApi mRestApi;
    private RestAdapter mRestAdapter;

    private RestClient(Context context) {
        mContext = context;
        Gson gson = new GsonBuilder().create();

        mRestAdapter = new RestAdapter.Builder().setEndpoint(API_URL).setClient(new OkClient()).setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson)).build();

        mRestApi = mRestAdapter.create(RestApi.class);
    }

    public static synchronized RestClient getInstance(Context context) {
        if (sMusicClient == null) {
            sMusicClient = new RestClient(context);
        }

        return sMusicClient;
    }

    public RestApi call() {
        return mRestApi;
    }

    public void search(final String text, Callback<ApiContentResponse> callback) {
        getApi().search(text, callback);
    }


    private RestApi getApi() {
        return mRestAdapter.create(RestApi.class);
    }

}
