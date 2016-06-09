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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Tobi on 5/27/2016.
 */
public class InformationSelector {
    private final Logger log = LoggerFactory.getLogger(InformationSelector.class);

    private IndexSearcher indexSearcher;
    public InformationSelector(IndexSearcher indexSearcher){
        this.indexSearcher = indexSearcher;
    }

//    public Info returnUnseenDocuent(HashMap<String,SeenDocument> seenDocumentHashMap, List<ScoreDoc> documents, String infoType) throws IOException {
//        Info info = null;
//        for (ScoreDoc document : documents) {
//            int docId = document.doc;
//            Document resolvedDocument = indexSearcher.doc(docId);
//            if (checkIfDocumentSeen(resolvedDocument,seenDocumentHashMap) != true)  {
//                info = classInstantiator(infoType);
//                buildInfo(info,resolvedDocument);
//                break;
//            }
//
//
//            log.info("Score :"+document.score);
//            log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
//            log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
//            log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
//            log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
//            log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
//            log.info("Seen By: "+resolvedDocument.get(LuceneConstants.SEEN_BY));
//        }
//
//        return info;
//    }

    public List<Info> returnDocListForClassification(HashMap<String,SeenDocument> seenDocumentHashMap, List<ScoreDoc> documents, String infoType) throws IOException {
        //Info info = null;
        List<Info> unseenList = new ArrayList<Info>();
//        ScoreDoc topDocument = documents.get(0);
//        float topScore = topDocument.score;
        for (ScoreDoc scoreDoc : documents) {
            int docId = scoreDoc.doc;
            Document resolvedDocument = indexSearcher.doc(docId);
            if (checkIfDocumentSeen(resolvedDocument,seenDocumentHashMap) != true)  {
                Info info = classInstantiator(infoType);
                info = buildInfo(info,resolvedDocument,scoreDoc);
                unseenList.add(info);
            }


            log.info("Score :"+scoreDoc.score);
            log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
            log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
            log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
            log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
            log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
            log.info("Seen By: "+resolvedDocument.get(LuceneConstants.SEEN_BY));
        }
        float topScore = unseenList.get(0).getLuceneScore();
        List<Info> resultList = returnDocsWithMatchingScores(unseenList,topScore);

        return resultList;
    }

    private List<Info> returnDocsWithMatchingScores(List<Info> infoList, float topScore){
        List<Info> resultList = infoList.stream()
                .filter(info -> info.getLuceneScore() == topScore)
                .collect(Collectors.toList());
        return resultList;
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

//    private void buildInfo(Info info, Document document){
//
//        info.setInfo_type(document.get(LuceneConstants.INFO_TYPE));
////        info.setUser_context(document.get(LuceneConstants.USER_CONTEXT));
////        info.setLocation_context(document.get(LuceneConstants.LOCATION_CONTEXT));
//        info.setId(document.get(LuceneConstants.ID));
//        info.setInformation(document.get(LuceneConstants.INFORMATION));
//        info.setUrl(document.get(LuceneConstants.URL));
//        HashMap<String, Object> timestampNow = new HashMap<String, Object>();
//        timestampNow.put("timestamp",ServerValue.TIMESTAMP);
//        info.setTimestampCreated(timestampNow);
//    }

    private Info buildInfo(Info info, Document document, ScoreDoc scoreDoc){

        info.setInfo_type(document.get(LuceneConstants.INFO_TYPE));
//        info.setUser_context(document.get(LuceneConstants.USER_CONTEXT));
//        info.setLocation_context(document.get(LuceneConstants.LOCATION_CONTEXT));
        info.setId(document.get(LuceneConstants.ID));
        info.setInformation(document.get(LuceneConstants.INFORMATION));
        info.setUrl(document.get(LuceneConstants.URL));
        HashMap<String, Object> timestampNow = new HashMap<String, Object>();
        timestampNow.put("timestamp",ServerValue.TIMESTAMP);
        info.setTimestampCreated(timestampNow);
        info.setLuceneScore(scoreDoc.score);

        return info;
    }

}
