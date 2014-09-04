package com.ipdev.common.dao.query;

import java.util.List;

import com.ipdev.common.query.Query;

public interface QueryDao {

    void save(Query query);

    Query findById(String queryId);

    List<Query> findByCreator(String creator);
}
