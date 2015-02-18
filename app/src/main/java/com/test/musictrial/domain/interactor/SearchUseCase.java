package com.test.musictrial.domain.interactor;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.exception.ErrorBundle;

import java.util.List;

/**
 * Created by albert on 17/02/15.
 */
public interface SearchUseCase extends Interactor{

    interface Callback {
        void onSearch(List<ItunesItem> items);
        void onError(ErrorBundle errorBundle);
    }

    public void execute(String search, Callback callback);
}
