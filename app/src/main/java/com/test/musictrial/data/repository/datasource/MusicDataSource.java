package com.test.musictrial.data.repository.datasource;

import com.test.musictrial.data.entity.ApiContentResponse;

/**
 * Created by albert on 17/02/15.
 */
public interface MusicDataSource {

    interface SearchCallback {
        void onSearchSuccess(ApiContentResponse response);

        void onError(Exception exception);
    }

    void search(String text, SearchCallback searchCallback);

}
