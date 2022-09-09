package br.com.devcave.jpa.multidatasource.configuration

import com.atomikos.jdbc.AtomikosDataSourceBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["br.com.devcave.jpa.multidatasource.domain.postgres"],
    entityManagerFactoryRef = "postgresEntityManager",
    transactionManagerRef = "jtaTransactionManager"
)
class PostgresDatasourceConfiguration(
    private val hibernateConfiguration: HibernateConfiguration
) {
    @Bean
    @ConfigurationProperties("spring.postgres-datasource")
    @Primary
    fun postgresDataSource(): DataSource {
        return AtomikosDataSourceBean()
    }

    @Bean
    @Primary
    fun postgresEntityManager(
        @Qualifier("postgresDataSource") postgresDataSource: DataSource,
        factoryBuilder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return factoryBuilder.dataSource(postgresDataSource)
            .jta(true)
            .packages("br.com.devcave.jpa.multidatasource.domain.postgres")
            .properties(hibernateConfiguration.vendorProperties("org.hibernate.dialect.PostgreSQLDialect"))
            .build()
    }
}