package com.clustering.domain;

import org.apache.commons.math3.ml.clustering.Clusterable;

/**
 * A sign observation represents an observation of a geometric point defined by
 * latitude, longitude and heading in degreee.
 * 
 * This class is designed to be used as a model class for clustering in apache
 * commons math.
 */
public class SignObservation implements Clusterable {
	/**
	 * The coordinates array contains the coordinates used in clustering. It
	 * will be filled as
	 * 
	 * [0]: latitude [1]: longitude [2]: heading
	 * 
	 * The heading can't be stored separately since apache commons clustering
	 * won't give it to the Distance measure instead.
	 */
	protected final double[] coordinates;

	public double getHeading() {
		if (coordinates == null || coordinates.length != 3) {
			throw new NumberFormatException(
					"The coordinates stored in this SignObservation object do not comply to the lat,lon,heading specification!");
		}
		return coordinates[2];
	}

	private Integer speed;
	private SignType type;

	@Override
	public double[] getPoint() {
		return coordinates;
	}

	public SignObservation(double lat, double lon, double heading, SignType type, Integer speed) {
		super();
		this.coordinates = new double[] { lat, lon, heading };
		this.type = type;
		this.speed = speed;
	}

	public Integer getSpeed() {
		return speed;
	}

	public SignType getType() {
		return type;
	}

	public static SignObservation toSignObservation(String signObservationStr) {
		if (signObservationStr == null) {
			throw new IllegalArgumentException();
		}
		String[] values = signObservationStr.split(",");
		if (values.length < 4) {
			throw new IllegalArgumentException();
		}

		try {

			return new SignObservation(Double.parseDouble(values[0]), Double.parseDouble(values[1]),
					Double.parseDouble(values[2]), SignType.valueOf(values[3]),
					values.length == 5 ? Integer.parseInt(values[4]) : null);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

	}

}
