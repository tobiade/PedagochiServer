package com.pedagochi.lucene;

import com.google.firebase.database.DataSnapshot;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.informationmodels.Info;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.jdeferred.DoneCallback;

import java.io.IOException;

/**
 * Created by Tobi on 5/27/2016.
 */
public class DocumentUpdater {
    //private IndexReader indexReader;
    private IndexWriter indexWriter;
   // private IndexSearcher indexSearcher;
    private FirebaseDataService dataService;
    private DocumentBuilder documentBuilder;

    public DocumentUpdater(IndexWriter indexWriter, FirebaseDataService dataService, DocumentBuilder documentBuilder){
       // this.indexReader = indexReader;
        this.indexWriter = indexWriter;
        //this.indexSearcher = indexSearcher;
        this.dataService = dataService;
        this.documentBuilder = documentBuilder;
    }

    public void updateDocumentListener(String key, final Class< ? extends Info> classType) throws IOException {
        dataService.registerSeenDocumentListener(key).done(new DoneCallback() {
            @Override
            public void onDone(Object resolvedObject) {

                try {
                    DataSnapshot snapshot = (DataSnapshot) resolvedObject;
                    Document newDocument = documentBuilder.createDocumentFromSnapshot(snapshot, classType);
                    Term term = new Term(LuceneConstants.ID, newDocument.get(LuceneConstants.ID));
                    indexWriter.updateDocument(term,newDocument);
                    indexWriter.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
