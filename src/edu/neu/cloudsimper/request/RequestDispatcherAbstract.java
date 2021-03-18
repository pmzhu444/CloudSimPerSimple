package edu.neu.cloudsimper.request;

import java.util.List;

import edu.neu.cloudsimper.Datacenter;
import edu.neu.cloudsimper.region.Region;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public abstract class RequestDispatcherAbstract implements RequestDispatcher {

	protected List<Datacenter> datacenters;
	protected List<RequestCore> requests;
	protected Region region;

	@Override
	public final void setDatacenters(List<Datacenter> datacenters) {
		this.datacenters = datacenters;

	}

	@Override
	public final void setRegion(Region region) {
		this.region = region;
	}

}