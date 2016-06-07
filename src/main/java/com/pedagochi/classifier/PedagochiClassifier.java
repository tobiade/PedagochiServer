package com.pedagochi.classifier;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tobi on 6/3/2016.
 */
public class PedagochiClassifier extends SimpleNaiveBayesClassifier {
    public PedagochiClassifier(LeafReader leafReader, Analyzer analyzer, Query query, String classFieldName, String... textFieldNames) {
        super(leafReader, analyzer, query, classFieldName, textFieldNames);
    }

    @Override
    public List<ClassificationResult<BytesRef>> assignClassNormalizedList(String inputDocument) throws IOException {
        return super.assignClassNormalizedList(inputDocument);
    }
}
