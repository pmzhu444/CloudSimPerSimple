package edu.neu.cloudsimper.energy;

import java.util.Random;

import edu.neu.cloudsimper.CloudSimPer;

public class EnergyPriceFunction extends EnergyPriceAbstract implements EnergyPrice {

	private double p0;
	private double pm;
	private double t;

	public void setP0(double p0) {
		this.p0 = p0;
	}

	public void setPm(double pm) {
		this.pm = pm;
	}

	@Override
	public double calculate(double energy) {
		this.t = (CloudSimPer.getClock() % (3600 * 24) / 3600);
		double ew = pm / p0;
		double cost = pm * (ew + Math.exp(1) * t);
		return cost * energy;
	}

}
