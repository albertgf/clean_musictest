package com.test.musictrial.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.test.musictrial.R;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.ui.activity.DetailActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemRowView extends RelativeLayout implements OnClickListener, View.OnLongClickListener {

    @InjectView (R.id.row_item_view_tv_title) TextView tvTitle;
    @InjectView (R.id.row_item_view_tv_artist) TextView tvArtist;
    @InjectView (R.id.row_item_view_iv_thumb) ImageView ivThumb;
    @InjectView(R.id.row_item_view_tv_duration) TextView tvDuration;

    private ItunesItem mItem;

    public ItemRowView(Context context) {
        this(context, null);
    }

    public ItemRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public void initView() {
        View view = View.inflate(getContext(), R.layout.row_item_view, this);
        ButterKnife.inject(this, view);

        this.setOnClickListener(this);
    }

    public void setValue(ItunesItem item) {
        final int millis = (int) item.getDuration();
        mItem = item;
        tvTitle.setText(item.getTitle());
        tvArtist.setText(item.getArtist());
        tvDuration.setText(String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        ));
        Picasso.with(getContext()).load(item.getThumbnail()).into(ivThumb);
    }

    @Override
    public void onClick(View v) {
        Log.d("click", "click");
        Gson gson = new Gson();
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("item_parsed", gson.toJson(mItem));
        getContext().startActivity(intent);
    }

    @Override public boolean onLongClick(View v) {
        Log.d("long", "click");
        return false;
    }
}
