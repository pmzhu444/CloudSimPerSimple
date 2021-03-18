package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaManager;

public interface PluginFactory {

	public static PluginFactoryCloudSimPer cloudsimper = new PluginFactoryCloudSimPer();
	public static PluginFactoryDatacenter datacenter = new PluginFactoryDatacenter();
	public static PluginFactoryEnergy energy = new PluginFactoryEnergy();
	public static PluginFactoryHost host = new PluginFactoryHost();
	public static PluginFactoryRequest request = new PluginFactoryRequest();

	public static PluginFactoryCloudSimPer cloudsimper() {
		return cloudsimper.createFacorty(MetaManager.getPluginInfor(Const.P_REQUEST_DISPATCHER));
	}

	public static PluginFactoryDatacenter datacenter(MetaContainer container) {
		return datacenter.createFacorty(container);
	}

	public static PluginFactoryEnergy energy(MetaContainer container) {
		return energy.createFacorty(container);
	}

	public static PluginFactoryHost host(MetaContainer container) {
		return host.createFacorty(container);
	}

	public static PluginFactoryRequest request(MetaContainer container) {
		return request.createFacorty(container);
	}
	
	public PluginFactory createFacorty(MetaContainer container);

}
