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

package com.easysoftware.infrastructure.oepkg.gatewalmpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(PackageConstant.OEPKG_TABLE)
public class OepkgDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private String id;

    /**
     * Timestamp for creation.
     */
    private Timestamp createAt;

    /**
     * Timestamp for update.
     */
    private Timestamp updateAt;

    /**
     * Name of the entity.
     */
    private String name;

    /**
     * Version information.
     */
    private String version;

    /**
     * Operating system information.
     */
    private String os;

    /**
     * Architecture information.
     */
    private String arch;

    /**
     * Category of the entity.
     */
    private String category;

    /**
     * Timestamp for RPM package update.
     */
    private String rpmUpdateAt;

    /**
     * Source repository information.
     */
    private String srcRepo;

    /**
     * Size of the RPM package.
     */
    private String rpmSize;

    /**
     * Binary download URL.
     */
    private String binDownloadUrl;

    /**
     * Source download URL.
     */
    private String srcDownloadUrl;

    /**
     * Summary of the entity.
     */
    private String summary;

    /**
     * Operating system support information.
     */
    private String osSupport;

    /**
     * Repository information.
     */
    private String repo;

    /**
     * Type of the repository.
     */
    private String repoType;

    /**
     * Installation instructions.
     */
    private String installation;

    /**
     * Description of the entity.
     */
    private String description;

    /**
     * Requirements information.
     */
    private String requires;

    /**
     * Provides information.
     */
    private String provides;

    /**
     * Conflicts information.
     */
    private String conflicts;

    /**
     * Change log information.
     */
    private String changeLog;

    /**
     * Maintainer ID.
     */
    private String maintainerId;

    /**
     * Maintainer email.
     */
    private String maintainerEmail;

    /**
     * Maintainer Gitee ID.
     */
    private String maintainerGiteeId;

    /**
     * Timestamp for maintainer update.
     */
    private String maintainerUpdateAt;

    /**
     * Maintainer status information.
     */
    private String maintainerStatus;

    /**
     * Upstream information.
     */
    private String upStream;

    /**
     * Security information.
     */
    private String security;

    /**
     * Similar packages information.
     */
    private String similarPkgs;

    /**
     * Download count information.
     */
    private String downloadCount;

    /**
     * Package ID.
     */
    @TableId
    private String pkgId;

    /**
     * Sub-path information.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;

    /**
     * number of selected pkgs.
     */
    @TableField(value = "count(*)", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Integer count;

    /**
     * get an OepkgDO entity createAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getCreateAt() {
        if (this.createAt != null) {
            return (Timestamp) this.createAt.clone();
        } else {
            return null;
        }
    }

    /**
     * get an OepkgDO entity updateAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getUpdateAt() {
        if (this.updateAt != null) {
            return (Timestamp) this.updateAt.clone();
        } else {
            return null;
        }
    }

    /**
     * set an OepkgDO entity createAt field value.
     *
     * @param createAt The OepkgDO entity createAt field for set
     */
    public void setCreateAt(Timestamp createAt) {
        if (this.createAt != null) {
            this.createAt = (Timestamp) createAt.clone();
        } else {
            this.createAt = null;
        }
    }

    /**
     * set an OepkgDO entity updateAt field value.
     *
     * @param updateAt The OepkgDO entity updateAt field for set
     */
    public void setUpdateAt(Timestamp updateAt) {
        if (this.updateAt != null) {
            this.updateAt = (Timestamp) updateAt.clone();
        } else {
            this.updateAt = null;
        }
    }
}
