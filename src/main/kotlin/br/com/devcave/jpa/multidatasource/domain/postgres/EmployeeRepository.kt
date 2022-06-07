package br.com.devcave.jpa.multidatasource.domain.postgres

import br.com.devcave.jpa.multidatasource.domain.postgres.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository :JpaRepository<Employee, Long> {
}