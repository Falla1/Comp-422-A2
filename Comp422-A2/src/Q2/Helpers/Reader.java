package Q2.Helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class Reader {

	private File file;

	public Reader(String fileName){
		file = new File(fileName);
	}

	public List<Data> readDataPoints(){

		List<Data> dataPoints = new ArrayList<Data>();
		Scanner sc = null;

		try{
			sc = new Scanner(file);
		}catch (Exception e){
			throw new Error(e);
		}

		while(sc.hasNext()){

			String[] lineSplit = sc.nextLine().split(" ");
			Data point = new Data();

			for(int i = 0 ; i < lineSplit.length - 1 ; i ++){

				point.addFeature(Double.valueOf(lineSplit[i]));

			}

			point.setClassType(Integer.valueOf(lineSplit[lineSplit.length -1]));

			dataPoints.add(point);

		}

		return dataPoints;
	}


	public List<DataSet> splitData(List<Data> data){

		List<DataSet> splitData = new ArrayList<DataSet>();

		splitData.add(new DataSet(data.get(0).getFeatureSize(),10));
		splitData.add(new DataSet(data.get(0).getFeatureSize(),10));

		for(int i = 0 ; i < data.size() ; i ++){

			if(i < 500){
				splitData.get(0).addRow(new DataSetRow(data.get(i).getFeatures(), data.get(i).getClassTypeArray()));
			}
			else{
				splitData.get(1).addRow(new DataSetRow(data.get(i).getFeatures(), data.get(i).getClassTypeArray()));
			}

		}

		return splitData;

	}

}
