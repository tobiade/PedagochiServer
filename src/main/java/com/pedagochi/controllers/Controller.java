package com.pedagochi.controllers;

import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.informationmodels.CarbsRelatedInfo;
import com.pedagochi.informationmodels.Info;
import com.pedagochi.informationmodels.SeenDocument;
import com.pedagochi.lucene.ContextMatcher;
import com.pedagochi.lucene.ContextQueryBuilder;
import com.pedagochi.lucene.LuceneConstants;
import com.pedagochi.user.InformationSelector;
import com.pedagochi.user.UserModel;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.jdeferred.DoneCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
@RestController
public class Controller {
    @Autowired
    private ContextQueryBuilder contextQueryBuilder;
    @Autowired
    private ContextMatcher contextMatcher;
    @Autowired
    private FirebaseDataService dataService;
    @Autowired
    private InformationSelector informationSelector;




    private final Logger log = LoggerFactory.getLogger(Controller.class);


    @RequestMapping(value = "/api/generateRecommendation", method =  RequestMethod.POST)
   // @ResponseBody
    public void test(@RequestBody final UserModel userModel){
        //Info info = null;

           // final CountDownLatch latch = new CountDownLatch(1);

            dataService.getDocumentsSeenByUser(userModel.getUserId(), userModel.getInfoType().get(0)).done(new DoneCallback() {
                @Override
                public void onDone(Object resolvedObject) {
                    try {
                        log.info("Gotten seen documents");

                        HashMap<String,SeenDocument> seenDocumentHashMap = (HashMap<String,SeenDocument>) resolvedObject;

                        BooleanQuery.Builder booleanQuery = contextQueryBuilder.buildQueryFromUserContext(userModel);

                        List<ScoreDoc> documents = contextMatcher.findMatchingDocuments(booleanQuery);

                        IndexSearcher indexSearcher = contextMatcher.getIndexSearcher();

                        Info info = informationSelector.returnUnseenDocuent(seenDocumentHashMap, documents, userModel.getInfoType().get(0));

                        for (ScoreDoc document : documents) {
                            int docId = document.doc;
                            Document resolvedDocument = indexSearcher.doc(docId);
                            Explanation explanation = indexSearcher.explain(booleanQuery.build(), docId);
                            log.info(explanation.toString());

                            log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
                            log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
                            log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
                            log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
                            log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
                        }
                        if (info != null) {
                            dataService.addDocumentToUserPortfolio(userModel.getUserId(),info);
                        }else{
                            //give it random implementation and set information field as below
                            info = new CarbsRelatedInfo();
                            info.setInformation("No information");
                        }



                    //latch.countDown();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            });



       // return info;
    }

//    private void retrieveSeenUserDocsSynchronously(String userId, String info_type) throws InterruptedException {
//        final CountDownLatch latch = new CountDownLatch(1);
//        HashMap<String,SeenDocument> seenDocumentHashMap;
//        dataService.getDocumentsSeenByUser(userId, info_type).done(new DoneCallback() {
//            @Override
//            public void onDone(Object resolvedObject) {
//                seenDocumentHashMap = (HashMap<String,SeenDocument>) resolvedObject;
//                latch.countDown();
//                log.info("Gotten seen documents");
//            }
//        });
//        latch.await();
//    }


    //previous
//    public void test(@RequestBody UserModel userModel){
//        try {
//            //contextMatcher.openIndexDirectory();
//
//            BooleanQuery.Builder booleanQuery = contextQueryBuilder.buildQueryFromUserContext(userModel);
//
//            List<ScoreDoc> documents = contextMatcher.findMatchingDocuments(booleanQuery);
//
//            IndexSearcher indexSearcher = contextMatcher.getIndexSearcher();
//            int count = 0;
//            for (ScoreDoc document : documents) {
//                int docId = document.doc;
//                Document resolvedDocument = indexSearcher.doc(docId);
//
//                log.info("Score :"+document.score);
//                log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
//                log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
//                log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
//                log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
//                log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
//                log.info("Seen By: "+resolvedDocument.get(LuceneConstants.SEEN_BY));
//                count++;
//            }
//            log.info("Documents returned: "+count);
//            //contextMatcher.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//    }


//    public Info test(@RequestBody UserModel userModel){
//        Info info = null;
//        try {
//            HashMap<String,SeenDocument> seenDocumentHashMap = new HashMap<String, SeenDocument>();
//
//
//            retrieveSeenUserDocsSynchronously(userModel.getUserId(), userModel.getInfoType().get(0));
//
//
//            BooleanQuery.Builder booleanQuery = contextQueryBuilder.buildQueryFromUserContext(userModel);
//
//            List<ScoreDoc> documents = contextMatcher.findMatchingDocuments(booleanQuery);
//
//            IndexSearcher indexSearcher = contextMatcher.getIndexSearcher();
//
//            info = informationSelector.returnUnseenDocuent(seenDocumentHashMap, documents, userModel.getInfoType().get(0));
//            for (ScoreDoc document : documents) {
//                int docId = document.doc;
//                Document resolvedDocument = indexSearcher.doc(docId);
//                Explanation explanation = indexSearcher.explain(booleanQuery.build(), docId);
//                log.info(explanation.toString());
////            if (checkIfDocumentSeen(resolvedDocument,seenDocumentHashMap) != true)  {
////                info = classInstantiator(infoType);
////                buildInfo(info,resolvedDocument);
////                break;
////            }
//
////
////                log.info("Score :"+document.score);
//                log.info("Info type: "+resolvedDocument.get(LuceneConstants.INFO_TYPE));
//                log.info("User Context: "+resolvedDocument.get(LuceneConstants.USER_CONTEXT));
//                log.info("Location Context: "+resolvedDocument.get(LuceneConstants.LOCATION_CONTEXT));
//                log.info("TimeContext: "+resolvedDocument.get(LuceneConstants.TIME_CONTEXT));
//                log.info("Information: "+resolvedDocument.get(LuceneConstants.INFORMATION));
////                log.info("Seen By: "+resolvedDocument.get(LuceneConstants.SEEN_BY));
//            }
//
//
//            //log.info("Documents returned: "+count);
//            //contextMatcher.close();
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } if (info == null){
//            //give it random implementation and set information field as below
//            info = new CarbsRelatedInfo();
//            info.setInformation("No information");
//        }
//
//        return info;
//    }

}
