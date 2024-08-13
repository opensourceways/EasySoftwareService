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
    private String applyId;

    /**
     * recordId.
     */
    private Long recordId;

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
     * updateAt.
     */
    private Timestamp updateAt;
}
