package com.test.musictrial.data.repository;

import com.test.musictrial.data.entity.ApiContentResponse;
import com.test.musictrial.data.entity.mapper.ItunesItemEntityDataMapper;
import com.test.musictrial.data.repository.datasource.MusicDataSource;
import com.test.musictrial.data.repository.datasource.MusicDataSourceFactory;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.repository.SearchRepository;

import java.util.List;

/**
 * Created by albert on 17/02/15.
 */
public class SearchDataRepository implements SearchRepository {

    private static SearchDataRepository INSTANCE;
    private final MusicDataSourceFactory musicDataSource;
    private final ItunesItemEntityDataMapper musicDataMapper;

    public static synchronized SearchDataRepository getInstance(MusicDataSourceFactory dataStore, ItunesItemEntityDataMapper dataMapper) {
        if (INSTANCE == null) {
            INSTANCE = new SearchDataRepository(dataStore, dataMapper);
        }
        return INSTANCE;
    }

    protected SearchDataRepository(MusicDataSourceFactory dataStore, ItunesItemEntityDataMapper dataMapper) {
        if (dataStore == null || dataMapper == null) {
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.musicDataSource = dataStore;
        this.musicDataMapper = dataMapper;
    }


    @Override
    public void search(String search, final SearchCallback callback) {
        final MusicDataSource musicDataSource = this.musicDataSource.createCloudDataStore();
        musicDataSource.search(search, new MusicDataSource.SearchCallback() {

            @Override public void onSearchSuccess(ApiContentResponse response) {
                List<ItunesItem> list = (List<ItunesItem>) musicDataMapper.transform(response.results);
                callback.onSearch(list);
            }

            @Override public void onError(Exception exception) {

            }
        });
    }
}