package br.com.devcave.jpa.multidatasource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultidatasourceApplication

fun main(args: Array<String>) {
	runApplication<MultidatasourceApplication>(*args)
}
