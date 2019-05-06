package com.vma.restrxjdbc.repository;

import org.davidmoten.rx.jdbc.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vma.restrxjdbc.model.Department;

import io.reactivex.Flowable;
import reactor.core.publisher.Flux;

@Component
public class DepartmentRepository {

	@Autowired(required=true)
	private Database database;

	public DepartmentRepository() {
	}

	public Flux<Department> getAll() {
		final String sql = "SELECT * FROM department";

		Flowable<Department> departmentFlowable = database.select(sql)
				.get(rs -> {
			Department department = new Department();
			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));

			return department;
		});

		return Flux.from(departmentFlowable);
	}
}
