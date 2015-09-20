package Q6.FeatureRanking;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class Wrapper {


	public Wrapper(){
		Instances instances = null;
		try {
			DataSource source = new DataSource("/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q6/sonar.arff");
			//DataSource source = new DataSource("/am/state-opera/home1/shawmarc/git/Comp-422-A2/Comp-422-A2/Comp422-A2/src/Q6/wbcd.arff");
			instances = source.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		NaiveBayes lmt = new NaiveBayes();
		lmt.setDebug(true);
		WrapperSubsetEval wrapeval = new WrapperSubsetEval();
		wrapeval.setClassifier(lmt);
		wrapeval.setEvaluationMeasure(new
				SelectedTag(WrapperSubsetEval.EVAL_AUC,WrapperSubsetEval.TAGS_EVALUATION));
		wrapeval.setFolds(10);

		try {
			wrapeval.buildEvaluator(instances);
			BestFirst search = new BestFirst();
			search.setDirection(new
					SelectedTag(0,BestFirst.TAGS_SELECTION));//0:backward

			int [] selattridxs = search.search(wrapeval,instances);

			for(int i = 0 ; i < selattridxs.length ; i ++){
				System.out.println(selattridxs[i]);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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

	public static void main(String args[]){
		new Wrapper();
	}

}
