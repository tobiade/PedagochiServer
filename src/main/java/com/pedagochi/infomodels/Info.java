package com.pedagochi.infomodels;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tobi on 5/25/2016.
 */
public interface Info {
     String getInfo_type();

     void setInfo_type(String info_type);

     String getInformation();

     void setInformation(String information);

     String getLocation_context();

     void setLocation_context(String location_context);

     String getTime_context();

     void setTime_context(String time_context);

     String getUrl();

     void setUrl(String url);

     String getUser_context();

     void setUser_context(String user_context);

     String getId();

     void setId(String id);

     Map<String, SeenByUser> getSeenBy();

     void setSeenBy(Map<String, SeenByUser> seenBy);

     String infoSeenByUsers();

     HashMap<String, Object> getTimestampCreated();

     void setTimestampCreated(HashMap<String, Object> timestampCreated);

}
