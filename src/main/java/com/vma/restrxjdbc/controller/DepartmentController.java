package com.vma.restrxjdbc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vma.restrxjdbc.model.Department;
import com.vma.restrxjdbc.repository.DepartmentRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/departments")
@Api(value = "Department API - it's all about department services", produces = "application/json")
public class DepartmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentRepository repository;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "Find all departments", response = Department.class, responseContainer = "java.util.List")  
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource found")
			})
	public Response findAll() {
		final List<Department> result = repository.getAll().toStream().collect(Collectors.toList());
		logger.debug("Received [{}]", result.toString());
		
		return Response.status(Status.OK).entity(result).build();
	}
}
