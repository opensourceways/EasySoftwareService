/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

        for (int i = rankingList.size() - 1; i >= 0; i--) {
            domainPage.add(0, rankingList.get(i));
        }

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
