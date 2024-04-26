package com.easysoftware.infrastructure.fieldapplication.gatewayimpl;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;

import com.easysoftware.infrastructure.mapper.FieldApplicationDOMapper;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FieldApplicationGatewayImpl implements FieldapplicationGateway {
    @Autowired
    private FieldApplicationDOMapper fieldAppMapper;

    private static final Logger logger = LoggerFactory.getLogger(FieldApplicationGatewayImpl.class);

    @Override
    public Map<String, Object> queryAll(FiledApplicationSerachCondition condition){
        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", "!23")
        );
        return res;
    }

    // @Override
    // public Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition) {
    //     Page<RPMPackageDO> page = createPage(condition);
    //     QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(), 
    //             condition, "rpm_update_at");
    //     IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
    //     List<RPMPackageDO> rPMDOs = resPage.getRecords();
    //     List<RPMPackageDetailVo> rPMDetails = RPMPackageConverter.toDetail(rPMDOs);
    //     long total = resPage.getTotal();

    //     Map<String, Object> res = Map.ofEntries(
    //         Map.entry("total", total),
    //         Map.entry("list", rPMDetails)
    //     );
        
    //     return res;
    // }

    // @Override
    // public boolean save(RPMPackage rPMPkg) {
    //     RPMPackageDO rPMPkgDO = RPMPackageConverter.toDataObjectForCreate(rPMPkg);
    //     int mark = rPMPkgMapper.insert(rPMPkgDO);
    //     return mark == 1;
    // }

    // @Override
    // public int update(RPMPackage rPMPkg) {
    //     RPMPackageDO rPMPkgDO = RPMPackageConverter.toDataObjectForUpdate(rPMPkg);

    //     UpdateWrapper<RPMPackageDO> wrapper = new UpdateWrapper<>();
    //     wrapper.eq("pkg_id", rPMPkg.getPkgId());

    //     int mark = rPMPkgMapper.update(rPMPkgDO, wrapper);
    //     return mark;
    // }

    // @Override
    // public boolean existRPM(String id) {
    //     QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
    //     wrapper.eq("id", id);
    //     return rPMPkgMapper.exists(wrapper);
    // }

    // @Override
    // public Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition) {
    //     Page<RPMPackageDO> page = createPage(condition);
    //     QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(), 
    //             condition, "rpm_update_at");
    //     RPMPackageMenuVo pkgVo = new RPMPackageMenuVo();
    //     List<String> columns = ClassField.getFieldNames(pkgVo);
    //     wrapper.select(columns);
    //     IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
    //     List<RPMPackageDO> rpmDOs = resPage.getRecords();
    //     List<RPMPackageMenuVo> rPMMenus = RPMPackageConverter.toMenu(rpmDOs);
    //     long total = resPage.getTotal();

    //     Map<String, Object> res = Map.ofEntries(
    //         Map.entry("total", total),
    //         Map.entry("list", rPMMenus)
    //     );
    //     return res;
    // }

    // private Page<RPMPackageDO> createPage(RPMPackageSearchCondition condition) {
    //     int pageNum = condition.getPageNum();
    //     int pageSize = condition.getPageSize();
    //     Page<RPMPackageDO> page = new Page<>(pageNum, pageSize);
    //     return page;
    // }

    // @Override
    // public Map<String, List<String>> queryColumn(List<String> columns) {
    //     Map<String, List<String>> res = new HashMap<>();
    //     for (String column : columns) {
    //         List<String> colList = queryColumn(column);
    //         res.put(column, colList);
    //     }
    //     return res;
    // }

    // public List<String> queryColumn(String column) {
    //     column = "category".equals(column) ? "category" : column;
    //     QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
    //     wrapper.select("distinct " + column);
    //     List<RPMPackageDO> rpmColumn = new ArrayList<>();
    //     try {
    //         rpmColumn = rPMPkgMapper.selectList(wrapper);
    //     } catch (BadSqlGrammarException e) {
    //         throw new ParamErrorException("unsupported param: " + column);
    //     }

    //     column = StringUtil.underlineToCamel(column);
    //     List<String> res = RPMPackageConverter.toColumn(rpmColumn, column);

    //     return res;
    // }

    // @Override
    // public long queryTableLength() {
    //     return rPMPkgMapper.selectCount(null);
    // }

    // @Override
    // public Collection<RPMPackageDO> convertBatch(Collection<String> dataObject){
    //     long startTime = System.nanoTime();
    //     Collection<RPMPackageDO> ObjList = new ArrayList<>();
    //     for (String obj : dataObject) {
    //         RPMPackage rpmPackage = ObjectMapperUtil.jsonToObject(obj, RPMPackage.class);
    //         RPMPackageDO rpmDO = RPMPackageConverter.toDataObjectForCreate(rpmPackage);
    //         log.info("convert pkgId: {}", rpmDO.getPkgId());
    //         ObjList.add(rpmDO);
    //     }
    //     long endTime1 = System.nanoTime();
    //     long duration = (endTime1 - startTime) / 1000000;
    //     logger.info("转换时间： " + duration + " 毫秒，" + "数据量：" + dataObject.size());
    //     return ObjList;
    // }

    // @Override
    // public Map<String, Object> queryPartRPMPkgMenu(RPMPackageSearchCondition condition) {
    //     QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO()
    //             , condition, "");
    //     RPMPackageDomainVo pkgVo = new RPMPackageDomainVo();
    //     List<String> columns = ClassField.getFieldNames(pkgVo);
    //     wrapper.select(columns);
    //     wrapper.in("category", List.of("AI", "大数据", "分布式存储", "数据库", "云服务", "HPC"));
    //     List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
    //     List<RPMPackageDomainVo> menus = RPMPackageConverter.toDomain(rpmList);
    //     Map<String, Object> res = Map.ofEntries(
    //         Map.entry("total", menus.size()),
    //         Map.entry("list", menus)
    //     );
    //     return res;
    // }

    // @Override
    // public RPMPackageMenuVo selectOne(String name) {
    //     QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
    //     wrapper.eq("name", name);
    //     wrapper.select("pkg_id");
    //     wrapper.last("order by rpm_update_at desc limit 1");
    //     List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
    //     if (rpmList.size() == 0) {
    //         return new RPMPackageMenuVo();
    //     }
    //     RPMPackageMenuVo res = RPMPackageConverter.toMenu(rpmList.get(0));
    //     return res;
    // }

    // @Override
    // public List<RPMPackageDetailVo> queryDetailByPkgId(String pkgId) {
    //     QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
    //     wrapper.eq("pkg_id", pkgId);
    //     List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
    //     List<RPMPackageDetailVo> res = RPMPackageConverter.toDetail(rpmList);
    //     return res;
    // }
}
