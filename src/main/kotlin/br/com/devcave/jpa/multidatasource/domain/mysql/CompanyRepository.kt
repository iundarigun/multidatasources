package br.com.devcave.jpa.multidatasource.domain.mysql

import br.com.devcave.jpa.multidatasource.domain.mysql.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, Long>