package edu.neu.cloudsimper.power;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class PowerModelLogarithm extends PowerModelContinue implements PowerModel {

	private double base;
	private double constant;

	@Override
	public double getPower(double utilization) {
		//System.out.println("log" + (this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base))));

		if (utilization == 0) {
			return this.idle;
		}
		return this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base));
	}

	public void setBase(double base) {
		this.base = base;
		this.constant = (max - idle) / (Math.log(100) / Math.log(this.base));
	}
}
