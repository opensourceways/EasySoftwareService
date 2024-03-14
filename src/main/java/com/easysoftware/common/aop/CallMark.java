package com.easysoftware.common.aop;

import java.time.Instant;

import lombok.Data;

@Data
public class CallMark {
    private Instant lastCallTime;
    private int callCount;

}
