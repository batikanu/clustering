package com.clustering.business;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import com.clustering.persistence.ClusterStore;
import com.clustering.utils.ClusteringHelpers;
import com.clustering.domain.POIDistanceMeasure;
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
		DBSCANClusterer<SignObservation> clusterer = new DBSCANClusterer<SignObservation>(EPSILON_NEIGBOURHOOD, MIN_PTS, new POIDistanceMeasure());
		List<Cluster<SignObservation>> clusters = clusterer.cluster(null);
		
		System.out.println("Clustering finished.");
		for(Cluster<SignObservation> singleCluster: clusters){
			//compute cluster center
			double[] clusterCenter = ClusteringHelpers.computeGeoMidAndHeadingMidPoint(singleCluster);
			
			System.out.println("CLUSTER START: " + clusterCenter[0] + ", " + clusterCenter[1] + " heading: " + clusterCenter[2]);
			for(SignObservation signObservation: singleCluster.getPoints()){
				System.out.println(signObservation.getPoint()[0] + "," + signObservation.getPoint()[1] + " heading: " + signObservation.getHeading());
			}
		}
		
		return store.getClusters();
	}

	@Override
	public void addObservation(String signObservationStr) {
		store.insertObservation(SignObservation.toSignObservation(signObservationStr));
	}
	
	private 

}
