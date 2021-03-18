package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.meta.MetaContainer;

public class PluginFactoryDatacenter extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryDatacenter() {
	}

	@Override
	public PluginFactoryDatacenter createFacorty(MetaContainer container) {
		return (PluginFactoryDatacenter) super.createFacorty(container);
	}

}
