package com.clustering.utils;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.stat.descriptive.MultivariateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import com.clustering.domain.SignObservation;


public class ClusteringHelpers {	
	/**
	 * Computing the mid point of several lat/lng observations along with heading.
	 * 
	 * The midpoint of the lat long observation is computed by converting the lat / lng coordinates
	 * to a three dimensional space, then computing the midpoint and then back to the 2 dimensional lat/lng
	 * space.
	 * 
	 * Heading midpoint is computed as simple mean 1d.
	 * 
	 * @param c The cluster of sign observations to compute the midpoint for.
	 * @return The midpoint in a three dimensional double array, with first dimension latitude, second longitude and third heading-
	 */
	public static double[] computeGeoMidAndHeadingMidPoint(Cluster<SignObservation> c){
		//convert each point to radians and save heading for later
		double[][] latLonAsRadians = new double[c.getPoints().size()][2];
		double headingObservations[] = new double[c.getPoints().size()];
		for(int i = 0; i < c.getPoints().size(); i++){
			SignObservation s = c.getPoints().get(i);
			latLonAsRadians[i][0] = Math.toRadians(s.getPoint()[0]);
			latLonAsRadians[i][1] = Math.toRadians(s.getPoint()[1]);
			headingObservations[i] = s.getHeading();
		}
		
		//now move to xyz coordinates to compute midpoint
		List<double[]> xyzPoints = new LinkedList<double[]>();
		for(double[] latLngRadiansPoint: latLonAsRadians){
			double x = Math.cos(latLngRadiansPoint[0]) * Math.cos(latLngRadiansPoint[1]);
			double y = Math.cos(latLngRadiansPoint[0]) * Math.sin(latLngRadiansPoint[1]);
			double z = Math.sin(latLngRadiansPoint[0]);
			xyzPoints.add(new double[]{x,y,z});
		}
		
		//compute midpoint in xyz space
		MultivariateSummaryStatistics stats = new MultivariateSummaryStatistics(3,true);
		for(double[] xyzPoint: xyzPoints){
			stats.addValue(xyzPoint);
		}
		
		double[] geometricMean = stats.getMean();
		
		//now we have to back project this into the lat / lon space
		double lonMidpointRad = Math.atan2(geometricMean[1], geometricMean[0]);
		//sqrt(x^2 + y^2)
		double hyperbola = Math.sqrt(Math.pow(geometricMean[0],2) +  Math.pow(geometricMean[1],2));
		double latMidpointRad = Math.atan2(geometricMean[2], hyperbola);
		
		//back convert from radians to degree
		double latMidpointDeg = Math.toDegrees(latMidpointRad);
		double lonMidpointDeg = Math.toDegrees(lonMidpointRad);

		//compute simple mean of heading
		Mean meanComputer = new Mean();
		double headingMean = meanComputer.evaluate(headingObservations);
		
		return new double[]{latMidpointDeg, lonMidpointDeg, headingMean};
	}
}