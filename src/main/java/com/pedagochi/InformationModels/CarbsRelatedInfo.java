package com.pedagochi.informationmodels;

import com.pedagochi.utils.PedagochiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobi on 5/24/2016.
 */
public class CarbsRelatedInfo implements Info {
    private String info_type;
    private String information;
    private String location_context;
    private String time_context;
    private String url;
    private String user_context;
    private String id;
    private Map<String, SeenByUser> seenBy;

    public Map<String, SeenByUser> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(Map<String, SeenByUser> seenBy) {
        this.seenBy = seenBy;
    }

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLocation_context() {
        return location_context;
    }

    public void setLocation_context(String location_context) {
        this.location_context = location_context;
    }

    public String getTime_context() {
        return time_context;
    }

    public void setTime_context(String time_context) {
        this.time_context = time_context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_context() {
        return user_context;
    }

    public void setUser_context(String user_context) {
        this.user_context = user_context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String infoSeenByUsers(){
        PedagochiUtils util = new PedagochiUtils();
        return util.convertMapToString(seenBy);
    }
}
