package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.Component;
import edu.neu.cloudsimper.Const;

public abstract class EnergyPriceAbstract extends Component implements EnergyPrice {

	private double price = Const.ENERGY_PRICE;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
