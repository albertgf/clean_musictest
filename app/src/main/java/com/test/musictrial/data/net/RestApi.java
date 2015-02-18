package com.test.musictrial.data.net;

import com.test.musictrial.data.entity.ApiContentResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by albert on 17/02/15.
 */
public interface RestApi {

    @GET(MusicPath.PATH_SEARCH)
    public void search(
            @Query("term") String text,
            Callback<ApiContentResponse> callback
    );
}
