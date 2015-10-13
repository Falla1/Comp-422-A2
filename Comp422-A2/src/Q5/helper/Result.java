package Q5.helper;

public class Result {

	//I ASK FORGIVENESS FROM THE ALMIGHTY

	//HONOURS DUE SOON>!>!>!>!>SO NOT CLEANEST JOB

	//ENCAPSULATION!? WHATS THAT?
	public int correctlyClassified;
	public double correctPercent;

	public int inCorrectlyClassified;
	public double inCorrectPercent;

	public double Kappa       ;
	public double Mean        ;
	public double Root         ;
	public double Relative       ;
	public double Root_relative  ;
	public double Coverage    ;
	public double Mean_rel   ;
	public double Total;

	public Result(String string,
			String string2,
			String string3,
			String string4,
			String string5,
			String string6,
			String string7,
			String string8,
			String string9,
			String string10,
			String string11,
			String string12){

		this.correctlyClassified = Integer.valueOf(string);
		this.correctPercent= Double.valueOf(string2);
		this.inCorrectlyClassified= Integer.valueOf(string3);
		this.inCorrectPercent=Double.valueOf(string4);
		this.Kappa=Double.valueOf(string5);
		this.Mean=Double.valueOf(string6);
		this.Root=Double.valueOf(string7);
		this.Relative=Double.valueOf(string8);
		this.Root_relative=Double.valueOf(string9);
		this.Coverage=Double.valueOf(string10);
		this.Mean_rel=Double.valueOf(string11);
		this.Total=Double.valueOf(string12);

	}

	@Override
	public String toString(){

		StringBuilder st = new StringBuilder();

		st.append("Correctly Classified Instances \t\t , " + correctlyClassified + " , " + correctPercent + "%\n" +
				"Incorrectly Classified Instances \t\t , " + inCorrectlyClassified + " , " + inCorrectPercent + "%\n" +
				"Kappa statistic \t\t , " + Kappa +
				"\nMean absolute error\t\t , " + Mean +
				"\nRoot mean squared error\t\t , " + Root +
				"\nRelative absolute error\t\t , " + Relative +
				"\nRoot relative squared error\t\t , " + Root_relative +
				"\nCoverage of cases (0.95 level)\t\t , " + Coverage +
				"\nMean rel. region size (0.95 level)\t\t , " + Mean_rel +
				"\nTotal Number of Instances\t\t , " + Total);


		return st.toString();
	}

	public void divide(int i){

		this.correctlyClassified /= i;
		this.correctPercent/= i;
		this.inCorrectlyClassified/= i;
		this.inCorrectPercent/= i;
		this.Kappa/= i;
		this.Mean/= i;
		this.Root/= i;
		this.Relative/= i;
		this.Root_relative/= i;
		this.Coverage/= i;
		this.Mean_rel/= i;
		this.Total/= i;

	}

}
