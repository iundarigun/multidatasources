package br.com.devcave.jpa.multidatasource.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["br.com.devcave.jpa.multidatasource.domain.mysql"],
    entityManagerFactoryRef = "mysqlEntityManager",
    transactionManagerRef = "mysqlTransactionManager"
)
class MysqlDatasourceConfiguration(
    private val hibernateConfiguration: HibernateConfiguration
) {
    @Bean
    @ConfigurationProperties("spring.mysql-datasource")
    fun mysqlDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun mysqlDataSource(): DataSource {
        return mysqlDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun mysqlEntityManager(
        @Qualifier("mysqlDataSource") mysqlDataSource: DataSource,
        factoryBuilder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return factoryBuilder.dataSource(mysqlDataSource)
            .packages("br.com.devcave.jpa.multidatasource.domain.mysql")
            .properties(hibernateConfiguration.vendorProperties("org.hibernate.dialect.MySQL8Dialect"))
            .build()
    }

    @Bean
    fun mysqlTransactionManager(
        @Qualifier("mysqlEntityManager") mysqlEntityManager: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = mysqlEntityManager.getObject()
        return transactionManager
    }
}