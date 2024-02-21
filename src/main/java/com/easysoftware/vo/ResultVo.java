package com.easysoftware.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo  implements Serializable {
    private int code;
    private Object msg;
    private Object data;
    private String error;
}
