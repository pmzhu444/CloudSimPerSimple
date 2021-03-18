package edu.neu.cloudsimper.energy;

public class EnergyPriceConstant extends EnergyPriceAbstract implements EnergyPrice {

	@Override
	public double calculate(double energy) {
		return this.getPrice() * energy;
	}
}
