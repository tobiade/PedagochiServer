package com.pedagochi.utils;

import com.pedagochi.informationmodels.SeenByUser;
import com.pedagochi.informationmodels.SeenDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tobi on 5/26/2016.
 */

public class PedagochiUtils {
    private final Logger log = LoggerFactory.getLogger(PedagochiUtils.class);

    public String convertMapToString(Map<String, SeenByUser> seenBy){
        StringBuilder seenByUsersString = new StringBuilder();
        if (seenBy != null) {
            List<SeenByUser> seenByUserList = new ArrayList<SeenByUser>(seenBy.values());
            for (SeenByUser user : seenByUserList) {
                log.info("Seen by user: "+user.getUserId());
                seenByUsersString.append(user.getUserId());
                seenByUsersString.append(",");

            }
            log.info("Generated seenBy string: "+seenByUsersString.substring(0, seenByUsersString.length() - 1));
        }
        return  seenByUsersString.length() > 0 ? seenByUsersString.substring(0, seenByUsersString.length() - 1) : "";
    }

    public static HashMap<String, SeenDocument> replaceHashMapKeysWithDocId(HashMap<String, SeenDocument> seenDocumentHashMap){
        HashMap<String, SeenDocument> newMap = new HashMap<String, SeenDocument>();
//        Iterator<HashMap.Entry<String, SeenDocument>> iterator = seenDocumentHashMap.entrySet().iterator() ;
//        while(iterator.hasNext()){
//            HashMap.Entry<String, SeenDocument> entry = iterator.next();
//            String newKey = entry.getValue().getId();
//            newMap.put(newKey, entry.getValue());
//
//
//        }
        for (Map.Entry<String, SeenDocument> entry : seenDocumentHashMap.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
            String newKey = entry.getValue().getId();
            newMap.put(newKey, entry.getValue());
            // ...
        }
        return newMap;
    }
}
