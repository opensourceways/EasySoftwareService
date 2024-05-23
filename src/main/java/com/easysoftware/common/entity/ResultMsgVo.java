package com.easysoftware.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMsgVo {
    /**
     * Error code.
     */
    private String code;

    /**
     * Error message in English.
     */
    @JsonProperty("message_en")
    private String msgEn;

    /**
     * Error message in Chinese.
     */
    @JsonProperty("message_zh")
    private String msgZh;

}
