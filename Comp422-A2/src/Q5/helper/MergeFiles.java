package Q5.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MergeFiles {

	public MergeFiles(String files[]){

		List<Scanner> fileList = new ArrayList<Scanner>();
		PrintWriter pw = null;

		for(int i = 0 ; i < files.length ;  i ++ ) {

			try {
				fileList.add(new Scanner(new File(files[i])));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		try {
			pw = new PrintWriter(new File("NewFeatures"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<String> newLines = new ArrayList<String>();

		boolean first = true;

		for(int i = 0 ; i < fileList.size() ; i ++ ){

			Scanner sc = fileList.get(i);
			int j = 0;

			while(sc.hasNext()){

				if(first == true){
					newLines.add("");
				}

				String newString = newLines.get(j);

				if(i == fileList.size() - 1){
					newString += sc.nextLine();
				}
				else{
					newString += sc.nextLine().split(",")[0] + ",";
				}
				newLines.set(j, newString);

				j++;
			}

			first = false;
		}

		for(String s : newLines){

			pw.println(s);
			pw.flush();
		}

		pw.close();
	}


	public static void main(String args[]){
		new MergeFiles(args);
	}

}
