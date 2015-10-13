package Q6.Classification;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Naive {


	private NaiveBayes classifier = new NaiveBayes();
	private Evaluation eval;

	public Naive(String toRemove, Instances instances){

		Instances newInst = null;


		Remove remove = new Remove();
		remove.setAttributeIndices(toRemove);
		remove.setInvertSelection(false);

		try {
			remove.setInputFormat(instances);
			newInst = Filter.useFilter(instances, remove);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}


		// Make the last attribute be the class
		newInst.setClassIndex(newInst.numAttributes() - 1);

/*		newInst.randomize(new java.util.Random(0));

		int trainSize = (int) Math.round(newInst.numInstances() * .80);
		int testSize = newInst.numInstances() - trainSize;
		Instances train = new Instances(newInst, 0, trainSize);
		Instances test = new Instances(newInst, trainSize, testSize);

		trainClassifier(train);*/

		testClassifier(newInst);

	}

	public static void main(String args[]){
		try {
			//DataSource source = new DataSource("/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q6/wbcd.arff");
			DataSource source = new DataSource("/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q6/sonar.arff");

			new Naive("",source.getDataSet());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void trainClassifier(Instances trainingData){

		try {
			classifier.buildClassifier(trainingData);
		} catch (Exception e) {
			throw new Error(e);
		}

	}

	public void testClassifier(Instances test) {

		try {
			//Evaluate the model with the test
			eval = new Evaluation(test);
			eval.crossValidateModel(classifier, test, 10, new Random());

		} catch (Exception e) {
			throw new Error(e);
		}


		//Print all the information about the evaluation
		String strSummary = eval.toSummaryString();
		System.out.println(strSummary);

		try {
			System.out.println(eval.toClassDetailsString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		double[][] matrix = eval.confusionMatrix();

		for(int i = 0 ; i < matrix.length ; i ++){
			for(int j = 0 ; j < matrix[i].length ; j ++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

	}

}
