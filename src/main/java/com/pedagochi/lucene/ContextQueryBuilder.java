package com.pedagochi.lucene;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.pedagochi.user.UserModel;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.QueryBuilder;

import java.util.List;

/**
 * Created by Tobi on 5/25/2016.
 */
public class ContextQueryBuilder {
    private BooleanQuery.Builder booleanQuery;


    public BooleanQuery.Builder buildQueryFromUserContext(UserModel userModel){
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        booleanQuery = addToBooleanQuery(LuceneConstants.INFO_TYPE, userModel.getInfoType(), booleanQuery, BooleanClause.Occur.MUST);
        booleanQuery = addToBooleanQuery(LuceneConstants.LOCATION_CONTEXT, userModel.getLocationContext(), booleanQuery, BooleanClause.Occur.SHOULD);
        booleanQuery = addToBooleanQuery(LuceneConstants.TIME_CONTEXT, userModel.getTimeContext(), booleanQuery, BooleanClause.Occur.SHOULD);
        booleanQuery = addToBooleanQuery(LuceneConstants.USER_CONTEXT, userModel.getUserContext(), booleanQuery, BooleanClause.Occur.SHOULD);
        return booleanQuery;
    }

    private BooleanQuery.Builder addToBooleanQuery(String fieldName, List<String> contextList, BooleanQuery.Builder booleanQuery, BooleanClause.Occur clause){
        for (String context : contextList) {
            Term term = new Term(fieldName,context);
            TermQuery termQuery = new TermQuery(term);
            if (fieldName.equals(LuceneConstants.USER_CONTEXT)){
                BoostQuery boostQuery = new BoostQuery(termQuery, 4.0f);
                booleanQuery.add(boostQuery,clause);
            }else{
                booleanQuery.add(termQuery,clause);
            }
        }
        return booleanQuery;
    }

    public BooleanQuery.Builder getBooleanQuery() {
        return booleanQuery;
    }

    public void setBooleanQuery(BooleanQuery.Builder booleanQuery) {
        this.booleanQuery = booleanQuery;
    }
}
