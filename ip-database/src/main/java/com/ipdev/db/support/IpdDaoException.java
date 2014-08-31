package com.ipdev.db.support;

import com.ipdev.common.IpdException;

public class IpdDaoException extends IpdException {
    private static final long serialVersionUID = 1L;

    public IpdDaoException(String message) {
        super(message);
    }

    public IpdDaoException(Throwable e) {
        super(e);
    }

    public IpdDaoException(String message, Throwable e) {
        super(message, e);
    }
}
