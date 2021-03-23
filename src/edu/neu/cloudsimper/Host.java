package edu.neu.cloudsimper;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaDatacenter;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.power.PowerModel;
import edu.neu.cloudsimper.request.RequestCore;

/**
 * @author Jie SONG <songjie@mail.neu.edu.cn>
 */
public class Host extends Component implements SimPerEntity {

	private int peNumber;
	private int mips;
	private PowerModel powerModel;
	private Datacenter datacenter;

	private double usage;
	private int count;

	private int usedCapacity;

	private Host(MetaContainer container, int index) {
		this.name = container.getName() + index;
		this.peNumber = Integer.parseInt(container.getAttribute(Const.V_PES_NUMBER));
		this.mips = Integer.parseInt(container.getAttribute(Const.V_MIPS));
		this.powerModel = PluginFactory.host(container).createPowerModel();
		this.usage = 0;
		this.count = 0;
		this.usedCapacity = 0;
	}

	public static List<Host> bulid4Datacenter(MetaDatacenter container) {
		return HostBuilder.bulid4Datacenter(container);
	}

	@Override
	public void runTick(int tick, LogUnit unit) {
		List<RequestCore> queue = datacenter.getQueue();
		int capacity = getCapacity() * tick;
		this.count = 0;
		while (!queue.isEmpty()) {
			int last = queue.size() - 1;
			RequestCore request = queue.get(last);
			int required = (int) Math.ceil(request.getLength() / request.getDeadline());
			if (required <= capacity) {
				capacity -= required;
				queue.remove(last);
				this.count++;
			} else {
				break;
			}
		}
		this.usedCapacity = getCapacity() * tick - capacity;
		this.usage = (double)usedCapacity / (getCapacity() * tick);
//		if (this.datacenter.getName().equals("DC_OS")) {
//			System.out.println(this.usage);
//		}
		unit.setFinishedRequestNum(count);
		unit.setDcConsume(this.getPower() * tick / 1000);
	}

	public Datacenter getDatacenter() {
		return this.datacenter;
	}

	public void setDatacenter(Datacenter datacenter) {
		this.datacenter = datacenter;
	}

	public int getUsedCapacity() {
		return this.usedCapacity;
	}

	public double getUsage() {
		return this.usage;
	}

	public int getRequestCount() {
		return this.count;
	}

	public double getPower() {
		return powerModel.getPower(usage);
	}

	public int getCapacity() {
		return mips * peNumber;
	}

	private static class HostBuilder {
		private static List<Host> bulid4Datacenter(MetaDatacenter container) {
			List<Host> hosts = new ArrayList<Host>();
			List<MetaContainer> metaDcHosts = container.getHosts();
			for (MetaContainer metaDcHost : metaDcHosts) {
				String name = metaDcHost.getName();
				int size = metaDcHost.getSize();
				MetaContainer metaHost = MetaManager.getHost(name);
				for (int i = 1; i <= size; i++) {
					hosts.add(new Host(metaHost, i));
				}
			}
			return hosts;
		}
	}
}
