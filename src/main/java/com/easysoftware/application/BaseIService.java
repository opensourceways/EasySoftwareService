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

package com.easysoftware.application;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

public interface BaseIService<T> extends IService<T> {
    /**
     * Saves a batch of data objects.
     *
     * @param dataObject An ArrayList containing the data objects to be saved.
     */
    void saveDataObjectBatch(ArrayList<String> dataObject);
}
