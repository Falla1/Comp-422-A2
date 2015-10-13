package Q4.main;

import java.util.List;

import Q4.pso.Problem;

public class Griewanks extends Problem {

	public Griewanks() {
		setMinimization(true);
		setMinDomain(-30);
		setMaxDomain(30);
		setMaxVelocity(10);
	}

	public double fitness(List<Double> position) {

		double sum = 0.0;
		double product = 1.0;

        for (int i = 0 ; i < position.size() ; i ++) {
                sum += ((position.get(i) * position.get(i)) / 4000.0);
                product *= Math.cos(position.get(i) / Math.sqrt(i+1));
        }

        return (sum - product + 1.0);
	}

}
