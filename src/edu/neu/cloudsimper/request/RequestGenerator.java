package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.region.Region;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public interface RequestGenerator {
	
	public void start();
	
	public int nextBatchSize(int duration);

	public void setRegion(Region region);

	public String getName();

}