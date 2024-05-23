package com.easysoftware.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo {
    /**
     * Error code.
     */
    private int code;

    /**
     * Error message.
     */
    private Object msg;

    /**
     * Data associated with the error.
     */
    private Object data;

    /**
     * Description of the error.
     */
    private String error;

}
