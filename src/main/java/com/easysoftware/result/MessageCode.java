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

package com.easysoftware.result;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MessageCode {
    // client exception
    EC0001("EC0001", "Request Error", "请求异常"),
    EC0005("EC0002", "Wrong parameter", "参数错误"),
    EC0009("EC0003", "Unsupported community", "不支持该community"),

    // self service exception
    ES0001("ES0001", "Internal Server Error", "服务异常");


    private final String code;
    private final String msgEn;
    private final String msgZh;

    MessageCode(String code, String msgEn, String msgZh) {
        this.code = code;
        this.msgEn = msgEn;
        this.msgZh = msgZh;
    }

    public String getCode() {
        return code;
    }

    public String getMsgEn() {
        return msgEn;
    }

    public String getMsgZh() {
        return msgZh;
    }

    public static final Map<String, MessageCode> msgCodeMap = Arrays.stream(MessageCode.values())
            .collect(Collectors.toMap(MessageCode::getCode, e -> e));
}
