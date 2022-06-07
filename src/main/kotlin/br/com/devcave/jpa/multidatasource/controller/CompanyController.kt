package br.com.devcave.jpa.multidatasource.controller

import br.com.devcave.jpa.multidatasource.domain.mysql.Company
import br.com.devcave.jpa.multidatasource.service.CompanyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("companies")
class CompanyController(
    private val companyService: CompanyService
) {

    @GetMapping
    fun findAll(): List<Company> = companyService.findAll()

    @PostMapping
    fun create() {
        companyService.create()
    }

    @PostMapping("with-error")
    fun createWithError() {
        companyService.createWithError()
    }
}