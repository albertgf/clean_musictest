package com.test.musictrial.data.repository.datasource;

import com.test.musictrial.data.entity.ApiContentResponse;
import com.test.musictrial.data.net.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by albert on 17/02/15.
 */
public class CloudMusicDataSource implements MusicDataSource {
    private final RestClient restApi;

    public CloudMusicDataSource(RestClient restClient) {
        this.restApi = restClient;
    }

    @Override public void search(String text, final SearchCallback searchCallback) {
        restApi.search(text, new Callback<ApiContentResponse>() {
            @Override
            public void success(ApiContentResponse apiContentResponse, Response response) {
                searchCallback.onSearchSuccess(apiContentResponse);
            }

            @Override public void failure(RetrofitError error) {
                searchCallback.onError(error);
            }
        });
    }
}
