package br.com.devcave.jpa.multidatasource.configuration

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Configuration

@Configuration
class HibernateConfiguration(
    private val hibernateProperties: HibernateProperties,
    private val jpaProperties:JpaProperties,
) {
    fun vendorProperties(dialect: String?): MutableMap<String, Any> {
        return this.hibernateProperties.determineHibernateProperties(
            jpaProperties.properties,
            HibernateSettings()
        ).also {
            it["hibernate.dialect"] = dialect
        }
    }
}