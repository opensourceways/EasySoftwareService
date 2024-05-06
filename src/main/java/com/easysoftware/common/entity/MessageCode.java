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

package com.easysoftware.common.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MessageCode {
    // client exception
    /**
     * Error code EC0001: Request Error.
     * Chinese: 请求异常.
     */
    EC0001("EC0001", "Request Error", "请求异常"),

    /**
     * Error code EC0002: Wrong parameter.
     * Chinese: 参数错误.
     */
    EC0002("EC0002", "Wrong parameter", "参数错误"),

    /**
     * Error code EC0003: Unsupported community.
     * Chinese: 不支持该community.
     */
    EC0003("EC0003", "Unsupported community", "不支持该community"),

    /**
     * Error code EC0004: Update failed.
     * Chinese: 更新失败.
     */
    EC0004("EC0004", "Update failed", "更新失败"),

    /**
     * Error code EC0005: Delete failed.
     * Chinese: 删除失败.
     */
    EC0005("EC0005", "Delete failed", "删除失败"),

    /**
     * Error code EC0006: Insert failed.
     * Chinese: 插入失败.
     */
    EC0006("EC0006", "Insert failed", "插入失败"),

    /**
     * Error code EC0007: Query failed.
     * Chinese: 查找失败.
     */
    EC0007("EC0007", "query failed", "查找失败"),

    /**
     * Error code EC0008: Item existed.
     * Chinese: 项目已存在.
     */
    EC0008("EC0008", "Item existed", "项目已存在"),

    /**
     * Error code EC0009: Item not existed.
     * Chinese: 项目不存在.
     */
    EC0009("EC0009", "Item not existed", "项目不存在"),

    /**
     * Error code EC00010: Request exceeds the limit.
     * Chinese: 请求超过限制.
     */
    EC00010("EC00010", "Request exceeds the limit", "请求超过限制"),

    /**
     * Error code EC00011: Failed to retrieve field using reflection.
     * Chinese: 无法通过反射获取字段.
     */
    EC00011("EC00011", "Failed to retrieve field using reflection", "无法通过反射获取字段"),

    /**
     * Error code EC00012: Unauthorized.
     * Chinese: 身份认证失败.
     */
    EC00012("EC00012", "Unauthorized", "身份认证失败"),

    /**
     * Error code EC00013: Unable to connect to database.
     * Chinese: 无法连接数据库.
     */
    EC00013("EC00013", "Unable to connect to database", "无法连接数据库"),

    /**
     * Error code EC00014: Param error.
     * Chinese: 参数错误.
     * Detailed message format: 参数错误, 详细信息:%s.
     */
    EC00014("EC00014", "Param error, detailed:%s", "参数错误, 详细信息:%s"),

    /**
     * Error code EC00015: Connect to Redis failed.
     * Chinese: 无法连接redis.
     */
    EC00015("EC00015", "Connect to Redis failed", "无法连接redis"),

    /**
     * Error code EC00016: Redis key does't exist, delete failed.
     * Chinese: key不存在，删除key错误.
     */
    EC00016("EC00016", "Redis key does't exsist, delete failed", "key不存在，删除key错误"),

    /**
     * Error code EC00017: Redis key delete failed.
     * Chinese: key删除错误.
     */
    EC00017("EC00017", "Redis key delete failed", "key删除错误"),
    /**
     * Error code ES0001: Internal Server Error.
     * Chinese: 服务异常.
     */
    // self service exception
    ES0001("ES0001", "Internal Server Error", "服务异常");


    /**
     * Error code.
     */
    private final String code;

    /**
     * Error message in English.
     */
    private final String msgEn;

    /**
     * Error message in Chinese.
     */
    private final String msgZh;


    /**
     * Constructor for MessageCode enum.
     *
     * @param code  Error code
     * @param msgEn Error message in English
     * @param msgZh Error message in Chinese
     */
    MessageCode(final String code, final String msgEn, final String msgZh) {
        this.code = code;
        this.msgEn = msgEn;
        this.msgZh = msgZh;
    }

    /**
     * Get the error code.
     *
     * @return The error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the error message in English.
     *
     * @return The error message in English
     */
    public String getMsgEn() {
        return msgEn;
    }

    /**
     * Get the error message in Chinese.
     *
     * @return The error message in Chinese
     */
    public String getMsgZh() {
        return msgZh;
    }

    /**
     * Map to store error codes and their corresponding MessageCode objects.
     */
    public static final Map<String, MessageCode> MSG_CODE_MAP = Arrays.stream(MessageCode.values())
            .collect(Collectors.toMap(MessageCode::getCode, e -> e));

}
