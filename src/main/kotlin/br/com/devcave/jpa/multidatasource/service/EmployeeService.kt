package br.com.devcave.jpa.multidatasource.service

import br.com.devcave.jpa.multidatasource.domain.mysql.Company
import br.com.devcave.jpa.multidatasource.domain.mysql.CompanyRepository
import br.com.devcave.jpa.multidatasource.domain.postgres.Employee
import br.com.devcave.jpa.multidatasource.domain.postgres.EmployeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val companyRepository: CompanyRepository
) {

    @Transactional(transactionManager = "jtaTransactionManager", readOnly = true)
    fun findAll(): List<Employee> = employeeRepository.findAll()

    @Transactional(transactionManager = "jtaTransactionManager")
    fun create() {
        val random = Random(System.currentTimeMillis())
        employeeRepository.save(
            Employee(
                name = "Name ${random.nextInt(1, 200)}",
                document = "${random.nextInt(1_000_000, 9_999_999)}"
            )
        )
    }

    @Transactional(transactionManager = "jtaTransactionManager")
    fun createWithError() {
        val random = Random(System.currentTimeMillis())
        employeeRepository.save(
            Employee(
                name = "Name from employee Exception ${random.nextInt(1, 200)}",
                document = "${random.nextInt(1_000_000, 9_999_999)}"
            )
        )
        companyRepository.save(
            Company(
                name = "Name from employee Exception " + UUID.randomUUID().toString(),
                countEmployees = random.nextInt(2, 100)
            )
        )
        if (LocalDate.now().isAfter(LocalDate.of(2020,10,10))) {
            throw RuntimeException("Invalid operation")
        }
    }
}