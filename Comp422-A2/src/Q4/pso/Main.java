
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Q4.pso;

import java.io.*;

import Q4.main.Griewanks;
import Q4.main.Rosenbrock;

/**
 *
 * @author xuebing
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		int number_of_particles = 50;
		int number_of_iterations = 2500;
		int number_of_runs = 30;
		double c1 = 1.49618, c2 = 1.49618;

		Swarm s = new Swarm();

		Problem type = null;

		if(args[0].equals("rosen")){
			type = new Rosenbrock();
		}
		else{
			type = new Griewanks();
		}

		s.setProblem(type);

		//s.setTopology(new StarTopology());
		s.setTopology(new RingTopology(5));

		s.setVelocityClamp(new BasicVelocityClamp());

		for (int i = 0; i < number_of_particles; ++i) {
			Particle p = new Particle();
			p.setSize(20);
			p.setC1(c1);
			p.setC2(c2);

			p.setInertia(0.7298);
			s.addParticle(p);
		}

		/**
		 *  // start operate PSO
		 */
		 /** Sava the results
		  */
		double Fitness_Runs_Iterations[][] = new double[number_of_runs][number_of_iterations]; // get bestfitnes in each iterate in each run
		double AverageFitnessRuns[] = new double[number_of_iterations]; // average best fitness in each iterate in all runs
		double BestFitnessRuns[] = new double[number_of_runs];    // final get bestfitnes in each run
		/** start PSO
		 */
		Particle bestParticles [] = new Particle[number_of_iterations * number_of_runs];

		Particle best_particle = null;
		for (int r = 0; r < number_of_runs; ++r) {

			s.initialize();
			for (int i = 0; i < number_of_iterations; ++i) {
				s.iterate();
				// System.out.println("BEST=" + s.getParticle(11).getPersonalFitness());
				// System.out.println("Average=" + NewMath.Averageitera(s));

				/**
				 *  // get bestfitnes in every iterate for different topology except star
				 */
				double best_fitness = s.getProblem().getWorstFitness();
				for (int j = 0; j < s.numberOfParticles(); ++j) {
					if (s.getProblem().isBetter(s.getParticle(j).getPersonalFitness(), best_fitness)) {
						best_particle = s.getParticle(j);
						best_fitness = best_particle.getPersonalFitness();
					}
				}
				Fitness_Runs_Iterations[r][i] = best_fitness;
				bestParticles[r] = new Particle(best_particle);
				// BestFitnessIterations[i] = best_fitness;
				/**
				 *  // get bestfitnes in each iterate
				 */

			}//end all iterations

			double Bestbest_fitness = s.getProblem().getWorstFitness();

			for (int i = 0;i < Fitness_Runs_Iterations[r].length - 1 ; i++) {

				if (s.getProblem().isBetter(Fitness_Runs_Iterations[r][i], Bestbest_fitness)) {

					Bestbest_fitness = Fitness_Runs_Iterations[r][i];
					// best_particle = s.getParticle(j);
				}


			}
			BestFitnessRuns[r] = Bestbest_fitness;

			/**
			 *  // get bestfitnes in each run
			 */
		}//end all runs


		// get bestfitnes from final bestfitnes in all runs
		double Bestbest_fitness = s.getProblem().getWorstFitness();
		int bestrun = 0;

		best_particle = bestParticles[0];
		double best_particle_fitness = type.fitness(best_particle.getPosition());

		for (int r = 0; r < number_of_runs; r++) {
			if (s.getProblem().isBetter(BestFitnessRuns[r], Bestbest_fitness)) {
				bestrun = r;
				Bestbest_fitness = BestFitnessRuns[bestrun];
				// best_particle = s.getParticle(j);
			}


		}

		for(int r = 0; r < bestParticles.length -1 ; r ++){

			if(bestParticles[r] != null
					&& type.fitness(bestParticles[r].getPosition()) < best_particle_fitness){
				best_particle = bestParticles[r];
				best_particle_fitness = type.fitness(bestParticles[r].getPosition());
			}
		}

		System.out.println(bestrun);
		System.out.println("Best:" + Bestbest_fitness);
		System.out.println("Average:" + NewMath.Best_Mean_STD(BestFitnessRuns)[0]);
		System.out.println("Standard Deviation:" + NewMath.Best_Mean_STD(BestFitnessRuns)[1]);

		System.out.println(best_particle.getPosition());
		System.out.println("Value: " + best_particle_fitness);

		AverageFitnessRuns = NewMath.AverageRunIterations(Fitness_Runs_Iterations);  // average best fitness in each iterate in all runs

		/** Output the results to tex
		 */
		// Print all the best fitness in all the runs to BestfitnessRuns.txt
		// Print all the best fitness in all the runs to Plot.txt, which are used to plot the averagefitness--iter figure
		try {
			// File path=new File("F:/test");
			File myFilePath = new File("BestfitnessRuns.txt");
			File myFilePath2 = new File("Plot.txt");

			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			if (!myFilePath2.exists()) {
				myFilePath2.createNewFile();
			}

			FileWriter pfile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(pfile);

			FileWriter pfile2 = new FileWriter(myFilePath2);
			PrintWriter myFile2 = new PrintWriter(pfile2);

			for (int r = 0; r < number_of_runs; ++r) {
				myFile.println(BestFitnessRuns[r]);
			}
			pfile.close();
			for (int i = 0; i < number_of_iterations; ++i) {
				myFile2.println(AverageFitnessRuns[i]);
				// System.out.println(AverageFitnessRuns[i]);
			}
			pfile2.close();

		} catch (Exception e) {
			System.out.println("eorr");
		}
	}
}
