package com.clustering.business;

import java.util.List;

import com.clustering.domain.SignObservationPojo;

/**
 * Business logic for clusters 
 */
public interface ClusterService {

	/**
	 * Constructs clusters for all observations and returns computed signs for
	 * each cluster
	 * 
	 * @return
	 */
	public List<SignObservationPojo> clusterObservations();

	/**
	 * Constructs clusters for observations filtered by given distance circle
	 * and returns computed signs for each cluster
	 * 
	 * @param lat
	 * @param lon
	 * @param radius
	 *            Radius in meters
	 * @return
	 */
	public List<SignObservationPojo> clusterObservationsByDistance(double lat, double lon, double radius);

	/**
	 * Adds an observation to the repository
	 * 
	 * @param signObservationStr
	 *            String form of the observation to be parsed
	 */
	public void addObservation(String signObservationStr);
}
