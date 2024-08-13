package com.easysoftware.infrastructure.apply.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.sql.Timestamp;

@Getter
@Setter
@TableName(PackageConstant.APPLY_HANDLE_RECORDS)
public class ApplyhandleRecordsDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the entity (ID: name).
     */
    @TableId(value = "id")
    private Long id;

    /**
     * create time.
     */

    private Timestamp createdAt;

    /**
     * apply Id.
     */
    private Long applyId;

    /**
     * recordId.
     */
    private Long recordId;

    /**
     * comment.
     */
    private String comment;

    /**
     * maintainer.
     */
    private String maintainer;

    /**
     * administrator.
     */
    private String administrator;

    /**
     * applyStatus.
     */
    private Byte applyStatus;


    /**
     * get an ApplyhandleRecordsDO entity createdAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getCreatedAt() {
        if (this.createdAt != null) {
            return (Timestamp) this.createdAt.clone();
        } else {
            return null;
        }
    }


    /**
     * set an ApplyhandleRecordsDO entity createAt field value.
     *
     * @param createAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setCreatedAt(Timestamp createAt) {
        if (createAt != null) {
            this.createdAt = (Timestamp) createAt.clone();
        } else {
            this.createdAt = null;
        }
    }
}
