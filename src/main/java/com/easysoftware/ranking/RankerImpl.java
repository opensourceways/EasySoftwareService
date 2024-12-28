/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.ranking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.ResultSet;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class RankerImpl implements Ranker {

    /**
     * Logger for FieldApplicationServiceImpl.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RankerImpl.class);
    /**
     * OperationConfigGateway instance for handling operation configurations.
     */
    @Autowired
    private OperationConfigGateway configGateway;

    /**
     * database url.
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * database username.
     */
    @Value("${spring.datasource.username}")
    private String user;

    /**
     * database password.
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * change the category by operation config.
     *
     * @return wheter change is sucessce.
     */
    @Scheduled(fixedRate = PackageConstant.TIMER) // 每5min执行一次
    public boolean changeCategoryByConfig() {
        List<OperationConfigVo> changeConfig = configGateway.selectCategoryChanges();

        // 任何事务失败均失败，需retry
        // step1 事务更新rpm基表数据
        if (!updateTransaction(changeConfig, PackageConstant.RPM_PKG_TABLE_CHANGE)) {
            return false;
        }
        // step2 事务更新app基表数据
        if (!updateTransaction(changeConfig, PackageConstant.APPLICATION_TABLE_CHANGE)) {
            return false;
        }

        // step3 事务更新oepkg基表数据
        if (!updateTransaction(changeConfig, PackageConstant.OEPKG_CHANGE)) {
            return false;
        }

        // step4 事务更新epkg基表数据
        if (!updateTransaction(changeConfig, PackageConstant.EPKG_CHANGE)) {
            return false;
        }

        // step5 事务更新filed基表数据
        if (!updateTransaction(changeConfig, PackageConstant.FIELD_CHANGE)) {
            return false;
        }

        // step6 移动filedpackage数据至首页domain展示
        if (!insertTransaction(changeConfig)) {
            return false;
        }
        return true;

    }

    /**
     * transactionally insertTransaction the category by config.
     *
     * @param changeConfig list of change config
     * @return wheter success
     */
    private boolean insertTransaction(List<OperationConfigVo> changeConfig) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // 关闭自动提交
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ParamErrorException("get sql seesion failed");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.error("sql cloesd error: {}", e.getMessage());
            }
        }
        PreparedStatement pstmt = null;
        PreparedStatement exsitmt = null;
        PreparedStatement refreshtmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // 关闭自动提交
            conn.setAutoCommit(false);
            String sql = "INSERT into domain_package SELECT * from field_package WHERE name = ?"
                        + "order by length(tags) desc limit 1";
            String exsitSql = "SELECT name from domain_package WHERE name = ?";
            for (OperationConfigVo config : changeConfig) {
                exsitmt = conn.prepareStatement(exsitSql);
                exsitmt.setString(1, config.getRecommend());
                rs = exsitmt.executeQuery();
                // 已存在数据则刷新 不插入
                if (rs.next()) {
                    refreshtmt = conn.prepareStatement(PackageConstant.DOMAIN_CHANGE);
                    refreshtmt.setString(1, config.getCategorys());
                    refreshtmt.setString(2, config.getRecommend());
                    refreshtmt.executeUpdate();
                } else {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, config.getRecommend());
                    pstmt.executeUpdate();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    LOGGER.error("error hanpend in transaction. trans roll back:{}", e.getMessage());
                }
            } catch (SQLException ex) {
                throw new ParamErrorException("trans roll back");
            }

            throw new ParamErrorException("sql excute error");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (exsitmt != null) {
                    exsitmt.close();
                }
                if (refreshtmt != null) {
                    refreshtmt.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.error("sql cloesd error: {}", e.getMessage());
            }
        }

        return true;
    }

    /**
     * transactionally update the category by config.
     *
     * @param sqlTemplate  the default update sql template
     * @param changeConfig list of change config
     * @return wheter success
     */
    private boolean updateTransaction(List<OperationConfigVo> changeConfig, String sqlTemplate) {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // 关闭自动提交
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ParamErrorException("get sql seesion failed");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.error("sql cloesd error: {}", e.getMessage());
            }
        }
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // 关闭自动提交
            conn.setAutoCommit(false);
            for (OperationConfigVo config : changeConfig) {
                pstmt = conn.prepareStatement(sqlTemplate);
                pstmt.setString(1, config.getCategorys());
                pstmt.setString(2, config.getRecommend());
                pstmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    LOGGER.error("error hanpend in transaction. trans roll back");
                }
            } catch (SQLException ex) {
                throw new ParamErrorException("trans roll back");
            }

            throw new ParamErrorException("sql excute error");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.error("sql cloesd error", e);
            }
        }

        return true;
    }

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

        List<OperationConfigVo> rankingConfig = configGateway.selectRankingConfig();

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

        return mergeList(rankingList, domainPage);
    }

    /**
     * merge rankedlist in OperationConfig and unRankedList.
     *
     * @param rankedList   rankedList.
     * @param unRandedList unRandedList.
     * @return list of entity.
     */
    public List<Map<String, Object>> mergeList(List<Map<String, Object>> rankedList,
            List<Map<String, Object>> unRandedList) {
        List<Map<String, Object>> res = new ArrayList<>();
        res.addAll(rankedList);

        Map<String, List<Map<String, Object>>> map = unRandedList.stream().collect(
                Collectors.groupingBy(e -> String.valueOf(e.get("name"))));
        List<String> sortedCategories = SortUtil.sortCategoryColumn(map.keySet());
        for (String sortedCategory : sortedCategories) {
            List<Map<String, Object>> list = map.get(sortedCategory);
            res.add(list.get(0));
        }

        return res;
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
