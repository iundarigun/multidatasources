package br.com.devcave.jpa.multidatasource.controller

import br.com.devcave.jpa.multidatasource.domain.postgres.Employee
import br.com.devcave.jpa.multidatasource.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @GetMapping
    fun findAll(): List<Employee> = employeeService.findAll()

    @PostMapping
    fun create() {
        employeeService.create()
    }

    @PostMapping("with-error")
    fun createWithError() {
        employeeService.createWithError()
    }
}