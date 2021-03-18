package edu.neu.cloudsimper.energy;

public class EnergyGeneratorEmpty extends EnergyGeneratorAbstract {
	@Override
	public double nextEnergy(int duration) {
		return 0;
	}

	@Override
	public void start() {
		
	}

}
