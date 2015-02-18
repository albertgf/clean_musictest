package com.test.musictrial.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.ui.view.ItemRowView;

/**
 * Created by albert on 11/12/14.
 */
public class ItemHolder extends RecyclerView.ViewHolder {

    private final ItemRowView mRowView;
    private ItunesItem mItem;

    public ItemHolder(ItemRowView itemView) {
        super(itemView);

        mRowView = itemView;
    }

    public void bindItem(ItunesItem item) {
        mItem = item;
        mRowView.setValue(item);
        Log.d("BIND", "view");
    }
}
