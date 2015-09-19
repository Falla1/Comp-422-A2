package Q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CreateDataSet {

	public CreateDataSet(){

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new File("tmp"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String eq = "(1/X) + (Math.sin(X))";

		for(int i = 1 ; i < 101; i ++){
			engine.put("X", i);

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println(i + "\t" + value);
			pw.flush();
		}

		eq = "(2*X) + Math.pow(X,2) + 3.0";

		for(int i = 0 ; i > -100; i --){
			engine.put("X", i);

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println(i + "\t" + value);
			pw.flush();
		}

		pw.close();

	}

	public static void main(String args[]){

		new CreateDataSet();
	}

}
