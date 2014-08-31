package com.ipdev.db.support;

import org.hibernate.SessionFactory;

public interface PersistenceContext {
    SessionFactory getSessionFactory();
}
