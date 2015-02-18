package com.test.musictrial.domain.executor;

/**
 * Created by albert on 17/02/15.
 */
public interface PostExecutionThread {
    /**
     * Causes the {@link Runnable} to be added to the message queue of the Main UI Thread
     * of the application.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    void post(Runnable runnable);
}
