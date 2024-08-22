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
package com.easysoftware.infrastructure.applyform.gatewayimpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.easysoftware.application.applyform.dto.ApplyFormSearchAdminCondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.dto.MyApply;
import com.easysoftware.application.applyform.dto.ProcessApply;
import com.easysoftware.application.applyform.vo.ApplyFormContentVO;
import com.easysoftware.application.applyform.vo.ApplyFormSearchMaintainerVO;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.DeleteException;
import com.easysoftware.common.exception.InsertException;
import com.easysoftware.common.exception.UpdateException;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.domain.apply.ApplyHandleRecord;
import com.easysoftware.domain.apply.gateway.ApplyGateway;
import com.easysoftware.domain.applyform.gateway.ApplyFormGateway;
import com.easysoftware.domain.collaboration.gateway.PackageStatusGateway;
import com.easysoftware.infrastructure.apply.gatewayimpl.dataobject.ApplyhandleRecordsDO;
import com.easysoftware.infrastructure.applyform.gatewayimpl.converter.ApplyFormConvertor;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;
import com.easysoftware.infrastructure.mapper.ApplyFormDOMapper;
import com.easysoftware.infrastructure.mapper.ApplyHandleRecordsDOMapper;

import jakarta.annotation.Resource;

@Component
public class ApplyFormGatewayImpl implements ApplyFormGateway {

    /**
     * Logger for ApplicationVersionQueryAdapter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyFormGatewayImpl.class);

    /**
     * Resource for interacting with package status Gateway.
     */
    @Resource
    private PackageStatusGateway pkgStatusGateway;

    /**
     * Resource for interacting with Apply Gateway.
     */
    @Resource
    private ApplyGateway applyGateway;

    /**
     * Autowired field for the ApplicationVersionDOMapper.
     */
    @Autowired
    private ApplyFormDOMapper applyFormDOMapper;

    /**
     * Autowired field for the ApplyHandleRecordsDOMapper.
     */
    @Autowired
    private ApplyHandleRecordsDOMapper applyHandleRecordsDOMapper;

    /**
     * Autowired permission for the UserPermission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApplyFormByPage(ApplyFormSearchMaintainerCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String maintainer = userPermission.getUserLogin();

        Page<ApplyFormDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplyFormDO> wrapper = new QueryWrapper<>();
        wrapper.eq("maintainer", maintainer);
        if (condition.getRepo() != null) {
            wrapper.in("repo", Arrays.asList(condition.getRepo().split(",")));
        }
        if (condition.getMetric() != null) {
            wrapper.in("metric", Arrays.asList(condition.getMetric().split(",")));
        }
        if (condition.getApplyStatus() != null) {
            wrapper.in("apply_status", Arrays.asList(condition.getApplyStatus().split(",")));
        }
        wrapper.orderByDesc("update_at");
        IPage<ApplyFormDO> resPage = applyFormDOMapper.selectPage(page, wrapper);

        long total = resPage.getTotal();
        List<ApplyFormDO> applyFormDOs = resPage.getRecords();
        List<ApplyFormSearchMaintainerVO> applyFormVOs = ApplyFormConvertor.toApplyFormVO(applyFormDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", applyFormVOs);

        return res;

    }

    /**
     * Query information based on the provided search condition.
     *
     * @param applyId The search condition for querying apply form
     * @return A map containing relevant information
     */
    public Map<String, Object> queryApplyFormByApplyId(Long applyId) {
        String maintainer = userPermission.getUserLogin();

        QueryWrapper<ApplyFormDO> queryWrapperForm = new QueryWrapper<>();
        queryWrapperForm.eq(PackageConstant.APPLY_FORM_ID, applyId);
        queryWrapperForm.eq(PackageConstant.APPLY_FORM_MAINTAINER, maintainer);

        List<ApplyFormDO> applyFormListDOs = applyFormDOMapper.selectList(queryWrapperForm);
        List<ApplyFormSearchMaintainerVO> applyFormListVOs = ApplyFormConvertor.toApplyFormVO(applyFormListDOs);

        QueryWrapper<ApplyhandleRecordsDO> queryWrapperRecords = new QueryWrapper<>();
        queryWrapperRecords.eq(PackageConstant.APPLY_FORM_ID, applyId);
        queryWrapperRecords.eq(PackageConstant.APPLY_FORM_MAINTAINER, maintainer);

        List<ApplyhandleRecordsDO> applyhandleRecordsDOs = applyHandleRecordsDOMapper.selectList(queryWrapperRecords);

        List<ApplyFormContentVO> applyFormContentVOs = new ArrayList<>();
        for (ApplyFormSearchMaintainerVO applyFormVO : applyFormListVOs) {
            ApplyFormContentVO applyFormContentVO = new ApplyFormContentVO();
            BeanUtils.copyProperties(applyFormVO, applyFormContentVO);
            applyFormContentVO.setApprovalTime(applyhandleRecordsDOs.get(0).getCreatedAt());
            applyFormContentVOs.add(applyFormContentVO);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", (long) applyFormContentVOs.size());
        res.put("list", applyFormContentVOs);

        return res;
    }

    /**
     * process apply based on the provided condition.
     *
     * @param processApply The update info for querying apply.
     * @return A map containing relevant information
     */
    @Override
    @Transactional(rollbackFor = UpdateException.class)
    public boolean processApply(ProcessApply processApply) {
        ApplyFormDO applyFormDO = ApplyFormConvertor.toApplyFormDO(processApply);
        UpdateWrapper<ApplyFormDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("apply_id", processApply.getApplyId());

        applyFormDO.setAdminstrator(userPermission.getUserName());
        int mark = applyFormDOMapper.update(applyFormDO, wrapper);
        if (mark != 1) {
            throw new UpdateException("update apply status failed");
        }

        applyFormDO = applyFormDOMapper.selectOne(wrapper);

        ApplyHandleRecord record = new ApplyHandleRecord();
        BeanUtils.copyProperties(applyFormDO, record);
        if (!applyGateway.savehandleRecord(record)) {
            throw new UpdateException("save record failed");
        }

        if (PackageConstant.APPLY_APPROVED.equalsIgnoreCase(processApply.getApplyStatus())) {
            if (!updatePackageStatus(applyFormDO)) {
                throw new UpdateException("update package status failed");
            }
        }

        return true;
    }

    /**
     * update package based on the provided condition.
     *
     * @param applyFormDO apply content.
     * @return update result
     */
    private boolean updatePackageStatus(ApplyFormDO applyFormDO) {
        return pkgStatusGateway.updateByMetric(applyFormDO);
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApplyFormByConditionAdmin(ApplyFormSearchAdminCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ApplyFormDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplyFormDO> wrapper = initWrapperByCondition(condition);

        IPage<ApplyFormDO> resPage = applyFormDOMapper.selectPage(page, wrapper);
        long total = resPage.getTotal();
        List<ApplyFormDO> applyFormDOs = resPage.getRecords();
        List<ApplyFormSearchMaintainerVO> applyFormVOs = ApplyFormConvertor.toApplyFormVO(applyFormDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", applyFormVOs);

        return res;
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApplyFormContentByConditionAdmin(ApplyFormSearchAdminCondition condition) {

        QueryWrapper<ApplyFormDO> queryWrapperForm = new QueryWrapper<>();
        queryWrapperForm.eq(PackageConstant.APPLY_FORM_ID, condition.getApplyId());
        List<ApplyFormDO> applyFormListDOs = applyFormDOMapper.selectList(queryWrapperForm);
        List<ApplyFormSearchMaintainerVO> applyFormListVOs = ApplyFormConvertor.toApplyFormVO(applyFormListDOs);

        QueryWrapper<ApplyhandleRecordsDO> queryWrapperRecords = new QueryWrapper<>();
        queryWrapperRecords.eq(PackageConstant.APPLY_FORM_ID, condition.getApplyId());

        List<ApplyhandleRecordsDO> applyhandleRecordsDOs = applyHandleRecordsDOMapper.selectList(queryWrapperRecords);

        List<ApplyFormContentVO> applyFormContentVOs = new ArrayList<>();
        for (ApplyFormSearchMaintainerVO applyFormVO : applyFormListVOs) {
            ApplyFormContentVO applyFormContentVO = new ApplyFormContentVO();
            BeanUtils.copyProperties(applyFormVO, applyFormContentVO);
            applyFormContentVO.setApprovalTime(applyhandleRecordsDOs.get(0).getCreatedAt());
            applyFormContentVOs.add(applyFormContentVO);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", (long) applyFormContentVOs.size());
        res.put("list", applyFormContentVOs);

        return res;
    }

    /**
     * Query apporved apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApprovedApplyFormByConditionAdmin(ApplyFormSearchAdminCondition condition) {
        String userName = userPermission.getUserName();

        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ApplyFormDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplyFormDO> queryWrapperForm = new QueryWrapper<>();
        queryWrapperForm.eq(PackageConstant.APPLY_FORM_ADMIN, userName);

        IPage<ApplyFormDO> resPage = applyFormDOMapper.selectPage(page, queryWrapperForm);
        long total = resPage.getTotal();
        List<ApplyFormDO> applyFormDOs = resPage.getRecords();
        List<ApplyFormSearchMaintainerVO> applyFormVOs = ApplyFormConvertor.toApplyFormVO(applyFormDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", applyFormVOs);

        return res;
    }

    /**
     * initWrapperByCondition.
     *
     * @param condition The search condition for querying apply form
     * @return A QueryWrapper
     */
    private QueryWrapper<ApplyFormDO> initWrapperByCondition(ApplyFormSearchAdminCondition condition) {
        QueryWrapper<ApplyFormDO> wrapper = new QueryWrapper<>();
        if (condition.getApplyStatus() != null) {
            wrapper.in("apply_status", Arrays.asList(condition.getApplyStatus().split(",")));
        }
        if (condition.getRepo() != null) {
            wrapper.in("repo", Arrays.asList(condition.getRepo().split(",")));
        }
        if (condition.getMetric() != null) {
            wrapper.in("metric", Arrays.asList(condition.getMetric().split(",")));
        }
        if ("desc".equals(condition.getTimeOrder())) {
            wrapper.orderByDesc("created_at");
        } else if ("asc".equals(condition.getTimeOrder())) {
            wrapper.orderByAsc("created_at");
        }
        return wrapper;
    }

    /**
     * MyApply apply based on the provided condition..
     *
     * @param myApply The process result for apply.
     * @return A boolean
     */
    @Override
    @Transactional(rollbackFor = InsertException.class)
    public boolean submitMyApplyWithLimit(MyApply myApply) {
        boolean result = true;
        ApplyFormDO applyFormDO = ApplyFormConvertor.toApplyFormDO(myApply);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        applyFormDO.setCreatedAt(now);
        applyFormDO.setUpdateAt(now);

        String maintainer = userPermission.getUserLogin();
        applyFormDO.setMaintainer(maintainer);
        int countapplyForm = applyFormDOMapper.insert(applyFormDO);

        ApplyhandleRecordsDO applyhandleRecordsDO = ApplyFormConvertor.toHandleRecordsDO(applyFormDO);
        int countapplyHandle = applyHandleRecordsDOMapper.insert(applyhandleRecordsDO);

        if (countapplyForm != 1 ||  countapplyHandle != 1) {
            LOGGER.info(
                    "UserName:" + maintainer + " Client Ip: localhost" + " Type: Delete" + " ApplyID:"
                            + applyFormDO.getApplyId() + " Result: failure.");
            throw new InsertException("insert failed");
        }
        LOGGER.info(
                "UserName:" + maintainer + " Client Ip: localhost" + " Type: Delete" + " ApplyID:"
                        + applyFormDO.getApplyId() + " Result: success.");

        return result;
    }

    /**
     * MyApply apply based on the provided condition..
     *
     * @param myApply The process result for apply.
     * @return A boolean
     */
    @Override
    @Transactional(rollbackFor = DeleteException.class)
    public boolean revokeMyApplyWithLimit(MyApply myApply) {
        boolean result = true;
        Long id = myApply.getApplyId();
        String maintainer = userPermission.getUserLogin();

        if (!checkMaintainerLimit(myApply.getApplyId(), maintainer)) {
            LOGGER.info(
                    "UserName:" + maintainer + " Client Ip: localhost" + " Type: Delete" + " ApplyID:"
                            + id + " Result: failure.");
            throw new DeleteException("permission authentication failed");
        }
        UpdateWrapper<ApplyFormDO> wrapper = new UpdateWrapper<>();
        wrapper.eq(PackageConstant.APPLY_FORM_MAINTAINER, maintainer).eq(PackageConstant.APPLY_FORM_ID,
        id).set("apply_status", PackageConstant.APPLY_REVOKED);

        int deleteNum = applyFormDOMapper.update(null, wrapper);
        if (deleteNum != 1) {
            LOGGER.info(
                    "UserName:" + maintainer + " Client Ip: localhost" + " Type: Delete" + " ApplyID:"
                            + id + " Result: failure.");
            throw new DeleteException("revoke failed");
        }
        LOGGER.info(
                "UserName:" + maintainer + " Client Ip: localhost" + " Type: Delete" + " ApplyID:"
                        + id + " Result: success.");

        return result;
    }

    /**
     * MyApply apply based on the provided condition..
     *
     * @param myApply The process result for apply.
     * @return A boolean
     */
    @Override
    @Transactional(rollbackFor = UpdateException.class)
    public boolean updateMyApplyWithLimit(MyApply myApply) {
        boolean result = true;
        ApplyFormDO applyFormDO = ApplyFormConvertor.toApplyFormDO(myApply);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        applyFormDO.setUpdateAt(now);

        String maintainer = userPermission.getUserLogin();
        applyFormDO.setMaintainer(maintainer);

        if (!checkMaintainerLimit(applyFormDO.getApplyId(), maintainer)) {
            LOGGER.info(
                    "UserName:" + maintainer + " Client Ip: localhost" + " Type: Update" + " ApplyID:"
                            + applyFormDO.getApplyId() + " Result: failuer.");
            throw new UpdateException("permission authentication failed");
        }

        UpdateWrapper<ApplyFormDO> wrapper = new UpdateWrapper<>();
        wrapper.eq(PackageConstant.APPLY_FORM_ID, myApply.getApplyId());
        wrapper.eq(PackageConstant.APPLY_FORM_MAINTAINER, maintainer);

        int count = applyFormDOMapper.update(applyFormDO, wrapper);
        if (count != 1) {
            LOGGER.info(
                    "UserName:" + maintainer + " Client Ip: localhost" + " Type: Update" + " ApplyID:"
                            + applyFormDO.getApplyId() + " Result: failuer.");
            throw new UpdateException("update failed");
        }
        LOGGER.info(
                "UserName:" + maintainer + " Client Ip: localhost" + " Type: Update" + " ApplyID:"
                        + applyFormDO.getApplyId() + " Result: success.");

        return result;
    }

    /**
     * MyApply apply based on the provided condition.
     *
     * @param applyId    check the maintainer's limits of authority.
     * @param maintainer check the maintainer's limits of authority.
     * @return A boolean .
     */
    @Override
    public boolean checkMaintainerLimit(Long applyId, String maintainer) {
        List<ApplyFormDO> list = applyFormDOMapper.selectByMap(Map.of("apply_id", applyId));
        if (list.size() == 0) {
            return false;
        }
        for (ApplyFormDO applyFormDO : list) {
            if (!applyFormDO.getMaintainer().equals(maintainer)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Query repos by maintainer.
     *
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApplyReposByMaintainer() {
        String maintainer = userPermission.getUserLogin();
        QueryWrapper<ApplyFormDO> wrapper = new QueryWrapper<>();
        wrapper.eq(PackageConstant.APPLY_FORM_MAINTAINER, maintainer);
        return queryApplyRepos(wrapper);
    }

    /**
     * query repos by admin.
     *
     * @return A map containing relevant information.
     */
    @Override
    public Map<String, Object> queryApplyReposByAdmin() {
        QueryWrapper<ApplyFormDO> wrapper = new QueryWrapper<>();
        return queryApplyRepos(wrapper);
    }

    /**
     * query repos.
     *
     * @param wrapper select condition
     * @return A map containing relevant information.
     */
    public Map<String, Object> queryApplyRepos(QueryWrapper<ApplyFormDO> wrapper) {
        wrapper.select("repo");
        List<ApplyFormDO> applyFormListDOs = applyFormDOMapper.selectList(wrapper);
        Map<String, Object> res = new HashMap<>();
        HashSet<String> repos = new HashSet<>();
        for (ApplyFormDO apply : applyFormListDOs) {
            repos.add(apply.getRepo());
        }
        res.put("total", (long) repos.size());
        res.put("list", repos);
        return res;
    }
}
