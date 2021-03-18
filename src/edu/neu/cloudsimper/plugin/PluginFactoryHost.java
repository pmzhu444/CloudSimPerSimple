package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.power.PowerModel;

public class PluginFactoryHost extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryHost() {
	}

	@Override
	public PluginFactoryHost createFacorty(MetaContainer container) {
		return (PluginFactoryHost) super.createFacorty(container);
	}
	
	public PowerModel createPowerModel() {
		prepare(Const.P_POWER_MODEL);
		createInstance();
		setPluginAttribute();
		return (PowerModel) this.instance;
	}
}
