package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.energy.EnergyPrice;
import edu.neu.cloudsimper.energy.EnergyGenerator;
import edu.neu.cloudsimper.meta.MetaContainer;

public class PluginFactoryEnergy extends PluginFactoryAbstract implements PluginFactory {
	protected PluginFactoryEnergy() {
	}

	@Override
	public PluginFactoryEnergy createFacorty(MetaContainer container) {
		return (PluginFactoryEnergy) super.createFacorty(container);
	}

	public EnergyGenerator createEnergyGenerator() {
		prepare(Const.P_ENERGY_GENERATOR);
		createInstance();
		setPluginAttribute();
		return (EnergyGenerator) this.instance;
	}
	
	public EnergyPrice createEnergyPrice() {
		prepare(Const.P_ENERGY_PRICE);
		createInstance();
		setPluginAttribute();
		return (EnergyPrice) this.instance;
	}
}
