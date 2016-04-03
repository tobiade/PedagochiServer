package com.tobi.finalyear;

import com.firebase.client.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by Tobi on 4/3/2016.
 */
public class FirebaseDailyAverageBGTask {
    CountDownLatch latch = new CountDownLatch(1);
    Double result;

    public FirebaseDailyAverageBGTask(){
        //this.latch = latch;
    }


    public Double getPedagochiCurrentDayBGAverage() throws InterruptedException {
        Firebase ref = new Firebase("https://brilliant-torch-960.firebaseio.com/users/0e22565f-dd64-4e2d-9bc1-a397b8eb88b6/pedagochiEntry");
        Query query = ref.orderByKey().limitToLast(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result = calculateDailyAverage(dataSnapshot);
                System.out.println("Average is:"+result);

                latch.countDown(); //unblock main thread
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        latch.await(); //block thread

        return result;
    }

    private Double calculateDailyAverage(DataSnapshot dataSnapshot){
        Double cumulativeAverage = 0.0;
        Double count = 0.0;
        //Double bloodGlucoseLevel = (Double) dataSnapshot.child("bloodGlucoseLevel").getValue();
        //String str = (String) dataSnapshot.child("bloodGlucoseLevel").toString();
        //DataSnapshot snap = (DataSnapshot) dataSnapshot.getValue();
        // System.out.println(dataSnapshot.toString());
        for(DataSnapshot dateSnapshot: dataSnapshot.getChildren()){
            for(DataSnapshot entrySnapshot: dateSnapshot.getChildren()){
                Long longVal = (Long) entrySnapshot.child("bloodGlucoseLevel").getValue();
                Double bloodGlucoseLevel = longVal.doubleValue();
                if(bloodGlucoseLevel != null){
                    cumulativeAverage = (bloodGlucoseLevel + (count*cumulativeAverage))/(count + 1);
                }
                count++;

            }


        }
        return cumulativeAverage;

    }


}
