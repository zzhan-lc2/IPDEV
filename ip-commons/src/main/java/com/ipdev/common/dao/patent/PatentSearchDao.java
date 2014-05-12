package com.ipdev.common.dao.patent;

import java.util.List;
import java.util.Map;

import com.ipdev.common.entity.patent.Patent;

public interface PatentSearchDao {

	List<Patent> generalSearch(Map<String,String> searchParams);
}
