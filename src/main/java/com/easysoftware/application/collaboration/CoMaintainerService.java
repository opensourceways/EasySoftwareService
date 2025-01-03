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

package com.easysoftware.application.collaboration;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.collaboration.dto.PackageSearchCondition;

public interface CoMaintainerService {
    /**
     * Searches for packages based on the specified search conditions.
     *
     * @param condition The search conditions to filter packages.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryPackages(PackageSearchCondition condition);

    /**
     * query repos and sigs based on condition.
     *
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryRepoSigs();
}
