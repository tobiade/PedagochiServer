package com.pedagochi;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.pedagochi.classifier.PedagochiClassifier;
import com.pedagochi.classifier.WekaClassifier;
import com.pedagochi.config.FirebasePedagochiAppConfig;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.infomodels.CarbsRelatedInfo;
import com.pedagochi.lucene.DocumentBuilder;
import com.pedagochi.lucene.DocumentUpdater;
import com.pedagochi.lucene.LuceneConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tobi on 4/2/2016.
 */
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main (String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

//        FirebaseTest test = new FirebaseTest();
//        String str = test.setupEventListener();
//        System.out.println(str);

        // System.out.println("Let's inspect the beans provided by Spring Boot:");

//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
        FirebasePedagochiAppConfig config = ctx.getBean(FirebasePedagochiAppConfig.class);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(Application.class.getClassLoader().getResourceAsStream("com/pedagochi/firebase/My First App-0ec3afcfe8e3.json"))
                .setDatabaseUrl(config.getRootUrl())
                .build();

        FirebaseApp.initializeApp(options);

        DocumentBuilder builder = ctx.getBean(DocumentBuilder.class);
        builder.createDocumentIndex();

//        try {
//            Directory ramD = new RAMDirectory();
//            org.apache.lucene.index.IndexWriterConfig configW = new org.apache.lucene.index.IndexWriterConfig(new StandardAnalyzer());
//            IndexWriter indexWriter = new IndexWriter(ramD, configW);
//            Document document = new Document();
//            Field info = new TextField(LuceneConstants.INFORMATION, "Fruit juices can be high in natural sugars and because they have less fibre than the whole fruits, they are not as beneficial. Because you can get through a lot of juice within a relatively short period of time, compared to eating the actual fruit, you may end up loading up with a lot of carbs over that period. Depending on how your diabetes is managed, this can result in your blood glucose levels going up, and may affect your weight in the long term as well.", Field.Store.YES);
//            Field rating = new TextField("rating", "positive", Field.Store.YES);
//            document.add(info);
//            document.add(rating);
//            Document document2 = new Document();
//            Field info2 = new TextField(LuceneConstants.INFORMATION, "A portion of fruit contains about 15-20g carbohydrate on average, which is similar to a slice of bread. To put things in perspective, just a can of cola contains 35g carb and a medium slice of chocolate cake contains 35g of carbs as well. So, if you are looking to reduce your carb intake, with the aim to manage blood glucose levels, the advice is to reduce your intake of foods like ordinary fizzy drinks, cakes, biscuits, chocolate and other snacks.", Field.Store.YES);
//            Field rating2 = new TextField("rating", "positive", Field.Store.YES);
//            document2.add(info2);
//            document2.add(rating2);
//
//            Document document4 = new Document();
//            Field info4 = new TextField(LuceneConstants.INFORMATION, "Everyone should eat at least five portions of fruit a day. Fresh, frozen, dried and canned fruit in juice and canned vegetables in water all count. Go for a rainbow of colours to get as wide a range of vitamins and minerals as possible.", Field.Store.YES);
//            Field rating4 = new TextField("rating", "positive", Field.Store.YES);
//            document4.add(info4);
//            document4.add(rating4);
//
//            Document document3 = new Document();
//            Field info3 = new TextField(LuceneConstants.INFORMATION, "It’s a myth that you can’t eat chocolate if you have diabetes, just eat it in moderation, rather than using it to satisfy hunger, and don’t eat a lot in one go as it affects your blood sugar levels.", Field.Store.YES);
//            Field rating3 = new TextField("rating", "negative", Field.Store.YES);
//            document3.add(info3);
//            document3.add(rating3);
//
//            indexWriter.addDocument(document);
//           indexWriter.addDocument(document2);
//            indexWriter.addDocument(document3);
//            indexWriter.addDocument(document4);
//
//            indexWriter.commit();
//            IndexReader indexReader  = DirectoryReader.open(ramD);
//
//
//            LeafReader leafReader = indexReader.leaves().get(0).reader();
//            PedagochiClassifier classifier = new PedagochiClassifier(leafReader,new StandardAnalyzer(),null,"rating",LuceneConstants.INFORMATION);
//            ClassificationResult<BytesRef> result = classifier.assignClass("Eating fruits and vegetables lowers the risk of developing many health conditions including high blood pressure, heart diseases, strokes, obesity and certain cancers. It’s even more important for people with diabetes to eat more fruits and vegetables as most of these conditions are more likely to affect them. Fruits and vegetables have a good mix of soluble and insoluble fibre which is good for your bowels and general health – so it makes sense to eat more of them.");
//
//            log.info("class is "+result.getAssignedClass().utf8ToString());
//            log.info("score is "+result.getScore());
//
////            List<ClassificationResult<BytesRef>> resultList = classifier.assignClassNormalizedList("Oh my is that a fruit");
////            ClassificationResult<BytesRef> result1 = resultList.get(0);
////            ClassificationResult<BytesRef> result2 = resultList.get(1);
////            log.info("class is "+result1.getAssignedClass().utf8ToString());
////            log.info("score is "+result1.getScore());
////
////            log.info("class is "+result2.getAssignedClass().utf8ToString());
////            log.info("score is "+result2.getScore());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String positive = "pos";
//        String negative = "neg";
//        String msg1 = "Fruit juices can be high in natural sugars and because they have less fibre than the whole fruits, they are not as beneficial. Because you can get through a lot of juice within a relatively short period of time, compared to eating the actual fruit, you may end up loading up with a lot of carbs over that period. Depending on how your diabetes is managed, this can result in your blood glucose levels going up, and may affect your weight in the long term as well.";
//        String msg2 = "A portion of fruit contains about 15-20g carbohydrate on average, which is similar to a slice of bread. To put things in perspective, just a can of cola contains 35g carb and a medium slice of chocolate cake contains 35g of carbs as well. So, if you are looking to reduce your carb intake, with the aim to manage blood glucose levels, the advice is to reduce your intake of foods like ordinary fizzy drinks, cakes, biscuits, chocolate and other snacks.";
//        String msg3 = "Everyone should eat at least five portions of fruit a day. Fresh, frozen, dried and canned fruit in juice and canned vegetables in water all count. Go for a rainbow of colours to get as wide a range of vitamins and minerals as possible.";
//        String msg4 = "It’s a myth that you can’t eat chocolate if you have diabetes, just eat it in moderation, rather than using it to satisfy hunger, and don’t eat a lot in one go as it affects your blood sugar levels.";
//        String classifyText = "Eating fruits and vegetables lowers the risk of developing many health conditions including high blood pressure, heart diseases, strokes, obesity and certain cancers. It’s even more important for people with diabetes to eat more fruits and vegetables as most of these conditions are more likely to affect them. Fruits and vegetables have a good mix of soluble and insoluble fibre which is good for your bowels and general health – so it makes sense to eat more of them.";
//        WekaClassifier classifier = new WekaClassifier();
//        classifier.updateModel(msg1,positive);
//        classifier.updateModel(msg2,positive);
//        classifier.updateModel(msg3,positive);
//        classifier.updateModel(msg4,negative);
//        classifier.train();
//        classifier.setupClassificationDataSet();
//        classifier.addDataForClassification(classifyText);
//        classifier.classify();

//        DocumentUpdater updater = ctx.getBean(DocumentUpdater.class);
//        try {
//            updater.updateDocumentListener(FirebaseDataService.FirebaseNode.CARBS_RELATED.toString(), CarbsRelatedInfo.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
