package com.clustering.persistence;

import java.util.LinkedList;
import java.util.List;

import com.clustering.domain.SignObservation;

public class ClusterStoreImp implements ClusterStore {

	public ClusterStoreImp() {
		this.observationList = new LinkedList<SignObservation>();
	}

	private List<SignObservation> observationList;

	@Override
	public List<SignObservation> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertObservation(SignObservation observation) {
		this.observationList.add(observation);
	}

}
