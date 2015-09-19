package Q5.classifier;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

public class DT {


	private J48 classifier = new J48();
	private Evaluation eval;

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
		String graph = null;
		try {
			graph = classifier.graph();
		} catch (Exception e) {
			e.printStackTrace();
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
