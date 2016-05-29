package com.pedagochi.lucene;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tobi on 5/26/2016.
 */
public class ContextMatcher {
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private String indexPath;
    private int maxHits = 1000;

    public ContextMatcher(IndexReader indexReader,IndexSearcher indexSearcher ){
        this.indexReader = indexReader;
        this.indexSearcher = indexSearcher;
    }

//    public void openIndexDirectory() throws IOException {
//        Directory indexDir = FSDirectory.open(Paths.get(indexPath));
//        indexReader  = DirectoryReader.open(indexDir);
//    }

    public List<ScoreDoc> findMatchingDocuments(BooleanQuery.Builder booleanQuery) throws IOException {
        //
        TopDocs topDocs = indexSearcher.search(booleanQuery.build(), maxHits);
        List<ScoreDoc> hitList = new ArrayList<ScoreDoc>(Arrays.asList(topDocs.scoreDocs));
        return hitList;
    }

    public void close() throws IOException {
        indexReader.close();
    }

    public IndexReader getIndexReader() {
        return indexReader;
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }
}
