package com.easysoftware.ranking;

import java.util.Map;
import java.util.List;


public interface Ranker {
    /**
     * Rank domain pages based on operation configuration.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    List<Map<String, Object>> rankingDomainPageByOperationConfig(List<Map<String, Object>> domainPage);

    /**
     * Rank domain pages based on algorithm.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    List<Map<String, Object>> rankingDomainPageByAlgorithm(List<Map<String, Object>> domainPage);

}
