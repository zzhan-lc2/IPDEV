package com.ipdev.common.dao.patent;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ipdev.common.entity.patent.Patent;

public interface PatentSearchDao extends PatentSimpleSearchDao {

    List<Patent> findPatentsByApplicant(String applicantName, Set<String> sourceDbs);

    List<Patent> generalSearch(Map<String, String> searchParams);
}
