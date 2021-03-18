package edu.neu.cloudsimper.power;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class PowerModelExponent extends PowerModelContinue implements PowerModel {

	private double exp;
	protected double constant;

	@Override
	public double getPower(double utilization) {
		//System.out.println("exp:" + (this.idle + this.constant * Math.pow(utilization * 100, this.exp)));
		return this.idle + this.constant * Math.pow(utilization * 100, this.exp);
	}

	public void setExp(double exp) {
		this.exp = exp;
		this.constant = (max - idle) / Math.pow(100, exp);
	}
}
