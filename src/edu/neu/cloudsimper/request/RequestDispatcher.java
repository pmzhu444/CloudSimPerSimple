package edu.neu.cloudsimper.request;

import java.util.List;

import edu.neu.cloudsimper.Datacenter;
import edu.neu.cloudsimper.region.Region;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public interface RequestDispatcher {
	
	public void setDatacenters(List<Datacenter> datacenters);

	public void setRegion(Region region);

	public void dispatch(List<RequestCore> requests);

}