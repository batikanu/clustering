package com.clustering.rest;

import java.util.Date;

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


@Path("/")
@Singleton
public class ClusterController {

	@Inject
	ClusterService service;
	

	/**
	 * Retrieves all clusters
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public Response getClusters() {

		String output = "<h1>Hello World!<h1>" + "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString()
				+ "</p<br>";
		return Response.status(200).entity(output).build();
	}
	
	/**
	 * Retrieves clusters for given location and radius
	 * Example call http://localhost:8080/clustering/?lat=48.1171&lon=11.3754&radius=100
	 * @param lat Latitude
	 * @param lon Longitude
	 * @param radius Radius in meters
	 * @return
	 */
	@GET
	@Consumes("text/plain")	
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getClustersByLocation(@QueryParam("lat") String lat, @QueryParam("lon") String lon, @QueryParam("radius") String radius) {

		String output = "<h1>Hello World!<h1>" + "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString()
				+ "</p<br>";
		return Response.status(200).entity(output).build();
	}

	
	/**
	 * Adds a new observation
	 * @param signObservationStr (lat,long,heading,sign type, speed)
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