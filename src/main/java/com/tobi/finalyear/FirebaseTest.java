package com.tobi.finalyear;

import com.firebase.client.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by Tobi on 4/2/2016.
 */
@RestController
public class FirebaseTest {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    public FirebaseTest(){

    }

    @RequestMapping("pedagochi/api/latestaverageBG")
    public String setupEventListener(){
        Double result = null;
        FirebaseDailyAverageBGTask task = new FirebaseDailyAverageBGTask();
        try {
            result = task.getPedagochiCurrentDayBGAverage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.toString();


    }

//    private Future<Double> activateTask(){
//        Future<Double> future = null;
//        FirebaseDailyAverageBGTask task = new FirebaseDailyAverageBGTask();
//        future = executor.submit(task);
//        return future;
//
//    }

}
