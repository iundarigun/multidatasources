package br.com.devcave.jpa.multidatasource.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["br.com.devcave.jpa.multidatasource.domain.postgres"],
    entityManagerFactoryRef = "postgresEntityManager",
    transactionManagerRef = "postgresTransactionManager"
)
class PostgresDatasourceConfiguration(
    private val hibernateConfiguration: HibernateConfiguration
) {
    @Bean
    @ConfigurationProperties("spring.postgres-datasource")
    fun postgresDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    fun postgresDataSource(): DataSource {
        return postgresDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    @Primary
    fun postgresEntityManager(
        @Qualifier("postgresDataSource") postgresDataSource: DataSource,
        factoryBuilder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return factoryBuilder.dataSource(postgresDataSource)
            .packages("br.com.devcave.jpa.multidatasource.domain.postgres")
            .properties(hibernateConfiguration.vendorProperties("org.hibernate.dialect.PostgreSQLDialect"))
            .build()
    }

    @Bean
    @Primary
    fun postgresTransactionManager(
        @Qualifier("postgresEntityManager") postgresEntityManager: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = postgresEntityManager.getObject()
        return transactionManager
    }
}