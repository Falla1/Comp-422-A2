package Q2.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Data {

	private List<Double> features;
	private int classType;


	public Data(){
		features = new ArrayList<Double>();
	}

	public boolean addFeature(double feature){
		return features.add(feature);
	}

	public double getFeature(int i){
		return features.get(i);
	}


	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public int getFeatureSize() {
		return features.size();
	}

	public double[] getFeatures() {

		double[] featureArray = new double[features.size()];

		for(int i = 0 ; i < features.size(); i ++){
			featureArray[i] = features.get(i);
		}

		return featureArray;
	}


	public double[] getClassTypeArray() {

		double[] classArray = new double[10];

		for(int i = 0 ; i < 10 ; i ++){
			if(i == classType){
				classArray[i] = 1;
			}
			else{
				classArray[i] = 0;
			}
		}

		return classArray;
	}

}
