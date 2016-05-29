package com.pedagochi.firebase;

import com.google.firebase.database.*;
import com.pedagochi.informationmodels.CarbsRelatedInfo;
import com.pedagochi.informationmodels.Info;
import com.pedagochi.informationmodels.SeenDocument;
import com.pedagochi.informationmodels.UserSeenDocuments;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.DoneCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;
import org.jdeferred.multiple.MultipleResults;
import org.slf4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tobi on 5/24/2016.
 */
public final class FirebaseDataService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    public enum FirebaseNode  {
        CARBS_RELATED("carbs_related"),
        EXERCISE_RELATED("exercise_related"),
        GENERAL("general");

        private final String text;

        FirebaseNode(final String text) {
            this.text = text;
        }


        @Override
        public String toString() {
            return text;
        }


    }

    public Promise getContextualInformation(String key){
        //instantiate promise object
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();
        log.info("Getting document information from firebase...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    CarbsRelatedInfo info = snapshot.getValue(CarbsRelatedInfo.class);
//                    System.out.println(info.getUrl());
//                    System.out.print(snapshot.getKey());
//                }
                log.info("Document retrieval succeeded");
                deferred.resolve(dataSnapshot);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //System.out.println("The read failed: " + databaseError.getCode());
                deferred.reject(databaseError);
            }
        });
        return promise;
    }

    public Promise getDocumentsSeenByUser(String userId, String info_type){
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();
        log.info("Getting documents user has seen from Firebase...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("users").child(userId).child("seenDocuments").child(info_type);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,SeenDocument> seenDocuments = new HashMap<String,SeenDocument>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    SeenDocument document = snapshot.getValue(SeenDocument.class);
                    seenDocuments.put(document.getDocumentId(), document);
                    log.info(document.getDocumentId());
                }
//
//               log.info(seenDocuments.get("-KInaWWKIoG_hQm1CMtw").getDocumentId());
//                log.info(dataSnapshot.getValue().toString());
// List<String> documentIds = new ArrayList<String>();
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    String documentId = snapshot.child("documentId").getValue().toString();
//                    documentIds.add(documentId);
//                }
                deferred.resolve(seenDocuments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                deferred.reject(databaseError);
            }
        });
        return promise;
    }

    public Promise registerSeenDocumentListener(String key){
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(key);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                deferred.resolve(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                deferred.reject(databaseError);

            }
        });
        return promise;
    }







}
