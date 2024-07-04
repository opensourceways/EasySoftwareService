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

package com.easysoftware.infrastructure.archnum;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easysoftware.domain.archnum.ArchNumGateway;
import com.easysoftware.infrastructure.archnum.converter.ArchNumConverter;
import com.easysoftware.infrastructure.archnum.dataobject.ArchNumDO;
import com.easysoftware.infrastructure.mapper.ArchNumDOMapper;

@Component
public class ArchNumGatewayImpl implements ArchNumGateway {
    /**
     * mapper.
     */
    @Autowired
    private ArchNumDOMapper mapper;

    /**
     * converter.
     */
    @Autowired
    private ArchNumConverter converter;

    /**
     * get all table row.
     * @return map.
     */
    @Override
    public Map<String, Map<String, Map<String, Integer>>> getAll() {
        LambdaQueryWrapper<ArchNumDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ArchNumDO::getOs, ArchNumDO::getType, ArchNumDO::getArchName, ArchNumDO::getCount);
        List<ArchNumDO> list = mapper.selectList(wrapper);
        return converter.toMap(list);
    }
}
