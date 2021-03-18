package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.Component;
import edu.neu.cloudsimper.region.Region;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public abstract class RequestGeneratorAbstract extends Component implements RequestGenerator {

	protected Region region;
	
	public RequestGeneratorAbstract() {
	}
	public final void setRegion(Region region) {
		this.region = region;
	}
}
