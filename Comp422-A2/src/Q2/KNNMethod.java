package Q2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

public class KNNMethod {

	public KNNMethod(){
		Scanner datafile = readDataFile("/vol/courses/comp422/datasets/digits/digits05");

		File tempTraining = new File("tempTraining.txt");
		File tempTest = new File("tempTest.txt");

		createTempFile(datafile,tempTraining,tempTest);

		BufferedReader tmpTrainData = readDataFileBuffer(tempTraining);
		BufferedReader tmpTestData = readDataFileBuffer(tempTest);


		Instances dataTrain;
		Instances dataTest;


		try {


			dataTrain = new Instances(tmpTrainData);
			dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

			dataTest = new Instances(tmpTestData);
			dataTest.setClassIndex(dataTest.numAttributes() - 1);

			IBk ibk = new IBk(3);

			ibk.buildClassifier(dataTrain);

			Evaluation eva = new Evaluation(dataTrain);

			eva.evaluateModel(ibk, dataTest);

			int correct = 0;
			int incorrect = 0;

			for(int i = 0 ; i < dataTest.numInstances() ; i ++){

				double value = ibk.classifyInstance(dataTest.instance(i));

				if(value == dataTest.instance(i).classValue()){
					correct++;
				}
				else{
					incorrect++;
				}
			}

			System.out.println(eva.toSummaryString());
			System.out.println("Correct: " + correct);
			System.out.println("Incorrect: " + incorrect);
			System.out.println("Ratio: " + (correct + 0.0)/(correct+incorrect));


		} catch (Exception e) {
			throw new Error(e);
		}

		datafile.close();
		//tempTraining.delete();
		tempTest.delete();
	}


	private void createTempFile(Scanner sc, File tempTrain, File tempTest) {


		PrintWriter pwTrain = null;
		PrintWriter pwTest = null;


		try {
			pwTrain = new PrintWriter(tempTrain);
			pwTest = new PrintWriter(tempTest);
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}

		pwTrain.println("@relation imageData");

		pwTest.println("@relation imageData");

		for(int i = 0 ; i < 50 ; i ++ ){
			pwTrain.println("@attribute " + i + " numeric");
			pwTest.println("@attribute " + i + " numeric");
		}

		pwTrain.flush();

		pwTrain.println("@data");

		pwTest.flush();

		pwTest.println("@data");

		int i = 0 ;

		while(sc.hasNext()){

			if(i > 499){
				pwTrain.println(sc.nextLine());
				pwTrain.flush();
			}
			else{
				pwTest.println(sc.nextLine());
				pwTest.flush();
			}
			i++;
		}

		pwTrain.close();
		pwTest.close();

	}


	public BufferedReader readDataFileBuffer(File tmp) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(tmp));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + tmp);
		}

		return inputReader;
	}

	public Scanner readDataFile(String filename) {
		Scanner inputReader = null;

		try {
			inputReader = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static void main(String[] args) throws Exception {
		new KNNMethod();
	}
}