package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.region.Location;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public interface EnergyGenerator {
	
	public void start();
	
	public double nextEnergy(int duration);

	public String getName();

	public void setLocation(Location location);
}