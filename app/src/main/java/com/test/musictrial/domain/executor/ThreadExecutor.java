package com.test.musictrial.domain.executor;

/**
 * Created by albert on 17/02/15.
 */
public interface ThreadExecutor {
    /**
     * Executes a {@link Runnable}.
     *
     * @param runnable The class that implements {@link Runnable} interface.
     */
    void execute(final Runnable runnable);
}