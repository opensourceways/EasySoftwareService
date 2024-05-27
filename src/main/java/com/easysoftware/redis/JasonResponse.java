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

package com.easysoftware.redis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class JasonResponse {
    /**
     * Total count.
     */
    private int total;

    /**
     * List of Category objects.
     */
    private List<Category> list;


    /**
     * Get the total count.
     *
     * @return The total count.
     */
    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    /**
     * Set the total count.
     *
     * @param total The total count to set.
     */
    public void setTotal(final int total) {
        this.total = total;
    }

    /**
     * Get the list of Category objects.
     *
     * @return The list of Category objects.
     */
    @JsonProperty("list")
    public List<Category> getList() {
        return list;
    }

    /**
     * Set the list of Category objects.
     *
     * @param list The list of Category objects to set.
     */
    public void setList(final List<Category> list) {
        this.list = list;
    }


    // Inner class for each item in the list
    public static class Category {
        /**
         * List of Child objects.
         */
        private List<Child> children;

        /**
         * Name string.
         */
        private String name;


        // Getter and setter methods

        /**
         * Get the list of Child objects.
         *
         * @return The list of Child objects.
         */
        @JsonProperty("children")
        public List<Child> getChildren() {
            return children;
        }

        /**
         * Set the list of Child objects.
         *
         * @param children The list of Child objects to set.
         */
        public void setChildren(final List<Child> children) {
            this.children = children;
        }

        /**
         * Get the name string.
         *
         * @return The name string.
         */
        @JsonProperty("name")
        public String getName() {
            return name;
        }

        /**
         * Set the name string.
         *
         * @param name The name string to set.
         */
        public void setName(final String name) {
            this.name = name;
        }

    }

    // Inner class for each child in the children list
    public static class Child {
        /**
         * Category string.
         */
        private String category;

        /**
         * Name string.
         */
        private String name;

        /**
         * Description string.
         */
        private String description;

        /**
         * URL for the icon.
         */
        private String iconUrl;

        /**
         * List of tags.
         */
        private List<String> tags;

        /**
         * Map of package IDs.
         */
        private Map<String, String> pkgIds;


        // Getter and setter methods

        /**
         * Get the category string.
         *
         * @return The category string.
         */
        @JsonProperty("category")
        public String getCategory() {
            return category;
        }

        /**
         * Set the category string.
         *
         * @param category The category string to set.
         */
        public void setCategory(final String category) {
            this.category = category;
        }

        /**
         * Get the name string.
         *
         * @return The name string.
         */
        @JsonProperty("name")
        public String getName() {
            return name;
        }

        /**
         * Set the name string.
         *
         * @param name The name string to set.
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Get the description string.
         *
         * @return The description string.
         */
        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        /**
         * Set the description string.
         *
         * @param description The description string to set.
         */
        public void setDescription(final String description) {
            this.description = description;
        }

        /**
         * Get the icon URL.
         *
         * @return The icon URL.
         */
        @JsonProperty("iconUrl")
        public String getIconUrl() {
            return iconUrl;
        }

        /**
         * Set the icon URL.
         *
         * @param iconUrl The icon URL to set.
         */
        public void setIconUrl(final String iconUrl) {
            this.iconUrl = iconUrl;
        }

        /**
         * Get the list of tags.
         *
         * @return The list of tags.
         */
        @JsonProperty("tags")
        public List<String> getTags() {
            return tags;
        }

        /**
         * Set the list of tags.
         *
         * @param tags The list of tags to set.
         */
        public void setTags(final List<String> tags) {
            this.tags = tags;
        }

        /**
         * Get the package IDs map.
         *
         * @return The package IDs map.
         */
        @JsonProperty("pkgIds")
        public Map<String, String> getPkgIds() {
            return pkgIds;
        }

        /**
         * Set the package IDs map.
         *
         * @param pkgIds The package IDs map to set.
         */
        public void setPkgIds(final Map<String, String> pkgIds) {
            this.pkgIds = pkgIds;
        }

    }
}
