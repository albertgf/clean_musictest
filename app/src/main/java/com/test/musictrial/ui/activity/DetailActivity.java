package com.test.musictrial.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.musictrial.R;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.ui.presenter.DetailPresenter;
import com.test.musictrial.ui.service.PlayerService;
import com.test.musictrial.ui.view.ItemRowView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by albert on 18/02/15.
 */
public class DetailActivity extends ActionBarActivity implements DetailPresenter.View {

    @InjectView (R.id.activity_detail_cv_detail) ItemRowView cvDetail;
    @InjectView (R.id.activity_detail_iv_artwork) ImageView ivArtwork;
    @InjectView (R.id.activity_detail_tv_next) TextView tvNext;
    @InjectView (R.id.activity_detail_tv_play) TextView tvPlay;
    @InjectView (R.id.activity_detail_tv_previous) TextView tvPrev;

    private DetailPresenter mPresenter;
    private Intent playerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        initPresenter();
        getData(getIntent().getExtras());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    private void initPresenter() {
        mPresenter = new DetailPresenter(this);
        mPresenter.setView(this);
    }

    private void getData(Bundle extras) {
        mPresenter.setItem(extras.getString("item_parsed"));
    }

    @OnClick (R.id.activity_detail_tv_next)
    public void onNextClick() {
        mPresenter.playNext();
    }

    @OnClick (R.id.activity_detail_tv_previous)
    public void onPreviousClick() {
        mPresenter.playPrevious();
    }

    @OnClick (R.id.activity_detail_tv_play)
    public void onPlayClick() {
        mPresenter.playSong();
    }

    @Override public void renderSong(ItunesItem item) {
        cvDetail.setValue(item);
        Picasso.with(this).load(item.getThumbnail()).into(ivArtwork);
    }
}
