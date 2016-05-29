package com.pedagochi.user;

import java.util.List;

/**
 * Created by Tobi on 5/25/2016.
 */
public class UserModel {
    private List<String> infoType;
    private List<String> locationContext;
    private List<String> timeContext;
    private List<String> userContext;
    private String userId;

    public List<String> getInfoType() {
        return infoType;
    }

    public void setInfoType(List<String> infoType) {
        this.infoType = infoType;
    }

    public List<String> getLocationContext() {
        return locationContext;
    }

    public void setLocationContext(List<String> locationContext) {
        this.locationContext = locationContext;
    }

    public List<String> getTimeContext() {
        return timeContext;
    }

    public void setTimeContext(List<String> timeContext) {
        this.timeContext = timeContext;
    }

    public List<String> getUserContext() {
        return userContext;
    }

    public void setUserContext(List<String> userContext) {
        this.userContext = userContext;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
