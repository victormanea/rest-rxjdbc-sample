package com.vma.restrxjdbc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vma.restrxjdbc.model.Employee;
import com.vma.restrxjdbc.repository.EmployeeRepository;

@Component
@Path("/employees")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeRepository repository;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll() {
		final List<Employee> result = repository.getAll().toStream().collect(Collectors.toList());
		logger.debug("Received [{}]", result.toString());
		
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("{firstName}/{lastName}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response find(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName) {
		final Employee result = repository.get(firstName, lastName).block();
		logger.debug("Received [{}]", result.toString());
		
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response find(@PathParam("id") String id) {
		final Employee result = repository.get(id).block();
		logger.debug("Received [{}]", result.toString());
		
		return Response.status(Status.OK).entity(result).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response create(Employee employee) {
		final Employee result = repository.create(employee).block();
		logger.debug("Received [{}]", result.toString());
		
		return Response.status(Status.OK).entity(result).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delete(@PathParam("id") String id) {
		repository.delete(id).block();
		logger.debug("Employee id [{}] was deleted", id);
		
		return Response.status(Status.OK).build();
	}
}
