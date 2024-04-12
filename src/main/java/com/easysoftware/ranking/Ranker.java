package com.easysoftware.ranking;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public interface Ranker {
    public List<Map<String, Object>> rankingDomainPageByOperationConfig(List<Map<String, Object>> domainPage);
    public List<Map<String, Object>> rankingDomainPageByAlgorithm(List<Map<String, Object>> domainPage);
}
