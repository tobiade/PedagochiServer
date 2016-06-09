package com.pedagochi.classifier;

import com.google.firebase.database.DataSnapshot;
import com.pedagochi.infomodels.GeneralInfo;
import com.pedagochi.infomodels.Info;
import com.pedagochi.lucene.LuceneConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Tobi on 6/7/2016.
 * Inspired by code at http://www.cs.waikato.ac.nz/~ml/weka/example_code/2ed/MessageClassifier.java
 * and opensource code by jmgomezh
 */
public class WekaClassifier {

    private Instances dataset;
    private Instances classificationDataSet;
    private StringToWordVector filter;
    private FilteredClassifier classifier;
    private static final Logger log = LoggerFactory.getLogger(WekaClassifier.class);

    public WekaClassifier(){

        String nameOfDataset = "PedagochiDataset";

        // Create vector of attributes.
        FastVector attributes = new FastVector(2);

        // Add attribute for holding messages.
        attributes.addElement(new Attribute("information", (FastVector)null));

        // Add class attribute.
        FastVector classValues = new FastVector(2);
        classValues.addElement("positive");
        classValues.addElement("negative");
        attributes.addElement(new Attribute("Class", classValues));

        // Create dataset with initial capacity of 100, and set index of class.
        dataset = new Instances(nameOfDataset, attributes, 100);
        dataset.setClassIndex(dataset.numAttributes() - 1);
    }

    public void train(){
        try {
            filter = new StringToWordVector();
            filter.setAttributeIndices("first");
            filter.setLowerCaseTokens(true);
            filter.setUseStoplist(true);
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());
            classifier.buildClassifier(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluate(){
        try {
            filter = new StringToWordVector();
            filter.setAttributeIndices("first");
            filter.setLowerCaseTokens(true);
            filter.setUseStoplist(true);
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());
            Evaluation eval = new Evaluation(dataset);
            eval.crossValidateModel(classifier, dataset, 4, new Random(1));
            log.info(eval.toSummaryString());
            log.info(eval.toClassDetailsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateModel(String infoText, String classValue){
        // Make message into instance.
        Instance instance = makeNewInstance(infoText, dataset);

        // Set class value for instance.
        instance.setClassValue(classValue);

        // Add instance to training data.
        dataset.add(instance);
    }

    private Instance makeNewInstance(String infoText, Instances data){
        // Create instance of length two.
        Instance instance = new Instance(2);

        // Set value for message attribute
        Attribute infoAttribute = data.attribute("information");
        instance.setValue(infoAttribute, infoAttribute.addStringValue(infoText));

        // Give instance access to attribute information from the dataset.
        instance.setDataset(data);
        return instance;
    }

//    public void setupClassificationDataSet(){
//        // Create vector of attributes.
//        FastVector attributes = new FastVector(2);
//
//        // Add attribute for holding messages.
//        attributes.addElement(new Attribute("information", (FastVector)null));
//
//        // Add class attribute.
//        FastVector classValues = new FastVector(2);
//        classValues.addElement("positive");
//        classValues.addElement("negative");
//        attributes.addElement(new Attribute("Class", classValues));
//
//        // Create dataset with initial capacity of 100, and set index of class.
//        classificationDataSet = new Instances("classifyDataSet", attributes, 1);
//        classificationDataSet.setClassIndex(classificationDataSet.numAttributes() - 1);
//    }

    public void setupClassificationDataSetWithCapacity(int dataSetCapacity){
        // Create vector of attributes.
        FastVector attributes = new FastVector(2);

        // Add attribute for holding messages.
        attributes.addElement(new Attribute("information", (FastVector)null));

        // Add class attribute.
        FastVector classValues = new FastVector(2);
        classValues.addElement("positive");
        classValues.addElement("negative");
        attributes.addElement(new Attribute("Class", classValues));

        // Create dataset with initial capacity of 100, and set index of class.
        classificationDataSet = new Instances("classifyDataSet", attributes, dataSetCapacity);
        classificationDataSet.setClassIndex(classificationDataSet.numAttributes() - 1);
    }

//    public void setupClassificationDataSetFromFirebase(DataSnapshot o){
//        DataSnapshot dataSnapshot = (DataSnapshot) o;
//        for (DataSnapshot parentSnapshot: dataSnapshot.getChildren()) {
//            for(DataSnapshot childSnapshot: parentSnapshot.getChildren()) {
//                Info info = childSnapshot.getValue(GeneralInfo.class);
//
//
//
//
//
//            }
//
//
//        }
//    }

    public void addDataForClassification(String infoText){
        Instance instance = makeNewInstance(infoText, classificationDataSet);
//        instance.setClassValue(classValue);
        classificationDataSet.add(instance);

    }

    public void classify(){
        try {
            double prediction = classifier.classifyInstance(classificationDataSet.instance(0));
            log.info("===== Classified instance =====");
            log.info("Class predicted: " + classificationDataSet.classAttribute().value((int) prediction));
            double[] probability = classifier.distributionForInstance(classificationDataSet.instance(0));
            log.info("predicted with probability: "+ Double.toString(probability[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Info> classifyList(List<Info> infoList){
        List<Info> resultList = new ArrayList<>();
        try {
            int positiveIndex = 0;
            int negativeIndex = 1;
            for(int i = 0; i < infoList.size(); i++){
                Instance instance = classificationDataSet.instance(i);
                double prediction = classifier.classifyInstance(instance);
                log.info("===== Classified instance =====");
                String predictedClass = classificationDataSet.classAttribute().value((int) prediction);
                log.info("Class predicted: " + predictedClass);
                double[] probability = classifier.distributionForInstance(instance);
//                if (predictedClass.equals("positive")){
//                    log.info("predicted with probability: "+ Double.toString(probability[positiveIndex]));
//                }else{
//                    log.info("predicted with probability: "+ Double.toString(probability[negativeIndex]));
//
//                }
                log.info("predicted with positive probability: "+ Double.toString(probability[positiveIndex]));
                infoList.get(i).setClassificationProbability(probability[positiveIndex]);

            }
            resultList = rankListByPositiveProbability(infoList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;

    }

    private List<Info> rankListByPositiveProbability(List<Info> infoList){
        List<Info> resultList = infoList.stream()
                .sorted((i1,i2) -> Double.compare(i2.getClassificationProbability(), i1.getClassificationProbability()))
                .collect(Collectors.toList());

        for(Info info: resultList){
            log.info("===ranked info list===");
            log.info("information: "+info.getInformation());
            log.info("probability: "+info.getClassificationProbability());
        }
        return resultList;
    }
}
