package com.test.musictrial.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.ui.holder.ItemHolder;
import com.test.musictrial.ui.view.ItemRowView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    private Context mContext;
    private List<ItunesItem> mList = new ArrayList<ItunesItem>();

    public ItemAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new ItemHolder(new ItemRowView(mContext));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int pos) {
        holder.bindItem(mList.get(pos));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<ItunesItem> list) {
        Log.d("ADApter", "list");
        Log.d("adapter", list.size() + "");
        this.mList.clear();
        this.mList = list;
    }
}
