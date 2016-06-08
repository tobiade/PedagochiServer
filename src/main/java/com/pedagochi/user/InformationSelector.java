package com.pedagochi.user;

import com.google.firebase.database.ServerValue;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.infomodels.*;
import com.pedagochi.lucene.LuceneConstants;
import com.pedagochi.utils.PedagochiUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobi on 5/27/2016.
 */
public class InformationSelector {
    private final Logger log = LoggerFactory.getLogger(InformationSelector.class);

    private IndexSearcher indexSearcher;
    public InformationSelector(IndexSearcher indexSearcher){
        this.indexSearcher = indexSearcher;
    }

    public Info returnUnseenDocuent(HashMap<String,SeenDocument> seenDocumentHashMap, List<ScoreDoc> documents, String infoType) throws IOException {
        Info info = null;
        for (ScoreDoc document : documents) {
            int docId = document.doc;
            Document resolvedDocument = indexSearcher.doc(docId);
            if (checkIfDocumentSeen(resolvedDocument,seenDocumentHashMap) != true)  {
                info = classInstantiator(infoType);
                buildInfo(info,resolvedDocument);
                break;
            }


            log.info("Score :"+document.score);
            log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
            log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
            log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
            log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
            log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
            log.info("Seen By: "+resolvedDocument.get(LuceneConstants.SEEN_BY));
        }

        return info;
    }

    private boolean checkIfDocumentSeen(Document document, HashMap<String,SeenDocument> seenDocumentHashMap){
        boolean isDocumentPresent = false;
        if (seenDocumentHashMap != null){
            String firebaseDocId = document.get(LuceneConstants.ID);
//            for (Map.Entry<String, SeenDocument> entry : seenDocumentHashMap.entrySet()) {
//                log.info(entry.getKey()+" : "+entry.getValue());
//            }
            //HashMap<String,SeenDocument> newMap = PedagochiUtils.replaceHashMapKeysWithDocId(seenDocumentHashMap);
            if (seenDocumentHashMap.containsKey(firebaseDocId)){
                isDocumentPresent = true;
                log.info("entered condition");
            }
        }
        return  isDocumentPresent;
    }

    private Info classInstantiator(String infoType){
        Info info;
        if (infoType == FirebaseDataService.FirebaseNode.CARBS_RELATED.toString()){
            info = new CarbsRelatedInfo();
        }
        else if (infoType == FirebaseDataService.FirebaseNode.EXERCISE_RELATED.toString()){
            info = new ExerciseRelatedInfo();
        }
        else{
            info = new GeneralInfo();
        }
        return info;

    }

    private void buildInfo(Info info, Document document){

        info.setInfo_type(document.get(LuceneConstants.INFO_TYPE));
//        info.setUser_context(document.get(LuceneConstants.USER_CONTEXT));
//        info.setLocation_context(document.get(LuceneConstants.LOCATION_CONTEXT));
        info.setId(document.get(LuceneConstants.ID));
        info.setInformation(document.get(LuceneConstants.INFORMATION));
        info.setUrl(document.get(LuceneConstants.URL));
        HashMap<String, Object> timestampNow = new HashMap<String, Object>();
        timestampNow.put("timestamp",ServerValue.TIMESTAMP);
        info.setTimestampCreated(timestampNow);
    }

}
