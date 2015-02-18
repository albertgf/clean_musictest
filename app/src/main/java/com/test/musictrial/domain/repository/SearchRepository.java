package com.test.musictrial.domain.repository;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.exception.ErrorBundle;

import java.util.List;

/**
 * Created by albert on 17/02/15.
 */
public interface SearchRepository {

    interface SearchCallback {
        void onSearch(List<ItunesItem> listItems);

        void onError(ErrorBundle errorBundle);
    }

    void search(final String search, SearchCallback callback);
}
