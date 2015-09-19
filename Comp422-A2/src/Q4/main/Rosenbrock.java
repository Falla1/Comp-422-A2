package Q4.main;

import java.util.List;

import Q4.pso.Problem;

public class Rosenbrock extends Problem {

    public Rosenbrock() {
        setMinimization(true);
        setMinDomain(-30);
        setMaxDomain(30);
        setMaxVelocity(6);
    }

    public double fitness(List<Double> position) {
    	    	
        double sum = 0.0;
        double [] v = new double[position.size()];
        for (int i = 0; i < position.size(); i++) v[i] = position.get(i);
        for (int i = 0 ; i < (position.size()-1) ; i ++) {
                double temp1 = (v[i] * v[i]) - v[i+1];
                double temp2 = v[i] - 1.0;
                sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
        }

        return sum;
    }
    
}
