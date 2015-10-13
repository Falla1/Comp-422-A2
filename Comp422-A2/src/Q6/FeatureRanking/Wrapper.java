package Q6.FeatureRanking;

import java.util.ArrayList;
import java.util.List;

import Q6.Classification.Naive;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class Wrapper {

	//10 for sonar
	//8 for wbcd
	int n = 10;

	public Wrapper(String file){
		Instances instances = null;
		try {
			DataSource source = new DataSource(file);
			//DataSource source = new DataSource("/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q6/wbcd.arff");
			instances = source.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		instances.randomize(new java.util.Random(1));

		int trainSize = (int) Math.round(instances.numInstances() * .50);
		int testSize = instances.numInstances() - trainSize;

		Instances train = new Instances(instances, 0, trainSize);
		Instances test = new Instances(instances, trainSize, testSize);

		NaiveBayes lmt = new NaiveBayes();
		//lmt.setDebug(true);

		WrapperSubsetEval wrapeval = new WrapperSubsetEval();
		wrapeval.setClassifier(lmt);
		wrapeval.setEvaluationMeasure(new
				SelectedTag(WrapperSubsetEval.EVAL_ACCURACY,WrapperSubsetEval.TAGS_EVALUATION));
		wrapeval.setFolds(20);

		int [] selattridxs = null;

		try {
			wrapeval.buildEvaluator(train);
			lmt.buildClassifier(train);
			BestFirst search = new BestFirst();

			search.setDirection(new
					SelectedTag(1,BestFirst.TAGS_SELECTION));//0:backward

			search.setSearchTermination(40);

			selattridxs = search.search(wrapeval,train);

			for(int i = 0 ; i < selattridxs.length ; i ++){
				System.out.print(selattridxs[i] + ",");
			}
			n = selattridxs.length;
			System.out.println("N = " + n);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Error(e);
		}

		List<Integer> toKeep = new ArrayList<Integer>();

		for(int i = 0 ; i < n ; i++ ) {
			toKeep.add(selattridxs[i]);
		}

		toKeep.add(train.numAttributes());

		StringBuilder removeColumns = new StringBuilder();

		for(int i = 0 ; i < train.numAttributes() ; i ++){
			if(!toKeep.contains(i + 1)){
				removeColumns.append(i + 1);
				removeColumns.append(",");
			}
		}


		new Naive(removeColumns.toString().substring(0, removeColumns.length() - 1),test);

	}

	public class Holder{

		public Holder(int i, double evaluateAttribute) {
			attributeNumber = i;
			value = evaluateAttribute;
		}
		public double value;
		public int attributeNumber;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + attributeNumber;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Holder other = (Holder) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (attributeNumber != other.attributeNumber)
				return false;
			return true;
		}
		private Wrapper getOuterType() {
			return Wrapper.this;
		}

		@Override
		public String toString(){
			return String.valueOf(value);
		}
	}


}
