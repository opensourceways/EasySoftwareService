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

package com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(PackageConstant.DOMAIN_PKG_TABLE)
public class FieldApplicationDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private String os;

    /**
     * Architecture information.
     */
    private String arch;

    /**
     * Name of the entity.
     */
    private String name;

    /**
     * Version information.
     */
    private String version;

    /**
     * Category of the entity.
     */
    private String category;

    /**
     * URL for the icon.
     */
    private String iconUrl;

    /**
     * Tags associated with the entity.
     */
    private String tags;

    /**
     * Package IDs related to the entity.
     */
    private String pkgIds;

    /**
     * Description of the entity.
     */
    private String description;

    /**
     * maintainers.
     */
    private String maintainers;

}
