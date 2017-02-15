package com.clustering.domain;

public class SignObservationPojo {

	private double lat;
	private double lon;
	private double heading;
	private SignType type;
	private Integer speed;
	
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public SignType getType() {
		return type;
	}
	public void setType(SignType type) {
		this.type = type;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	/**
	 * Creates new instance
	 * @param lat
	 * @param lon
	 * @param heading
	 * @param type
	 * @param speed
	 * @return
	 */
	public static SignObservationPojo create(double lat, double lon, double heading, SignType type, Integer speed) {
		SignObservationPojo instance = new SignObservationPojo();
		instance.setLat(lat);
		instance.setLon(lon);
		instance.setHeading(heading);
		instance.setType(type);
		instance.setSpeed(speed);
		return instance;	
	}
	
}
