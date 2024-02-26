/* This project is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 PURPOSE.
 See the Mulan PSL v2 for more details.
 Create: 2024
*/

package com.easysoftware.common.utils;

import java.util.HashMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.entity.ResultMsgVo;
import com.easysoftware.common.entity.ResultVo;

public class ResultUtil {
    public static ResponseEntity<Object> success(HttpStatus status) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(status.getReasonPhrase());
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> success(HttpStatus status, MessageCode msgCode) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()));
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> success(HttpStatus status, HttpHeaders headers, Object data) {
        return new ResponseEntity<>(data, headers, status);
    }

    public static ResponseEntity<Object> success(HttpStatus status, Object data) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(status.getReasonPhrase())
                .setData(data);
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> fail(HttpStatus status, MessageCode msgCode) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()));
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> fail(HttpStatus status, MessageCode msgCode, String error) {
        ResultVo res = new ResultVo()
                .setCode(status.value())
                .setMsg(new ResultMsgVo()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()))
                .setError(error);
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> setResult(HttpStatus status, MessageCode msgCode) {
        HashMap<String, Object> res = new HashMap<>();
        if (status.value() == 200) {
            res.put("code", status.value());
            res.put("msg", status.getReasonPhrase());
            return new ResponseEntity<>(res, status);
        }

        if (msgCode != null) {
            HashMap<String, Object> msgMap = new HashMap<>();
            msgMap.put("code", msgCode.getCode());
            msgMap.put("message_en", msgCode.getMsgEn());
            msgMap.put("message_zh", msgCode.getMsgZh());
            res.put("msg", msgMap);
        }
        return new ResponseEntity<>(res, status);
    }

    public static ResponseEntity<Object> setResult(HttpStatus status, MessageCode msgCode, String error) {
        HashMap<String, Object> res = new HashMap<>();
        if (status.value() == 200) {
            res.put("code", status.value());
            res.put("msg", status.getReasonPhrase());
            res.put("error", error);
            return new ResponseEntity<>(res, status);
        }

        if (msgCode != null) {
            HashMap<String, Object> msgMap = new HashMap<>();
            msgMap.put("code", msgCode.getCode());
            msgMap.put("message_en", msgCode.getMsgEn());
            msgMap.put("message_zh", msgCode.getMsgZh());
            res.put("msg", msgMap);
            res.put("error", error);
        }
        return new ResponseEntity<>(res, status);
    }

/*    public static ResponseEntity<Object> paramError(HttpStatus status, MessageCode msgCode, String parameter) {
        ResultEntity res = new ResultEntity()
                .setCode(status.value())
                .setMsg(new ResultMsgEntity()
                        .setCode(msgCode.getCode())
                        .setMsgEn(msgCode.getMsgEn())
                        .setMsgZh(msgCode.getMsgZh()))
                .setError();
        return new ResponseEntity<>(res, status);
    }*/

    /*public static ResponseEntity<Object> paramError(String parameter) {
        String error = MessageCode.EC0004.getMsgEn() + ": " + parameter;
        return fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004, error);
    }*/
}
