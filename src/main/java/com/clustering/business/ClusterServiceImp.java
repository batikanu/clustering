package com.clustering.business;

import java.util.List;

import javax.inject.Inject;

import com.clustering.persistence.ClusterStore;
import com.clustering.domain.SignObservation;

public class ClusterServiceImp implements ClusterService {

	@Inject
	ClusterStore store;

	// DBScan Epsilon Neighbourhood - 100m should be generous enough.
	private double EPSILON_NEIGBOURHOOD = 0.1;
	// We set minPts to 0 to allow clusters of single points.
	private int MIN_PTS = 0;

	@Override
	public List<SignObservation> readClusters() {
		return store.getClusters();
	}

	@Override
	public void addObservation(String signObservationStr) {
		store.insertObservation(SignObservation.toSignObservation(signObservationStr));
	}

}
