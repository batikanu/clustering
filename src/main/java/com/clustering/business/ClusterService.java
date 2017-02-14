package com.clustering.business;

import java.util.List;

import com.clustering.domain.SignObservation;

public interface ClusterService {

	public List<SignObservation> readClusters();			
	public void addObservation(String signObservationStr);
}
