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

package com.easysoftware.infrastructure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;
import com.easysoftware.infrastructure.fieldpkg.dataobject.FieldPkgDO;

public interface FieldPkgDOMapper extends BaseMapper<FieldPkgDO> {
    /**
     * process apply if apply status is OPEN.
     *
     * @param pageSize pageSize.
     * @param offset offset.
     * @param condition condition.
     * @return List<FieldPkgDO> result
     */
    List<FieldPkgDO> selectCustomPageByCondition(@Param("pageSize") int pageSize,
    @Param("offset") int offset, @Param("condition") FieldPkgSearchCondition condition);
}
