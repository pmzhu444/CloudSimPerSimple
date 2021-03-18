package edu.neu.cloudsimper.power;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class PowerModelLinear extends PowerModelContinue implements PowerModel {

	@Override
	public double getPower(double utilization) {
		//System.out.println("lin" + (this.idle + (max - idle)* utilization));
		return this.idle + (max - idle)* utilization ;
	}

}
