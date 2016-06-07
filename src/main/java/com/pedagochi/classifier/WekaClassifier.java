package com.pedagochi.classifier;

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

import java.util.Random;

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
        classValues.addElement("pos");
        classValues.addElement("neg");
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

    public void setupClassificationDataSet(){
        // Create vector of attributes.
        FastVector attributes = new FastVector(2);

        // Add attribute for holding messages.
        attributes.addElement(new Attribute("information", (FastVector)null));

        // Add class attribute.
        FastVector classValues = new FastVector(2);
        classValues.addElement("pos");
        classValues.addElement("neg");
        attributes.addElement(new Attribute("Class", classValues));

        // Create dataset with initial capacity of 100, and set index of class.
        classificationDataSet = new Instances("classifyDataSet", attributes, 1);
        classificationDataSet.setClassIndex(classificationDataSet.numAttributes() - 1);
    }

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
}
