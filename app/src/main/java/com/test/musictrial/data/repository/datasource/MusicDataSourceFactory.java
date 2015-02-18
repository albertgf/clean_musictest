package com.test.musictrial.data.repository.datasource;

import android.content.Context;

import com.test.musictrial.data.net.RestClient;

/**
 * Created by albert on 17/02/15.
 */
public class MusicDataSourceFactory {
    private final Context context;

    public MusicDataSourceFactory(Context context) {
        if ( context == null) {
            throw new IllegalArgumentException(("constructor parameters cannot be null!!!"));
        }
        this.context = context.getApplicationContext();
    }

    public MusicDataSource create() {
        return createCloudDataStore();
    }

    public MusicDataSource createCloudDataStore() {
        return new CloudMusicDataSource(RestClient.getInstance(context));
    }
}
