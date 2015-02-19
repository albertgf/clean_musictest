package com.test.musictrial.ui.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.ServiceStatus;

import de.greenrobot.event.EventBus;

/**
 * Created by albert on 18/02/15.
 */
public class DetailPresenter {

    private Context context;

    public DetailPresenter(Context context) {
        this.context = context;
    }

    public void onResume() {
        EventBus.getDefault().register(this);
    }

    public void onPause() {
        EventBus.getDefault().unregister(this);
    }

    public void setItem(String toParse) {
        Gson gson = new Gson();
        final ItunesItem item = gson.fromJson(toParse, ItunesItem.class);
        EventBus.getDefault().post(item.getTrackId());
        view.renderSong(item);
    }

    public void playSong() {
        EventBus.getDefault().post(ServiceStatus.PLAY);
    }

    public void playNext() {
        EventBus.getDefault().post(ServiceStatus.NEXT);
    }

    public void playPrevious() {
        EventBus.getDefault().post(ServiceStatus.PREV);
    }

    public void onEvent(ItunesItem item) {
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
