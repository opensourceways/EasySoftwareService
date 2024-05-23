package com.easysoftware.common.aop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementLog {
    /**
     * Type of the log event.
     */
    private String type;

    /**
     * Time of the log event.
     */
    private String time;

    /**
     * Function associated with the log event.
     */
    private String func;

    /**
     * Details of the event.
     */
    private String eventDetails;

    /**
     * URL of the request.
     */
    private String requestUrl;

    /**
     * HTTP method used in the request.
     */
    private String method;

    /**
     * IP address of the application.
     */
    private String appIP;

    /**
     * HTTP status code of the response.
     */
    private int status;

    /**
     * Message related to the event.
     */
    private String message;

    /**
     * Error log information.
     */
    private String errorLog;

    /**
     * Operator associated with the event.
     */
    private String operator;

}

