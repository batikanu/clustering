package com.clustering.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.clustering.business.ClusterService;


@Path("/")
@Singleton
public class ClusterController {

	@Inject
	ClusterService service;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClusters() {

		String output = "<h1>Hello World!<h1>" + "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString()
				+ "</p<br>";
		return Response.status(200).entity(output).build();
	}

	@POST
	@Consumes("text/plain")
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