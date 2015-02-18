package com.test.musictrial.domain.interactor;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.exception.ErrorBundle;
import com.test.musictrial.domain.executor.PostExecutionThread;
import com.test.musictrial.domain.executor.ThreadExecutor;
import com.test.musictrial.domain.repository.SearchRepository;

import java.util.List;

/**
 * Created by albert on 17/02/15.
 */
public class SearchUseCaseImpl implements SearchUseCase {
    private final SearchRepository searchRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private String search;

    public SearchUseCaseImpl(SearchRepository repository, ThreadExecutor executor, PostExecutionThread postExecution) {
        this.searchRepository = repository;
        this.threadExecutor = executor;
        this.postExecutionThread = postExecution;
    }

    @Override
    public void execute(String search, Callback callback) {
        if ( search == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!");
        }

        this.search = search;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.searchRepository.search(this.search, this.repositoryCallback);
    }

    private final SearchRepository.SearchCallback repositoryCallback =
            new SearchRepository.SearchCallback() {

                @Override public void onSearch(List<ItunesItem> items) {
                    notifySearchSuccessfully(items);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifySearchSuccessfully(final List<ItunesItem> list) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onSearch(list);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
