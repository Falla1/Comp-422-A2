
package Q5.classifier;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.CSVLoader;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

public class Naive {

	Classifier bayes;
	Instances training;

	String dir;
	/**
	 * 0 = Face
	 * 1 = Non-Face
	 * @param dir
	 */
	public Naive(String dir){
		this.dir = dir;
		setup();
		loadTrainingSet();
		testSet();
	}

	private void testSet() {

		CSVLoader loader = new CSVLoader();
		Instances test;

		Evaluation eva;

		try {
			loader.setSource(new File(dir + "/2.2/mit-cbcl-faces-balanced/test.csv"));
			test = loader.getDataSet();
			test.setClass(test.attribute("class"));

			eva = new Evaluation(training);

			eva.crossValidateModel(bayes, test, 10, new Random());

			String strSummary = eva.toSummaryString();
			System.out.println(strSummary);

			try {
				System.out.println(eva.toClassDetailsString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}


	}

	private void setup() {
		bayes = new NaiveBayes();
	}

	private void loadTrainingSet(){
		CSVLoader loader = new CSVLoader();

		try {
			loader.setSource(new File(dir + "2.2/mit-cbcl-faces-balanced/train.csv"));
			training = loader.getDataSet();
			training.setClass(training.attribute("class"));
			bayes.buildClassifier(training);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}

	}

}