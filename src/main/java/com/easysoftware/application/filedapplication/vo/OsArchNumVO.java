package com.easysoftware.application.filedapplication.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsArchNumVO {
    /**
     * aarch64.
     */
    private Integer aarch64 = 0;

    /**
     * noarch.
     */
    private Integer noarch = 0;

    /**
     * x86_64.
     */
    @JsonProperty("x86_64")
    private Integer x8664 = 0;

    /**
     * riscv64.
     */
    private Integer riscv64 = 0;

    /**
     * loongarch64.
     */
    private Integer loongarch64 = 0;

    /**
     * sw_64.
     */
    @JsonProperty("sw_64")
    private Integer sw64 = 0;

    /**
     * type.
     */
    private String type;
}
