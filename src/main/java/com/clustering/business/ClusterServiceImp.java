package com.clustering.business;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import com.clustering.persistence.ClusterStore;
import com.clustering.utils.ClusteringHelpers;
import com.clustering.domain.POIDistanceMeasure;
import com.clustering.domain.SignObservation;
import com.clustering.domain.SignObservationPojo;
import com.clustering.domain.SignType;

public class ClusterServiceImp implements ClusterService {

	@Inject
	ClusterStore store;

	// DBScan Epsilon Neighbourhood - 100m should be generous enough.
	private double EPSILON_NEIGBOURHOOD = 0.1;
	// We set minPts to 0 to allow clusters of single points.
	private int MIN_PTS = 0;

	@Override
	public List<SignObservationPojo> clusterObservations() {
		List<SignObservationPojo> clusteredObservations = new LinkedList<SignObservationPojo>();
		for (SignType type : SignType.values()) {
			clusteredObservations.addAll(constructClusteredSigns(store.retrieveObservationsByType(type)).stream()
					.map(x -> convertToPojo(x)).collect(Collectors.toList()));
		}

		return clusteredObservations;
	}

	@Override
	public List<SignObservationPojo> clusterObservationsByDistance(double lat, double lon, double radius) {
		boolean isInValid = lat <= 0 || lon <= 0 || radius <= 0;
		if (isInValid) {
			throw new IllegalArgumentException();
		}

		return filterSignObservationsByDistance(clusterObservations(), lat, lon, radius);
	}

	@Override
	public void addObservation(String signObservationStr) {
		store.insertObservation(SignObservation.toSignObservation(signObservationStr));
	}

	/**
	 * Constructs clusters and retrieves computed signs of these clusters
	 * 
	 * @param observations
	 * @return
	 */
	private List<SignObservation> constructClusteredSigns(List<SignObservation> observations) {

		DBSCANClusterer<SignObservation> clusterer = new DBSCANClusterer<SignObservation>(EPSILON_NEIGBOURHOOD, MIN_PTS,
				new POIDistanceMeasure());
		List<Cluster<SignObservation>> clusters = clusterer.cluster(observations);

		System.out.println("Clustering finished.");

		List<SignObservation> clusterSigns = new LinkedList<SignObservation>();

		for (Cluster<SignObservation> singleCluster : clusters) {
			// compute cluster center
			double[] clusterCenter = ClusteringHelpers.computeGeoMidAndHeadingMidPoint(singleCluster);

			// Assuming that every sign in the cluster is consistent with speed.
			// Otherwise most popular speed can be taken as cluster speed.
			SignObservation sampleClusterObservation = singleCluster.getPoints().get(0);
			Integer clusterSpeed = sampleClusterObservation.getSpeed();
			SignType clusterType = sampleClusterObservation.getType();

			System.out.println(
					"CLUSTER START: " + clusterCenter[0] + ", " + clusterCenter[1] + " heading: " + clusterCenter[2]);

			clusterSigns.add(new SignObservation(clusterCenter[0], clusterCenter[1], clusterCenter[2], clusterType,
					clusterSpeed));

			for (SignObservation signObservation : singleCluster.getPoints()) {
				System.out.println(signObservation.getPoint()[0] + "," + signObservation.getPoint()[1] + " heading: "
						+ signObservation.getHeading() + "type: " + signObservation.getType() + " speed: "
						+ signObservation.getSpeed());
			}
		}
		return clusterSigns;
	}

	/**
	 * Filters given observations by given distance circle determined by lat,
	 * lon and radius
	 * 
	 * @param observations
	 *            List to be filtered
	 * @param lat
	 *            Latitude
	 * @param lon
	 *            Longitude
	 * @param radius
	 *            Radius of the position given in meters
	 * @return
	 */
	private List<SignObservationPojo> filterSignObservationsByDistance(List<SignObservationPojo> observations,
			double lat, double lon, double radius) {

		POIDistanceMeasure distanceMeasure = new POIDistanceMeasure();
		double[] referencePoint = new double[] { lat, lon };
		return observations.stream()
				.filter(x -> distanceMeasure.computeDistance(new double[]{ x.getLat(), x.getLon()}, referencePoint) <= radius)
				.collect(Collectors.toList());

	}

	private static SignObservationPojo convertToPojo(SignObservation observation) {
		return SignObservationPojo.create(observation.getPoint()[0], observation.getPoint()[1],
				observation.getPoint()[2], observation.getType(), observation.getSpeed());
	}

}
