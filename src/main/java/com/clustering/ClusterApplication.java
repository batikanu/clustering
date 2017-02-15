package com.clustering;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.clustering.ClusterApplicationBinder;


@ApplicationPath("/")
public class ClusterApplication extends ResourceConfig {
	public ClusterApplication(){
							
		register(new ClusterApplicationBinder());
		packages("com.clustering.rest");
	}


	
}
