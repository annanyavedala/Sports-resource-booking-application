
package com.example.sportsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("resource_id")
    @Expose
    private Integer resourceId;
    @SerializedName("resource_name")
    @Expose
    private String resourceName;
    @SerializedName("resources_available")
    @Expose
    private Integer resourcesAvailable;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourcesAvailable() {
        return resourcesAvailable;
    }

    public void setResourcesAvailable(Integer resourcesAvailable) {
        this.resourcesAvailable = resourcesAvailable;
    }

}
