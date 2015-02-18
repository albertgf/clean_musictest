package com.test.musictrial.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.musictrial.R;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.ui.presenter.DetailPresenter;
import com.test.musictrial.ui.view.ItemRowView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        initPresenter();
        getData(getIntent().getExtras());
    }

    private void initPresenter() {
        mPresenter = new DetailPresenter();
        mPresenter.setView(this);
    }

    private void getData(Bundle extras) {
        mPresenter.setItem(extras.getString("item_parsed"));
    }

    @OnClick (R.id.activity_detail_tv_next)
    public void onNextClick() {
        Toast.makeText(this, "call service next song", Toast.LENGTH_SHORT).show();
    }

    @OnClick (R.id.activity_detail_tv_previous)
    public void onPreviousClick() {
        Toast.makeText(this, "call service previous song", Toast.LENGTH_SHORT).show();
    }

    @OnClick (R.id.activity_detail_tv_play)
    public void onPlayClick() {
        Toast.makeText(this, "call service play/pause", Toast.LENGTH_SHORT).show();
    }

    @Override public void renderSong(ItunesItem item) {
        cvDetail.setValue(item);
        Picasso.with(this).load(item.getThumbnail()).into(ivArtwork);
    }
}
