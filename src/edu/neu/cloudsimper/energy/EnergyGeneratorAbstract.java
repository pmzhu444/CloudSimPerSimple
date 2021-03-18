package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.Component;
import edu.neu.cloudsimper.region.Location;

public abstract class EnergyGeneratorAbstract extends Component implements EnergyGenerator {

	protected Location location;

	@Override
	public final void setLocation(Location location) {
		this.location = location;
	}

}
