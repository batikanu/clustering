package com.clustering.persistence;


import java.util.List;

import com.clustering.domain.SignObservation;


public interface ClusterStore {

	public List<SignObservation> getClusters();
	public void insertObservation(SignObservation observation);
}
