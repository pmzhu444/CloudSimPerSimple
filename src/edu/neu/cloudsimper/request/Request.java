package edu.neu.cloudsimper.request;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper.Component;
import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.Datacenter;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.plugin.PluginFactoryRequest;
import edu.neu.cloudsimper.region.Region;
import edu.neu.cloudsimper.region.RegionManger;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class Request extends Component {

	private int length;
	private int deadline;
	private Region region;

	private RequestGenerator generator;
	private RequestDispatcher dispatcher;
	private RequestRandomization rdm;

	private int executeCount;

	private Request(MetaContainer container) {
		this.name = container.getName();
		this.length = Integer.parseInt(container.getAttribute(Const.V_LENGTH));
		this.deadline = Integer.parseInt(container.getAttribute(Const.V_DEADLINE));
		this.region = RegionManger.getRegion(container.getAttribute(Const.V_REGION));
		this.dispatcher = PluginFactory.cloudsimper.createRequestDispatcher();
		dispatcher.setRegion(this.region);
		PluginFactoryRequest factory = PluginFactory.request(container);
		this.rdm = factory.createRequestRandomization();
		this.generator = factory.createRequestGenerator();
		this.generator.setRegion(this.region);
		this.generator.start();
		this.executeCount = 0;
	}

	public static List<Request> buildAll() {
		return RequestBuilder.buildAll();
	}

	public void initDispatcher(List<Datacenter> datacenters) {
		dispatcher.setDatacenters(datacenters);
	}

	public void runDispatch(int tick, List<Datacenter> datacenters) {
		dispatcher.dispatch(nextBatch(tick));
	}

	public long getLength() {
		return length;
	}

	public int getDeadLine() {
		return deadline;
	}

	public Region getRegion() {
		return region;
	}

	public int getExecuteCount() {
		return executeCount;
	}

	private RequestCore asCore() {
		int length = rdm.randomized(this.length);
		RequestCore core = new RequestCore(length, deadline, name + executeCount);
		executeCount++;
		return core;
	}

	private List<RequestCore> nextBatch(int time) {
		int size = generator.nextBatchSize(time);
		List<RequestCore> requests = new ArrayList<RequestCore>(size);
		for (int i = 0; i < size; i++) {
			requests.add(this.asCore());
		}
		return requests;
	}

	private static class RequestBuilder {
		private static List<Request> buildAll() {
			List<Request> requests = new ArrayList<Request>();
			List<MetaContainer> requestMeta = MetaManager.getRequests();
			for (MetaContainer metaContainer : requestMeta) {
				requests.add(new Request(metaContainer));
			}
			return requests;
		}
	}
}
