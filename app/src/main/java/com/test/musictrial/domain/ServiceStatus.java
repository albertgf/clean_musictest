package com.test.musictrial.domain;

import android.util.SparseArray;

/**
 * Created by albertgf on 18/2/15.
 */
public enum ServiceStatus {
    PLAY(0), PAUSE(1), NEXT(2), PREV(3);

    private static SparseArray<ServiceStatus> map = new SparseArray<ServiceStatus>();
    private int id;

    static {
        for (ServiceStatus id : ServiceStatus.values()) {
            map.put(id.getId(), id);
        }
    }

    ServiceStatus(final int id) {
        this.id = id;
    }

    public static ServiceStatus valueOf(int id) {
        return map.get(id);
    }

    public int getId() {
        return id;
    }
}
