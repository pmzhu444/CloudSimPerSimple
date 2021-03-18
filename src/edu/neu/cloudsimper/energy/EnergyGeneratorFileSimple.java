package edu.neu.cloudsimper.energy;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class EnergyGeneratorFileSimple extends EnergyGeneratorFile implements EnergyGenerator {

	@Override
	protected double parseEnergy(String line) {
		return Double.parseDouble(line);
	}

}
