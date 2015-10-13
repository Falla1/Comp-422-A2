package Q5.classifier;

import java.io.PrintWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Naive {


	private NaiveBayes classifier = new NaiveBayes();
	private Evaluation eval;

	private int k;

	public Naive(int k, String file){

		this.k = k;
		Instances instances = null;
		try {

			DataSource source = new DataSource(file);

			instances = source.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		instances.randomize(new java.util.Random(0));

/*		int trainSize = (int) Math.round(instances.numInstances() * .80);
		int testSize = instances.numInstances() - trainSize;
		Instances train = new Instances(instances, 0, trainSize);
		Instances test = new Instances(instances, trainSize, testSize);

		trainClassifier(train);*/

		testClassifier(instances);

	}

	public static void main(String args[]){
		//new Naive(10,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/balance.arff");
		new Naive(10,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/wine.arff");

	}

	public void trainClassifier(Instances trainingData){

		try {
			classifier.buildClassifier(trainingData);
		} catch (Exception e) {
			throw new Error(e);
		}

	}

	public void testClassifier(Instances test) {

		PrintWriter pw = null;

		try {
			//Evaluate the model with the test
			eval = new Evaluation(test);
			eval.crossValidateModel(classifier, test, k, new Random(1));
			pw = new PrintWriter("NaiveResult");
		} catch (Exception e) {
			throw new Error(e);
		}


		//Print all the information about the evaluation
		String strSummary = eval.toSummaryString();
		pw.println(strSummary);

		try {
			pw.println(eval.toClassDetailsString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pw.flush();
		pw.close();

		double[][] matrix = eval.confusionMatrix();

		for(int i = 0 ; i < matrix.length ; i ++){
			for(int j = 0 ; j < matrix[i].length ; j ++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

	}

}
