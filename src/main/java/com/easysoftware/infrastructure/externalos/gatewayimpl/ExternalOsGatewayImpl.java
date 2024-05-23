package com.easysoftware.infrastructure.externalos.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;
import com.easysoftware.domain.externalos.gateway.ExternalOsGateway;
import com.easysoftware.infrastructure.externalos.gatewayimpl.converter.ExternalOsConverter;
import com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject.ExternalOsDO;
import com.easysoftware.infrastructure.mapper.ExternalOsDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExternalOsGatewayImpl implements ExternalOsGateway {

    /**
     * Autowired ExternalOsDOMapper for database operations.
     */
    @Autowired
    private ExternalOsDOMapper externalOsDOMapper;

    /**
     * Query package mapping based on the provided search condition.
     *
     * @param condition The search condition for querying package mappings
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryPkgMap(final ExternalOsSearchCondiiton condition) {
        Page<ExternalOsDO> page = createPage(condition);
        QueryWrapper<ExternalOsDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ExternalOsDO(), condition, "");
        IPage<ExternalOsDO> resPage = externalOsDOMapper.selectPage(page, wrapper);
        List<ExternalOsDO> resList = resPage.getRecords();
        List<ExternalOs> exs = ExternalOsConverter.toEntity(resList);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", exs)
        );
        return res;
    }


    /**
     * Creates a Page of ExternalOsDO based on the provided search condition.
     *
     * @param condition The ExternalOsSearchCondition object to create the page from.
     * @return A Page of ExternalOsDO entities.
     */
    private Page<ExternalOsDO> createPage(final ExternalOsSearchCondiiton condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ExternalOsDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    /**
     * Check if an external operating system exists based on its unique identifier.
     *
     * @param uni The unique identifier of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    @Override
    public boolean existExternalOs(final ExternalOsUnique uni) {
        QueryWrapper<ExternalOsDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ExternalOsDO(), uni, "");
        return externalOsDOMapper.exists(wrapper);
    }


    /**
     * Check if an external operating system exists based on its ID.
     *
     * @param id The ID of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    @Override
    public boolean existExternalOs(final String id) {
        QueryWrapper<ExternalOsDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return externalOsDOMapper.exists(wrapper);
    }
}
