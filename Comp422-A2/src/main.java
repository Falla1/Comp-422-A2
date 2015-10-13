import Q1.GP.Q1_GP;
import Q1.NeuralNet.Q1_NN;
import Q2.KNNMethod;
import Q2.NNMethod;
import Q3.CreateDataSet;
import Q3.Q3;
import Q4.pso.Main;
import Q5.classifier.DT;
import Q5.classifier.Naive;
import Q5.helper.NoBias;
import Q6.FeatureRanking.ChiSq;
import Q6.FeatureRanking.InfoGain;
import Q6.FeatureRanking.Wrapper;


public class main {

	public static void main(String args[]) throws Exception{

		try{
			if(args[0].equals("Q1GP")){
				Q1_GP.main(new String[]{args[1]});
			}


			else if(args[0].equals("Q2NN")){
				new NNMethod(args[1]);
			}

			else if(args[0].equals("Q2KNN")){
				new KNNMethod(args[1]);
			}

			else if(args[0].equals("Q3")){
				CreateDataSet cd = new CreateDataSet();
				new CreateDataSet(true);

				Q3.main(new String[]{args[1]});
			}

			else if(args[0].equals("Q4")){
				Main.main(new String[]{args[1]});
			}


			else if(args[0].equals("Q5DT")){
				new DT(Integer.valueOf(args[1]),args[2]);
			}

			else if(args[0].equals("Q5Naive")){
				new Naive(Integer.valueOf(args[1]),args[2]);
			}

			else if(args[0].equals("Q5GP")){
				new NoBias(Integer.valueOf(args[1]),args[2],args[3]);
			}

			else if(args[0].equals("Q6Chi")){
				new ChiSq(args[1],Integer.valueOf(args[2]));
			}

			else if(args[0].equals("Q6Info")){
				new InfoGain(args[1],Integer.valueOf(args[2]));
			}

			else if(args[0].equals("Q6Wrap")){
				new Wrapper(args[1]);
			}
			else{
				System.out.println("Please read readme file.");

			}

		}
		catch (Exception e){
			System.out.println("Please read readme file.");
		}
	}
}
