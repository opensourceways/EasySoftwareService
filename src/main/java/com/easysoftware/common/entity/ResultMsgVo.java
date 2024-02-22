package com.easysoftware.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMsgVo  implements Serializable {
    private String code;

    @JsonProperty("message_en")
    private String msgEn;

    @JsonProperty("message_zh")
    private String msgZh;
}
