package com.easysoftware.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


@Component
public class RankerImpl implements Ranker {


    /**
     * OperationConfigGateway instance for handling operation configurations.
     */
    @Autowired
    private OperationConfigGateway configGateway;

    /**
     * Rank domain pages based on a specific algorithm.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    public List<Map<String, Object>> rankingDomainPageByAlgorithm(final List<Map<String, Object>> domainPage) {
        return null;
    }


    /**
     * Rank domain pages based on operation configuration.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    public List<Map<String, Object>> rankingDomainPageByOperationConfig(final List<Map<String, Object>> domainPage) {

        List<OperationConfigVo> rankingConfig = configGateway.selectAll();

        // 再排序，避免数据库写入顺序错乱
        rankingConfig.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getOrderIndex())));
        List<Map<String, Object>> rankingList = new ArrayList<>();

        for (OperationConfigVo config : rankingConfig) {
            String rankingCatego = config.getCategorys();

            // 排序边栏
            Iterator<Map<String, Object>> iterator = domainPage.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> item = iterator.next();
                String catego = (String) item.get("name");
                if (catego.equals(rankingCatego)) {
                    // 排序内容
                    Map<String, Object> rankedItem = rankContent(item, config.getRecommend());
                    rankingList.add(rankedItem);
                    iterator.remove();
                }
            }
        }
        // 逆序排列 rankingList
        Collections.reverse(rankingList);
        // 一次性将 rankingList 添加到 domainPage 的开头
        domainPage.addAll(0, rankingList);

        return domainPage;
    }

    /**
     * Rank content based on a specific recommendation order.
     *
     * @param item           The content item to rank.
     * @param recommendOrder The recommendation order to apply.
     * @return A map representing the ranked content item.
     */
    private Map<String, Object> rankContent(final Map<String, Object> item, final String recommendOrder) {

        List<Object> menuVoList = (List<Object>) item.get("children");

        if (menuVoList == null) {
            return item;
        }

        String[] recommendNames = recommendOrder.replace(" ", "").split(",");
        List<Object> rankingList = new ArrayList<>();

        for (String name : recommendNames) {
            Iterator<Object> iterator = menuVoList.iterator();
            while (iterator.hasNext()) {
                Object menuVoObj = iterator.next();
                FiledApplicationVo menuVo = (FiledApplicationVo) menuVoObj;
                String softWareName = (String) menuVo.getName();
                if (softWareName.equals(name)) {
                    rankingList.add(menuVo);
                    iterator.remove();
                }
            }
        }

        for (int i = rankingList.size() - 1; i >= 0; i--) {
            menuVoList.add(0, rankingList.get(i));
        }

        item.put("children", menuVoList);

        return item;
    }
}
