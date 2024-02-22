package com.easysoftware.aop;

import java.time.Instant;

import lombok.Data;

@Data
public class CallMark {
    private Instant lastCallTime;
    private int callCount;

}
