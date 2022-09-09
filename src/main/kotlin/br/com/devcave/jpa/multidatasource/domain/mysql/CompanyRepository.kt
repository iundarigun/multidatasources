package br.com.devcave.jpa.multidatasource.domain.mysql

import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, Long>