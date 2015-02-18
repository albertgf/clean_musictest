package com.test.musictrial.ui.presenter;

import com.google.gson.Gson;
import com.test.musictrial.domain.ItunesItem;

/**
 * Created by albert on 18/02/15.
 */
public class DetailPresenter {

    public void setItem(String toParse) {
        Gson gson = new Gson();
        final ItunesItem item = gson.fromJson(toParse, ItunesItem.class);
        view.renderSong(item);
    }

    private View view;

    public void setView(View view) {
        this.view = view;
    }

    public interface View {
        void renderSong(ItunesItem item);
    }
}
