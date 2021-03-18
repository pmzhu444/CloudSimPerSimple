package edu.neu.cloudsimper.power;

public class PowerModelConst implements PowerModel {

	@Override
	public double getPower(double utilization) {
		return 100;
	}

}
