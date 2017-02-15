package com.clustering.persistence;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.clustering.domain.SignObservation;
import com.clustering.domain.SignType;

/**
 * Mock persistence implementation. It doesn't contain real persistence. Data
 * is stored as a variable in the instance.  
 */
public class ClusterStoreImp implements ClusterStore {

	final static Logger logger = Logger.getLogger(ClusterStoreImp.class.getName());
	
	public ClusterStoreImp() {
		this.observationList = new LinkedList<SignObservation>();
	}

	private List<SignObservation> observationList;

	@Override
	public List<SignObservation> retrieveObservationsByType(SignType type) {
		if (type == null) {
			return observationList;
		}
		return observationList.stream().filter(x -> x.getType() == type).collect(Collectors.toList());
	}

	@Override
	public void insertObservation(SignObservation observation) {
		observationList.add(observation);
		logger.info("New sign observation inserted - lat: " + observation.getPoint()[0] + " lon: " + observation.getPoint()[1] + " heading: " + observation.getHeading() + " type: " + observation.getType() + " speed: " +  observation.getSpeed());
	}

	@Override
	public List<SignObservation> retrieveAllObservations() {
		return observationList;
	}

}
