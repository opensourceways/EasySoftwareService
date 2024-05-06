package com.easysoftware.common.aop;

import lombok.Data;

import java.time.Instant;

@Data
public class CallMark {
    /**
     * Timestamp of the last call time.
     */
    private Instant lastCallTime;

    /**
     * Number of calls made.
     */
    private int callCount;


}
