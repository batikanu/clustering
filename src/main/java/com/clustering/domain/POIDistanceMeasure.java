package com.clustering.domain;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.distance.DistanceMeasure;


/**
 * This class implements a distance measure for geopoints based on latitude, longitude and heading.
 * 
 * The difference is determined in kilometers, based on the great circle distance. Points with a heading
 * difference that is too large will be assigned an infinity distance.
 * 
 */
public class POIDistanceMeasure implements DistanceMeasure{
	public static final int EARTH_RADIUS_KM = 6371;
	public static final double MAX_HEADING_DIFF = 70.0;
	
	@Override
	public double compute(double[] arg0, double[] arg1) throws DimensionMismatchException {
		//compute heading diff first and see if these points shall be clusterable.
		double headingA = arg0[2];
		double headingB = arg1[2];
		double headingDiff = headingDiff(headingA, headingB);
		
		//points shall only be clusterable if their heading diff is not too large
		if(headingDiff > MAX_HEADING_DIFF){
			return Double.MAX_VALUE;
		}	
		
		//otherwise compute haversine to determine distance between points
		return haversine(arg0[0], arg0[1], arg1[0], arg1[1]);
	}
	
	
	/**
	 * Compute the great circle distance between two points given by
	 * latitude and longitude in kilometers.
	 * 
	 * @param latA The latitude of the first point
	 * @param lonA The longitude of the first point
	 * @param latB The latitude of the second point
	 * @param lonB The longitude of the second point
	 * @return The great circle distance in km.
	 */
	private double haversine(double latA, double lonA, double latB, double lonB){
		double dLat = Math.toRadians(latB - latA);
		double dLon = Math.toRadians(lonB - lonA);
		double latARadians = Math.toRadians(latA);
		double latBRadians = Math.toRadians(latB);
		
		double sinLatSquare = Math.pow(Math.sin(dLat/2), 2);
		double a = sinLatSquare + Math.cos(latARadians) * Math.cos(latBRadians) * Math.pow(Math.sin(dLon/2),2);
		double c = 2 *  Math.asin(Math.sqrt(a));
		
		return EARTH_RADIUS_KM * c;
	}
	
	/**
	 * Computes the difference of two given headings in degree between 0 and 360. It does account
	 * for the headings making circulating around the 360deg as follows.
	 * 
	 * Examples from this method the differences in degrees are
     *   350deg to 10deg => 20
     *   170deg to 190deg => 20
     *   10deg to 5deg => 5
	 *
	 * @param headingA The heading as double in [0.0, 360.0]
	 * @param headingB The heading as double in [0.0, 360.0]
	 * @return The difference in heading as double in [0.0, 180.0]
	 */
	private double headingDiff(double headingA, double headingB){
		if(headingA > 360 || headingA < 0 || headingB > 360 || headingB < 0){
			throw new IllegalArgumentException("The headings given where not in between 0 and 360.");
		}
		
		double headingDiff = Math.abs(headingB-headingA) % 360.0;
		
		//if the distance circulated we need to apply correction.
		if(headingDiff > 180){
			headingDiff -= 180;
		}
		
		return Math.abs(headingDiff);
	}
}