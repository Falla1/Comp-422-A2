package Q6.FeatureRanking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ChiSq {


	public ChiSq(){
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

		ChiSquaredAttributeEval chi = new ChiSquaredAttributeEval();

		List<Holder> results = new ArrayList<Holder>();

		try {
			chi.buildEvaluator(instances);

			for(int i = 0 ; i < instances.numAttributes(); i ++){
				results.add(new Holder(i,chi.evaluateAttribute(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(results, new Comparator<Holder>(){

			@Override
			public int compare(Holder o1, Holder o2) {
				// TODO Auto-generated method stub
				return Double.compare(o1.value, o2.value);
			}

		});

		System.out.println(results);

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
		private ChiSq getOuterType() {
			return ChiSq.this;
		}

		@Override
		public String toString(){
			return String.valueOf(value);
		}
	}

	public static void main(String args[]){
		new ChiSq();
	}

}
