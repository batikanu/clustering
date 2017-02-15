package com.clustering.persistence;


import java.util.List;

import com.clustering.domain.SignObservation;
import com.clustering.domain.SignType;


public interface ClusterStore {

	/**
	 * Retrieves all stored observations
	 * @return
	 */
	public List<SignObservation> retrieveAllObservations();
	/**
	 * Retrieves observations filtered by SigType
	 * @param type
	 * @return
	 */
	public List<SignObservation> retrieveObservationsByType(SignType type);
	/**
	 * Stores a new observation
	 * @param observation
	 */
	public void insertObservation(SignObservation observation);
}
