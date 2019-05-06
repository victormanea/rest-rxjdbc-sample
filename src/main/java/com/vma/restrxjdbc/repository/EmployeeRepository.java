package com.vma.restrxjdbc.repository;

import org.davidmoten.rx.jdbc.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vma.restrxjdbc.model.Employee;

import io.reactivex.Flowable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeRepository {

	@Autowired(required=true)
	private Database database;

	public EmployeeRepository() {
	}

	public Flux<Employee> getAll() {
		final String sql = "SELECT * FROM employee e JOIN department d ON e.department_id = d.department_id";

		Flowable<Employee> employeeFlowable = database.select(sql)
				.get(rs -> {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("employee_firstname"));
			employee.setLastName(rs.getString("employee_lastname"));
			employee.setDepartment(rs.getString("department_name"));

			return employee;
		});

		return Flux.from(employeeFlowable);
	}

	public Mono<Employee> get(String firstName, String lastName) {
		String sql = "SELECT employee_id, employee_firstname, employee_lastname, department_name FROM employee e "
				+ "JOIN department d ON e.department_id = d.department_id " + "WHERE employee_firstname = ? AND "
				+ "employee_lastname = ?";

		Flowable<Employee> employeeFlowable = database.select(sql).parameters(firstName, lastName).get(rs -> {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("employee_firstname"));
			employee.setLastName(rs.getString("employee_lastname"));
			employee.setDepartment(rs.getString("department_name"));

			return employee;
		});

		return Mono.from(employeeFlowable);
	}
	
	public Mono<Employee> get(String id) {
		String sql = "SELECT employee_id, employee_firstname, employee_lastname, department_name FROM employee e "
				+ "JOIN department d ON e.department_id = d.department_id " + "WHERE employee_id = ?";

		Flowable<Employee> employeeFlowable = database.select(sql).parameters(id).get(rs -> {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("employee_firstname"));
			employee.setLastName(rs.getString("employee_lastname"));
			employee.setDepartment(rs.getString("department_name"));

			return employee;
		});

		return Mono.from(employeeFlowable);
	}

	public Mono<Employee> create(Employee employee) {

		String createSql = "INSERT INTO employee (employee_firstname, employee_lastname, department_id) VALUES (?, ?, ?)";
		String selectDepartmentId = "SELECT department_id from department where department_name = ?";
		String selectSql = "SELECT employee_id, employee_firstname, employee_lastname, department_name FROM employee e "
				+ "JOIN department d ON e.department_id = d.department_id " + "WHERE employee_id = ?";

		return Mono.just(employee).flatMap(newEmployee -> {
			Flowable<Integer> employeeIds = database.select(selectDepartmentId).parameters(newEmployee.getDepartment())
					.getAs(Integer.class)
					.flatMap(departmentId -> database.update(createSql)
							.parameters(newEmployee.getFirstName(), newEmployee.getLastName(), departmentId)
							.returnGeneratedKeys().getAs(Integer.class));

			Flowable<Employee> employeeFlowable = database.select(selectSql).parameterStream(employeeIds).get(rs -> {
				Employee result = new Employee();
				result.setId(rs.getInt("employee_id"));
				result.setFirstName(rs.getString("employee_firstname"));
				result.setLastName(rs.getString("employee_lastname"));
				result.setDepartment(rs.getString("department_name"));

				return result;
			});

			return Mono.from(employeeFlowable);
		});
	}

	public Mono<Void> delete(String id) {
		String sql = "DELETE FROM employee WHERE employee_id = ?";
		Flowable<Integer> counts = database.update(sql).parameter(id).counts();
		return Flux.from(counts).then();
	}

}
