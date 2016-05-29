package com.pedagochi.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;

import java.io.IOException;

/**
 * Created by Tobi on 5/25/2016.
 */
public class Indexer {
    private IndexWriter indexWriter;

    public Indexer(IndexWriter indexWriter){
        this.indexWriter = indexWriter;

    }

    public void commit() throws IOException {
        indexWriter.commit();
    }

    public void addToIndex(Document document) throws IOException {
        indexWriter.addDocument(document);
    }





}
