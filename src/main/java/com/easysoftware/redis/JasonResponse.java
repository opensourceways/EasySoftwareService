package com.easysoftware.redis;

import com.fasterxml.jackson.annotation.JsonProperty;  
import java.util.List;  
import java.util.Map;  

public class JasonResponse {
        private int total;  
    private List<Category> list;  
  
    // Getter and setter methods  
    @JsonProperty("total")  
    public int getTotal() {  
        return total;  
    }  
  
    public void setTotal(int total) {  
        this.total = total;  
    }  
  
    @JsonProperty("list")  
    public List<Category> getList() {  
        return list;  
    }  
  
    public void setList(List<Category> list) {  
        this.list = list;  
    }  
  
    // Inner class for each item in the list  
    public static class Category {  
        private List<Child> children;  
        private String name;  
  
        // Getter and setter methods  
        @JsonProperty("children")  
        public List<Child> getChildren() {  
            return children;  
        }  
  
        public void setChildren(List<Child> children) {  
            this.children = children;  
        }  
  
        @JsonProperty("name")  
        public String getName() {  
            return name;  
        }  
  
        public void setName(String name) {  
            this.name = name;  
        }  
    }  
  
    // Inner class for each child in the children list  
    public static class Child {  
        private String category;  
        private String name;  
        private String description;  
        private String iconUrl;  
        private List<String> tags;  
        private Map<String, String> pkgIds;  
  
        // Getter and setter methods  
        @JsonProperty("category")  
        public String getCategory() {  
            return category;  
        }  
  
        public void setCategory(String category) {  
            this.category = category;  
        }  
  
        @JsonProperty("name")  
        public String getName() {  
            return name;  
        }  
  
        public void setName(String name) {  
            this.name = name;  
        }  
  
        @JsonProperty("description")  
        public String getDescription() {  
            return description;  
        }  
  
        public void setDescription(String description) {  
            this.description = description;  
        }  
  
        @JsonProperty("iconUrl")  
        public String getIconUrl() {  
            return iconUrl;  
        }  
  
        public void setIconUrl(String iconUrl) {  
            this.iconUrl = iconUrl;  
        }  
  
        @JsonProperty("tags")  
        public List<String> getTags() {  
            return tags;  
        }  
  
        public void setTags(List<String> tags) {  
            this.tags = tags;  
        }  
  
        @JsonProperty("pkgIds")  
        public Map<String, String> getPkgIds() {  
            return pkgIds;  
        }  
  
        public void setPkgIds(Map<String, String> pkgIds) {  
            this.pkgIds = pkgIds;  
        }  
    } 
}
