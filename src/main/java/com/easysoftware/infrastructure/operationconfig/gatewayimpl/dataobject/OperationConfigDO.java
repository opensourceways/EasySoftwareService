package com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("operation_config")
public class OperationConfigDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Categories information.
     */
    private String categorys;

    /**
     * Order index information.
     */
    private String orderIndex;

    /**
     * Recommendation status.
     */
    private String recommend;

    /**
     * Type of the entity.
     */
    private String type;

}
