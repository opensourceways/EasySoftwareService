/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.common.utils;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.entity.ResultMsgVo;
import com.easysoftware.common.entity.ResultVo;

import org.apache.commons.text.StringEscapeUtils;
import org.owasp.esapi.codecs.HTMLEntityCodec;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public final class ResultUtil {

    // Private constructor to prevent instantiation of the utility class
    private ResultUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("ResultUtil class cannot be instantiated.");
    }

    /**
     * Create a ResponseEntity representing a successful response with the specified HTTP status.
     *
     * @param status The HTTP status for the response
     * @return A ResponseEntity object representing success
     */
    public static ResponseEntity<Object> success(HttpStatus status) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(status.getReasonPhrase());
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity representing a successful response with the specified HTTP status and message code.
     *
     * @param status  The HTTP status for the response
     * @param msgCode The message code associated with the success response
     * @return A ResponseEntity object representing success
     */
    public static ResponseEntity<Object> success(HttpStatus status, MessageCode msgCode) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()));
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity representing a successful response with the specified HTTP status, headers, and data.
     *
     * @param status  The HTTP status for the response
     * @param headers HttpHeaders to include in the response
     * @param data    The data to include in the response
     * @return A ResponseEntity object representing success
     */
    public static ResponseEntity<Object> success(HttpStatus status, HttpHeaders headers, Object data) {
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(data));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, headers, status);
    }

    /**
     * Create a ResponseEntity representing a successful response with the specified HTTP status and data.
     *
     * @param status The HTTP status for the response
     * @param data   The data to include in the response
     * @return A ResponseEntity object representing success
     */
    public static ResponseEntity<Object> success(HttpStatus status, Object data) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(status.getReasonPhrase())
                .setData(data);
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity representing a failure response with the specified HTTP status and message code.
     *
     * @param status  The HTTP status for the response
     * @param msg The msg
     * @return A ResponseEntity object representing failure
     */
    public static ResponseEntity<Object> fail(HttpStatus status, String msg) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(status.getReasonPhrase())
                .setError(msg);
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

        /**
     * Create a ResponseEntity representing a failure response with the specified HTTP status and message code.
     *
     * @param status  The HTTP status for the response
     * @param msgCode The message code associated with the failure response
     * @return A ResponseEntity object representing failure
     */
    public static ResponseEntity<Object> fail(HttpStatus status, MessageCode msgCode) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()));
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity representing a failure response with the specified HTTP status,
     * message code, and error message.
     *
     * @param status  The HTTP status for the response
     * @param msgCode The message code associated with the failure response
     * @param error   The error message
     * @return A ResponseEntity object representing failure
     */
    public static ResponseEntity<Object> fail(HttpStatus status, MessageCode msgCode, String error) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()))
                .setError(error);
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity with the specified HTTP status and message code.
     *
     * @param status  The HTTP status for the response
     * @param msgCode The message code associated with the response
     * @return A ResponseEntity object
     */
    public static ResponseEntity<Object> setResult(HttpStatus status, MessageCode msgCode) {
        HashMap<String, Object> res = new HashMap<>();
        if (status.value() == 200) {
            res.put("code", status.value());
            res.put("msg", status.getReasonPhrase());
            HTMLEntityCodec codec = new HTMLEntityCodec();
            String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
            String s2 = codec.decode(s1);
            return new ResponseEntity<>(s2, status);
        }

        if (msgCode != null) {
            HashMap<String, Object> msgMap = new HashMap<>();
            msgMap.put("code", msgCode.getCode());
            msgMap.put("message_en", msgCode.getMsgEn());
            msgMap.put("message_zh", msgCode.getMsgZh());
            res.put("msg", msgMap);
        }
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }

    /**
     * Create a ResponseEntity with the specified HTTP status, message code, and error message.
     *
     * @param status  The HTTP status for the response
     * @param msgCode The message code associated with the response
     * @param error   The error message
     * @return A ResponseEntity object
     */
    public static ResponseEntity<Object> setResult(HttpStatus status, MessageCode msgCode, String error) {
        HashMap<String, Object> res = new HashMap<>();
        if (status.value() == 200) {
            res.put("code", status.value());
            res.put("msg", status.getReasonPhrase());
            res.put("error", error);
            HTMLEntityCodec codec = new HTMLEntityCodec();
            String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
            String s2 = codec.decode(s1);
            return new ResponseEntity<>(s2, status);
        }

        if (msgCode != null) {
            HashMap<String, Object> msgMap = new HashMap<>();
            msgMap.put("code", msgCode.getCode());
            msgMap.put("message_en", msgCode.getMsgEn());
            msgMap.put("message_zh", msgCode.getMsgZh());
            res.put("msg", msgMap);
            res.put("error", error);
        }
        HTMLEntityCodec codec = new HTMLEntityCodec();
        String s1 = StringEscapeUtils.escapeHtml4(ObjectMapperUtil.writeValueAsString(res));
        String s2 = codec.decode(s1);
        return new ResponseEntity<>(s2, status);
    }
}
