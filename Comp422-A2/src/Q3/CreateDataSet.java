package Q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CreateDataSet {

	public CreateDataSet(){

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		PrintWriter pw = null;

		List<Integer> primeNumbers = new ArrayList<Integer>();

		for(int i = 200 ; i < 1000 ; i ++){
			if(isPrime(i)){
				primeNumbers.add(i);
			}
		}

		try {
			pw = new PrintWriter(new File("tmpData"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String eq = "(1/X) + (Math.sin(X))";


		for(Integer i : primeNumbers){
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

		for(int i = 15 ; i < 55 ; i ++){
			engine.put("X", -(-Math.pow(i, 2)) + 10);

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println(-(-Math.pow(i, 2)) + 10 + "\t" + value);
			pw.flush();
		}


		eq = "(2*X) + Math.pow(X,2) + 3.0";

		for(Integer i : primeNumbers){
				engine.put("X",  -1 * i);

				double value = 0;

				try {
					value = (double) engine.eval(eq);
				} catch (ScriptException e) {
					e.printStackTrace();
				}
				pw.println(-1 * i + "\t" + value);
				pw.flush();
		}

		for(int i = 15 ; i < 55 ; i ++){
			engine.put("X", (-Math.pow(i, 2)) + 10);

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println((-Math.pow(i, 2)) + 10 + "\t" + value);
			pw.flush();
		}

		pw.close();

	}

	public CreateDataSet(boolean bool){

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new File("tmpData"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String eq = "(1/X) + (Math.sin(X))";

		for(int i = 1 ; i < 201; i ++){
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

		for(int i = 15 ; i < 55; i ++){
			engine.put("X", i * i);

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println(i * i + "\t" + value);
			pw.flush();
		}

		eq = "(2*X) + Math.pow(X,2) + 3.0";

		for(int i = 0 ; i > -200; i --){
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

		for(int i = 15 ; i < 55; i ++){
			engine.put("X", -1 * (i * i));

			double value = 0;

			try {
				value = (double) engine.eval(eq);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			pw.println(-1 * (i * i) + "\t" + value);
			pw.flush();
		}

		pw.close();

	}


	boolean isPrime(int n) {
		//check if n is a multiple of 2
		if (n%2==0) return false;
		//if not, then just check the odds
		for(int i=3;i*i<=n;i+=2) {
			if(n%i==0)
				return false;
		}
		return true;
	}

	public static void main(String args[]){

		//new CreateDataSet(true);
		new CreateDataSet();
	}

}
