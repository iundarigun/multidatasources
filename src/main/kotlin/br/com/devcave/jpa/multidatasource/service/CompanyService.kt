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
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {

    @Transactional(transactionManager = "mysqlTransactionManager", readOnly = true)
    fun findAll(): List<Company> = companyRepository.findAll()

    @Transactional(transactionManager = "mysqlTransactionManager")
    fun create() {
        companyRepository.save(
            Company(
                name = UUID.randomUUID().toString(),
                countEmployees = Random(System.currentTimeMillis()).nextInt(2, 100)
            )
        )
    }

    @Transactional(transactionManager = "mysqlTransactionManager")
    fun createWithError() {
        val random = Random(System.currentTimeMillis())
        employeeRepository.save(
            Employee(
                name = "Name from company Exception ${random.nextInt(1, 200)}",
                document = "${random.nextInt(1_000_000, 9_999_999)}"
            )
        )
        companyRepository.save(
            Company(
                name = "Name from Company Exception " + UUID.randomUUID().toString(),
                countEmployees = random.nextInt(2, 100)
            )
        )
        if (LocalDate.now().isAfter(LocalDate.of(2020,10,10))) {
            throw RuntimeException("Invalid operation")
        }
    }
}