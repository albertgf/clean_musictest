package com.test.musictrial.ui.presenter;

import android.util.Log;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.exception.ErrorBundle;
import com.test.musictrial.domain.interactor.SearchUseCase;

import java.util.List;

/**
 * Created by albert on 18/02/15.
 */
public class MainPresenter {

    private View view;
    private SearchUseCase searchUseCase;

    public MainPresenter(SearchUseCase useCase) {
        this.searchUseCase = useCase;
    }

    public void search(String s) {
        String search = s.replace(" ", "+");
        searchUseCase.execute(search, searchCallback);
    }

    private final SearchUseCase.Callback searchCallback = new SearchUseCase.Callback() {
        @Override public void onSearch(List<ItunesItem> items) {
            view.renderList(items);
        }

        @Override public void onError(ErrorBundle errorBundle) {
        }
    };

    public void setView(View view) {
        this.view = view;
    }

    public interface View {
        void renderList(List<ItunesItem> list);
    }
}
