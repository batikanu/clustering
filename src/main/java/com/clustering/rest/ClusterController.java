package com.clustering.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.clustering.business.ClusterService;
import com.clustering.utils.JsonConverter;

@Path("/")
@Singleton
public class ClusterController {

	@Inject
	ClusterService service;

	/**
	 * Retrieves all clusters. Example call http://hostname:port/clustering/all
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public Response getClusters() {
		try {
			return Response.status(200).entity(JsonConverter.convert(service.clusterObservations())).build();
		} catch (IllegalArgumentException e) {
			return Response.status(406).entity("Invalid request parameters").build();
		} catch (Exception e) {
			return Response.serverError().build();
		}

	}

	/**
	 * Retrieves clusters for given location and radius. Example call: Example
	 * call: http://hostname:port/clustering/?lat=48.1171&lon=11.3754&radius=100
	 * 
	 * @param lat
	 *            Latitude
	 * @param lon
	 *            Longitude
	 * @param radius
	 *            Radius in meters
	 * @return
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClustersByLocation(@QueryParam("lat") double lat, @QueryParam("lon") double lon,
			@QueryParam("radius") double radius) {

		try {
			String jsonStr = JsonConverter.convert(service.clusterObservationsByDistance(lat, lon, radius));
			return Response.status(200).entity(jsonStr).build();
		} catch (IllegalArgumentException e) {
			return Response.status(406).entity("Invalid request parameters").build();
		} catch (Exception e) {
			return Response.serverError().build();
		}

	}

	/**
	 * Adds a new observation Example call: POST http://hostname:port/clustering
	 * 
	 * @param signObservationStr
	 *            sign observation in string form as "lat,long,heading,sign
	 *            type, speed"
	 * @return
	 */
	@POST
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response addSignObservation(String signObservationStr) {

		try {
			service.addObservation(signObservationStr);
		} catch (IllegalArgumentException e) {
			return Response.status(406).entity("Invalid request parameters").build();
		} catch (Exception e) {
			return Response.serverError().build();
		}

		return Response.ok(signObservationStr).build();
	}

}