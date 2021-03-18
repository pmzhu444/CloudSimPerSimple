package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.request.RequestGenerator;
import edu.neu.cloudsimper.request.RequestRandomization;

public class PluginFactoryRequest extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryRequest() {
	}

	@Override
	public PluginFactoryRequest createFacorty(MetaContainer container) {
		return (PluginFactoryRequest) super.createFacorty(container);
	}

	public RequestGenerator createRequestGenerator() {
		prepare(Const.P_REQUEST_GENERATOR);
		createInstance();
		setPluginAttribute();
		return (RequestGenerator) this.instance;
	}

	public RequestRandomization createRequestRandomization() {
		prepare(Const.P_REQUEST_RANDOMIZATION);
		createInstance();
		setPluginAttribute();
		return (RequestRandomization) this.instance;
	}
}
