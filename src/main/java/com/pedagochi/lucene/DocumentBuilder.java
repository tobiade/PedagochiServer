package com.pedagochi.lucene;


import com.google.firebase.database.DataSnapshot;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.informationmodels.CarbsRelatedInfo;
import com.pedagochi.informationmodels.ExerciseRelatedInfo;
import com.pedagochi.informationmodels.GeneralInfo;
import com.pedagochi.informationmodels.Info;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.jdeferred.DeferredManager;
import org.jdeferred.DoneCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.multiple.MultipleResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobi on 5/24/2016.
 */
public class DocumentBuilder {

    private FirebaseDataService dataService;
    private Indexer indexer;
    private final Logger log = LoggerFactory.getLogger(DocumentBuilder.class);

    public DocumentBuilder(FirebaseDataService dataService, Indexer indexer){
        this.dataService = dataService;
        this.indexer = indexer;
    }

    public void createDocumentIndex(){
        Promise carbsInfoPromise = dataService.getContextualInformation(FirebaseDataService.FirebaseNode.CARBS_RELATED.toString());
        Promise exerciseInfoPromise = dataService.getContextualInformation(FirebaseDataService.FirebaseNode.EXERCISE_RELATED.toString());
        Promise generalInfoPromise = dataService.getContextualInformation(FirebaseDataService.FirebaseNode.GENERAL.toString());



        DeferredManager deferredManager = new DefaultDeferredManager();
        deferredManager.when(carbsInfoPromise, exerciseInfoPromise, generalInfoPromise)
                .done(new DoneCallback<MultipleResults>() {
                    @Override
                    public void onDone(MultipleResults oneResults) {
                        log.info("Creating document index...");
                        DataSnapshot carbsInfoSnapshot = (DataSnapshot) oneResults.get(0).getResult();
                        DataSnapshot exerciseInfoSnapshot = (DataSnapshot) oneResults.get(1).getResult();
                        DataSnapshot generalInfoSnapshot = (DataSnapshot) oneResults.get(2).getResult();

                        List<Document> carbsDocumentsList = buildDocumentFields(carbsInfoSnapshot, CarbsRelatedInfo.class);
                        List<Document> exerciseDocumentsList = buildDocumentFields(exerciseInfoSnapshot, ExerciseRelatedInfo.class);
                        List<Document> generalDocumentsList = buildDocumentFields(generalInfoSnapshot, GeneralInfo.class);

                        try {
                            writeDocumentsToIndex(carbsDocumentsList);
                            writeDocumentsToIndex(exerciseDocumentsList);
                            writeDocumentsToIndex(generalDocumentsList);
                            indexer.commit();
                            log.info("Document index created");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }


    private List<Document> buildDocumentFields(DataSnapshot dataSnapshot, Class< ? extends Info> classType ){
        List<Document> documentList = new ArrayList<Document>();
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            Info info =  snapshot.getValue(classType);

            Document document = new Document();
            Field infoType = new TextField(LuceneConstants.INFO_TYPE, info.getInfo_type(), Field.Store.YES);
            document.add(infoType);
            Field information = new StoredField(LuceneConstants.INFORMATION, info.getInformation());
            document.add(information);
            Field url = new StoredField(LuceneConstants.URL, info.getUrl());
            document.add(url);
            Field locationContext = new TextField(LuceneConstants.LOCATION_CONTEXT, info.getLocation_context(), Field.Store.YES);
            document.add(locationContext);
            Field timeContext = new TextField(LuceneConstants.TIME_CONTEXT, info.getTime_context(), Field.Store.YES);
            document.add(timeContext);
            Field userContext = new TextField(LuceneConstants.USER_CONTEXT, info.getUser_context(), Field.Store.YES);
            document.add(userContext);
            //Field seenBy = new StoredField(LuceneConstants.SEEN_BY, info.infoSeenByUsers());
           // document.add(seenBy);
            Field id = new StoredField(LuceneConstants.ID, snapshot.getKey());
            document.add(id);

            documentList.add(document);


        }
        return  documentList;
    }

    public Document createDocumentFromSnapshot(DataSnapshot snapshot, Class< ? extends Info> classType ){
        Info info =  snapshot.getValue(classType);

        Document document = new Document();
        Field infoType = new TextField(LuceneConstants.INFO_TYPE, info.getInfo_type(), Field.Store.YES);
        document.add(infoType);
        Field information = new StoredField(LuceneConstants.INFORMATION, info.getInformation());
        document.add(information);
        Field url = new StoredField(LuceneConstants.URL, info.getUrl());
        document.add(url);
        Field locationContext = new TextField(LuceneConstants.LOCATION_CONTEXT, info.getLocation_context(), Field.Store.YES);
        document.add(locationContext);
        Field timeContext = new TextField(LuceneConstants.TIME_CONTEXT, info.getTime_context(), Field.Store.YES);
        document.add(timeContext);
        Field userContext = new TextField(LuceneConstants.USER_CONTEXT, info.getUser_context(), Field.Store.YES);
        document.add(userContext);
//        Field seenBy = new StoredField(LuceneConstants.SEEN_BY, info.infoSeenByUsers());
//        document.add(seenBy);
        Field id = new StoredField(LuceneConstants.ID, snapshot.getKey());
        document.add(id);

        return document;
    }



    private void writeDocumentsToIndex(List<Document> documents) throws IOException {
        for(Document document: documents ){
            indexer.addToIndex(document);
        }

    }



    public void printSnapShotContents(DataSnapshot dataSnapshot, Class< ? extends Info> cls ){
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Info info =  snapshot.getValue(cls);
                    log.info(info.getInfo_type());


                }
    }


}
