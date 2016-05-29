package com.pedagochi;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.pedagochi.config.FirebasePedagochiAppConfig;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.informationmodels.CarbsRelatedInfo;
import com.pedagochi.lucene.DocumentBuilder;
import com.pedagochi.lucene.DocumentUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

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

//        DocumentUpdater updater = ctx.getBean(DocumentUpdater.class);
//        try {
//            updater.updateDocumentListener(FirebaseDataService.FirebaseNode.CARBS_RELATED.toString(), CarbsRelatedInfo.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
