package com.test.musictrial.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.test.musictrial.R;
import com.test.musictrial.data.entity.mapper.ItunesItemEntityDataMapper;
import com.test.musictrial.data.executor.JobExecutor;
import com.test.musictrial.data.repository.SearchDataRepository;
import com.test.musictrial.data.repository.datasource.MusicDataSourceFactory;
import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.executor.PostExecutionThread;
import com.test.musictrial.domain.executor.ThreadExecutor;
import com.test.musictrial.domain.interactor.SearchUseCaseImpl;
import com.test.musictrial.domain.repository.SearchRepository;
import com.test.musictrial.ui.UIThread;
import com.test.musictrial.ui.adapter.ItemAdapter;
import com.test.musictrial.ui.presenter.MainPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, MainPresenter.View {


    @InjectView (R.id.activity_main_rv) RecyclerView mItemsRv;
    private ItemAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        initializeLoginUseCase();
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private void initViews() {
        mItemsRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this);
        mItemsRv.setAdapter(new ItemAdapter(this));
    }


    private void initializeLoginUseCase() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        MusicDataSourceFactory musicDataSourceFactory = new MusicDataSourceFactory(this);
        ItunesItemEntityDataMapper itunesEntityDataMapper = new ItunesItemEntityDataMapper();
        SearchRepository searchRepository = SearchDataRepository.getInstance(musicDataSourceFactory, itunesEntityDataMapper);

        mPresenter = new MainPresenter(new SearchUseCaseImpl(searchRepository, threadExecutor, postExecutionThread));
        mPresenter.setView(this);
    }


    @Override public boolean onQueryTextSubmit(String s) {
        mPresenter.search(s);
        return false;
    }

    @Override public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override public void renderList(List<ItunesItem> list) {
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mItemsRv.setAdapter(mAdapter);
    }
}
