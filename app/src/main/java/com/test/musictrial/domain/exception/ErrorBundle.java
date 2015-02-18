package com.test.musictrial.domain.exception;

/**
 * Created by albert on 17/02/15.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
