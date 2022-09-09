package br.com.devcave.jpa.multidatasource.domain.postgres

import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository :JpaRepository<Employee, Long> {
}