package Q2;

import java.util.Arrays;
import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.RangeRandomizer;

import Q2.Helpers.Data;
import Q2.Helpers.Reader;

public class NNMethod {


	public NNMethod(){

		Reader r = new Reader("/vol/courses/comp422/datasets/digits/digits05");

		List<Data> points = r.readDataPoints();

		List<DataSet> splitPoints = r.splitData(points);

		DataSet trainingData = splitPoints.get(0);
		DataSet testData = splitPoints.get(1);

		execute(trainingData,testData);

	}

	public void execute(DataSet trainingSet, DataSet testSet){

		MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, trainingSet.getInputSize(), 9,
				trainingSet.getOutputSize());

		MomentumBackpropagation bp = new MomentumBackpropagation();

		bp.setMaxError(0.0002);
		bp.setMaxIterations(2000);
		bp.setLearningRate(0.2);

		myMlPerceptron.randomizeWeights(new RangeRandomizer(-1,1));

		myMlPerceptron.setLearningRule(bp);

		// learn the training set
		myMlPerceptron.learn(trainingSet);

		// test perceptron
		System.out.println("Testing trained neural network");
		testNeuralNetwork(myMlPerceptron, testSet);

		// save trained neural network
		myMlPerceptron.save("myMlPerceptron.nnet");

	}

	public void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

		int correct = 0;
		int incorrect = 0;

		for(DataSetRow dataRow : testSet.getRows()) {
			nnet.setInput(dataRow.getInput());
			nnet.calculate();
			double[ ] networkOutput = nnet.getOutput();

			double expected = getResult(dataRow.getDesiredOutput());
			double actual = getResult(networkOutput);

			if(expected == actual){
				correct ++;
			}
			else{
				incorrect ++;
			}
		}

		System.out.println("Correct: "  + correct);
		System.out.println("Incorrect: "  + incorrect);
		System.out.println("Ratio: "  + (correct + 0.0)/(correct + incorrect));


	}

	public double getResult(double[] output){

		double largest = -1;
		int classType = -1;

		for(int i = 0 ; i < output.length ; i ++){

			double value = output[i];

			if(value > largest){
				largest = value;
				classType = i;
			}
		}

		return classType;
	}


	public static void main(String args[]){
		new NNMethod();

	}


}
