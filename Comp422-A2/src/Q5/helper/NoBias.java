package Q5.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import weka.core.converters.ConverterUtils.DataSource;
import Q5.GP.GP;
import Q5.classifier.DT;
import Q5.classifier.Naive;

public class NoBias {


	public NoBias(int kDepth, String file, String arffFile){

		List<Data> noBias = loadInData(file);

		List<Dataset> dataSet = createDataSets(noBias);

		List<Result> naiveResults = new ArrayList<Result>();
		List<Result> dtResults = new ArrayList<Result>();
		List<String> functions = new ArrayList<String>();

		for(int i = 0 ; i < 10 ; i ++){

			createNewDataFile(file, dataSet.get(i).training);

			System.out.println("Execution: " + i);

			try {
				new GP(new String[]{"tempConfFile","tempOutputFile1"});
				functions.add(parseFunction());
				new GP(new String[]{"tempConfFile","tempOutputFile2"});
				functions.add(parseFunction());
				new GP(new String[]{"tempConfFile","tempOutputFile3"});
				functions.add(parseFunction());

				new MergeFiles(new String[]{"tempOutputFile1","tempOutputFile2","tempOutputFile3"});

				createNewArffFile(arffFile);

				Naive nv = new Naive(3,new File("tempArffFile.arff").getAbsolutePath());

				DT dt = new DT(3,new File("tempArffFile.arff").getAbsolutePath());

				dtResults.add(parseDTResult());
				naiveResults.add(parseNaiveResult());



			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Result naiveAvgResult = new Result("0","0.0","0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0", "0.0");
		Result dtAvgResult = new Result("0","0.0","0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0", "0.0");

		for(int i = 0 ; i < naiveResults.size(); i ++){

			naiveAvgResult.correctlyClassified += naiveResults.get(i).correctlyClassified;
			naiveAvgResult.inCorrectlyClassified += naiveResults.get(i).inCorrectlyClassified;
			naiveAvgResult.correctPercent += naiveResults.get(i).correctPercent;
			naiveAvgResult.inCorrectPercent += naiveResults.get(i).inCorrectPercent;
			naiveAvgResult.Kappa += naiveResults.get(i).Kappa;
			naiveAvgResult.Mean += naiveResults.get(i).Mean;
			naiveAvgResult.Mean_rel += naiveResults.get(i).Mean_rel;
			naiveAvgResult.Relative += naiveResults.get(i).Relative;
			naiveAvgResult.Root += naiveResults.get(i).Root;
			naiveAvgResult.Root_relative += naiveResults.get(i).Root_relative;
			naiveAvgResult.Total += naiveResults.get(i).Total;

			dtAvgResult.correctlyClassified += dtResults.get(i).correctlyClassified;
			dtAvgResult.inCorrectlyClassified += dtResults.get(i).inCorrectlyClassified;
			dtAvgResult.correctPercent += dtResults.get(i).correctPercent;
			dtAvgResult.inCorrectPercent += dtResults.get(i).inCorrectPercent;
			dtAvgResult.Kappa += dtResults.get(i).Kappa;
			dtAvgResult.Mean += dtResults.get(i).Mean;
			dtAvgResult.Mean_rel += dtResults.get(i).Mean_rel;
			dtAvgResult.Relative += dtResults.get(i).Relative;
			dtAvgResult.Root += dtResults.get(i).Root;
			dtAvgResult.Root_relative += dtResults.get(i).Root_relative;
			dtAvgResult.Total += dtResults.get(i).Total;

		}


		try {
			PrintWriter pw = new PrintWriter(new File("Results"));


			naiveAvgResult.divide(naiveResults.size());
			dtAvgResult.divide(naiveResults.size());

			pw.println("NAIVE BAYES OVERALL RESULTS");
			pw.println(naiveAvgResult.toString());
			pw.println("\nDT OVERALL RESULTS");
			pw.println(dtAvgResult.toString());

			pw.println("\n\n");

			for(int i = 0 ; i < naiveResults.size() ; i++){

				pw.println("Iteration Number, " + i);
				pw.println("Functions used:\n");

				for(int j = i * 3 ; j < (i*3) + 3 ; j ++){
					pw.println(functions.get(j));
				}

				pw.println("\nNaive Bayes Details\n");
				pw.println(naiveResults.get(i));
				pw.println("\nDT Details\n");
				pw.println(dtResults.get(i) + "\n\n");
				pw.flush();
			}

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	private String parseFunction() {


		try {
			Scanner sc = new Scanner(new File("tempFunction"));

			return sc.nextLine();



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private Result parseNaiveResult() {

		try {
			Scanner sc = new Scanner(new File("NaiveResult"));

			sc.nextLine();

			String correctClass = sc.nextLine();
			String incorrectClass = sc.nextLine();

			return new Result(correctClass.split("\\s+")[3], correctClass.split("\\s+")[4],
					incorrectClass.split("\\s+")[3], incorrectClass.split("\\s+")[4],
					sc.nextLine().split("\\s+")[2],sc.nextLine().split("\\s+")[3],
					sc.nextLine().split("\\s+")[4],sc.nextLine().split("\\s+")[3],
					sc.nextLine().split("\\s+")[4],sc.nextLine().split("\\s+")[5],
					sc.nextLine().split("\\s+")[6],sc.nextLine().split("\\s+")[4]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private Result parseDTResult() {

		try {
			Scanner sc = new Scanner(new File("DTResult"));

			sc.nextLine();

			String correctClass = sc.nextLine();
			String incorrectClass = sc.nextLine();

			return new Result(correctClass.split("\\s+")[3], correctClass.split("\\s+")[4],
					incorrectClass.split("\\s+")[3], incorrectClass.split("\\s+")[4],
					sc.nextLine().split("\\s+")[2],sc.nextLine().split("\\s+")[3],
					sc.nextLine().split("\\s+")[4],sc.nextLine().split("\\s+")[3],
					sc.nextLine().split("\\s+")[4],sc.nextLine().split("\\s+")[5],
					sc.nextLine().split("\\s+")[6],sc.nextLine().split("\\s+")[4]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}

	private void createNewArffFile(String file) {

		Scanner sc = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("tempArffFile.arff"));
			sc = new Scanner(new File(file));

			String line = sc.nextLine();
			pw.println(line);

			while(!line.equals("@data")){
				line = sc.nextLine();
				pw.println(line);
				pw.flush();
			}

			sc.close();

			sc = new Scanner(new File("NewFeatures"));

			while(sc.hasNext()){
				pw.println(sc.nextLine());
				pw.flush();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
			pw.close();
		}

	}

	public List<Dataset> createDataSets(List<Data> data){

		List<Dataset> dataSet = new ArrayList<Dataset>();

		int sizeOfEachFold = data.size()/10;

		List<Integer> numbers = new ArrayList<Integer>();

		Random r = new Random(1);

		for(int i = 0 ; i < data.size(); i ++){
			numbers.add(i);
		}

		Collections.shuffle(numbers, r);

		for(int i = 0 ; i < 10 ; i ++ ) {

			List<Data> trainingSet = new ArrayList<Data>();
			List<Data> testSet = new ArrayList<Data>();

			List<Integer> numbersOfInterest = new ArrayList<Integer>();

			for(int j = i * sizeOfEachFold ; j < (i * sizeOfEachFold) + sizeOfEachFold ; j ++){
				numbersOfInterest.add(numbers.get(j));
			}

			if(i == 10-1){
				for(int j = i * sizeOfEachFold + sizeOfEachFold ; j < numbers.size(); j ++){
					numbersOfInterest.add(numbers.get(j));
				}
			}

			for(int j = 0 ; j < data.size(); j ++ ) {

				if(numbersOfInterest.contains(j)){
					testSet.add(data.get(j));
				}
				else{
					trainingSet.add(data.get(j));
				}

			}

			dataSet.add(new Dataset(trainingSet,testSet));

		}

		return dataSet;
	}

	private List<Data> loadInData(String file){


		List<Data> dataList = new ArrayList<Data>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(file));

			String line = sc.nextLine();

			while(!line.equals("data")){
				line = sc.nextLine();
			}

			while(sc.hasNext()){

				String[] nextLine = sc.nextLine().split(",");


				dataList.add(new Data(nextLine));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}

		return dataList;

	}

	private void createNewDataFile(String file, List<Data> trainingData){

		Scanner sc = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("tempConfFile"));
			sc = new Scanner(new File(file));

			String line = sc.nextLine();
			pw.println(line);

			while(!line.contains("num_rows:")){
				line = sc.nextLine();
				if(!line.contains("num_rows:")){
					pw.println(line);
					pw.flush();
				}
			}

			pw.println("num_rows: " + trainingData.size());

			while(!line.equals("data")){
				line = sc.nextLine();
				pw.println(line);
				pw.flush();
			}

			for(int i = 0 ; i < trainingData.size(); i ++ ) {

				for(int j = 0 ; j < trainingData.get(i).features.size(); j ++){
					pw.print(trainingData.get(i).features.get(j));
					pw.print(",");
				}

				pw.println(trainingData.get(i).classNumber);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
			pw.close();
		}


	}

	public static void main(String args[]){

		new NoBias(0,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/wine.conf"
		,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/wineGP.arff");
		//new NoBias(0,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/balance.conf"
				//,"/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q5/balanceGP.arff");

	}

	private class Data{

		public List<Double> features = new ArrayList<Double>();
		public int classNumber;

		public Data(String[] nextLine) {

			for(int i = 0 ; i < nextLine.length - 1 ; i ++){
				features.add(Double.valueOf(nextLine[i]));
			}

			classNumber = Integer.valueOf(nextLine[nextLine.length - 1]);

		}
	}

	private class Dataset{

		public List<Data> training;
		public List<Data> test;

		public Dataset(List<Data> training,List<Data> test){
			this.training = training;
			this.test = test;

		}

	}

}
