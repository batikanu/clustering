package com.clustering;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.clustering.business.ClusterService;
import com.clustering.business.ClusterServiceImp;
import com.clustering.persistence.ClusterStore;
import com.clustering.persistence.ClusterStoreImp;


public class ClusterApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ClusterServiceImp.class).to(ClusterService.class);
		bind(ClusterStoreImp.class).to(ClusterStore.class);

	}

}
